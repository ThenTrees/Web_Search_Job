package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.models.*;
import com.thentrees.lab_week5_www.backend.services.ICandidateJobService;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import com.thentrees.lab_week5_www.backend.services.ISkillService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final IJobService jobService;
    private final ICandidateJobService candidateJobService;
    final int NUMBER_ELEMENT = 3;
    private final ISkillService skillService;

    @GetMapping({"/",""})
    public ModelAndView showHomePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String city,
            ModelAndView mv) {
        // search job by title and city
        ArrayList<Long> jobApplied = new ArrayList<Long>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getName().equalsIgnoreCase("anonymousUser")){
            User candidate = (User) authentication.getPrincipal();
            List<CandidateJob> candidateJobs = candidateJobService.getAllCandidateJobByCandidateId(candidate.getId());
            for (CandidateJob candidateJob : candidateJobs){
                jobApplied.add(candidateJob.getJob().getId());
            }
        }
        mv.addObject("jobApplied", jobApplied);

        Pageable pageable = PageRequest.of(page,NUMBER_ELEMENT); // 5 jobs per page
        Page<Job> jobs = jobService.getAllJobs(pageable, search, city);
        mv.addObject("skills", skillService.getAllSkills());
        mv.addObject("jobs", jobs);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("/index");
        return mv;
    }
    @GetMapping("/profile")
    public ModelAndView showProfilePage(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(hasRole(authentication, "ROLE_COMPANY")) {
            Company company = (Company) authentication.getPrincipal();
            log.info("Company:::{}", company);
            if(company == null) {
                mv.setViewName("redirect:/login");
                return mv;
            }
            mv.setViewName("redirect:/companies/my");
            return mv;
        } else if (hasRole(authentication, "ROLE_CANDIDATE")) {
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
        return mv;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/sign-in";  // Default login form view
    }

    @GetMapping("/apply")
    public String showApplyPage() {
       return "apply";
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
