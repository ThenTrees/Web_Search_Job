package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final IJobService jobService;

    @GetMapping({"/",""})
    public ModelAndView showHomePage(@RequestParam(defaultValue = "0") int page, ModelAndView mv) {
        log.info("Show home page.");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Authentication:::{}", authentication.getPrincipal().toString());
        Pageable pageable = PageRequest.of(page, 3); // 5 jobs per page
        Page<Job> jobs = jobService.getAllJobs(pageable);
        mv.addObject("jobs", jobs);
        mv.setViewName("/index");
        return mv;
    }

    @GetMapping("/profile")
    public ModelAndView showProfilePage(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Candidate candidate = (Candidate) authentication.getPrincipal();
        log.info("Candidate:::{}", candidate);
        if(candidate == null) {
            mv.setViewName("redirect:/login");
            return mv;
        }
            mv.addObject("candidate", candidate);
            mv.addObject("address", candidate.getAddress());
            mv.addObject("countries", CountryCode.values());
            mv.setViewName("candidate/profile");
            return mv;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("auth/sign-up");
        return modelAndView;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/sign-in";  // Default login form view
    }

    @GetMapping("/apply")
    public String showApplyPage() {
       return "apply";
    }

}
