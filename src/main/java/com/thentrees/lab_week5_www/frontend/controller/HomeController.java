package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import com.thentrees.lab_week5_www.backend.services.IJobSkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final IJobService jobService;
    private final IJobSkillService jobSkillService;
    @GetMapping("/")
    public ModelAndView showHomePage(ModelAndView mv) {
        log.info("Show home page.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof Candidate) {
            Candidate candidate =  (Candidate) authentication.getPrincipal();
            log.info("Candidate:::{}", candidate.getFullName());
            mv.addObject("candidate", candidate);
        }else{
            mv.addObject("candidate", null);
        }
        List<Job> jobs = jobService.getAllJobs();
        mv.addObject("jobs", jobs);
        mv.setViewName("/index");
        return mv;
    }


    @GetMapping("/profile")
    public ModelAndView showProfilePage(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Candidate candidate = (Candidate) authentication.getPrincipal();
        log.info("Profile:::{}", candidate.getFullName());
        mv.addObject("candidate", candidate);
        mv.addObject("address", candidate.getAddress());
        mv.addObject("countries", CountryCode.values());
        mv.setViewName("candidate/profile");
        return mv;
    }

    @GetMapping("/show-register-form")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("auth/sign-up");
        return modelAndView;
    }

    @GetMapping("/show-login-form")
    public String showLoginForm() {
        return "auth/sign-in";  // Default login form view
    }



}
