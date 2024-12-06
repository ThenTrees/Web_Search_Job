package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.CandidateJob;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.services.ICandidateJobService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final IJobService jobService;
    private final ICandidateJobService candidateJobService;
    @GetMapping({"/",""})
    public ModelAndView showHomePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String city,
            ModelAndView mv) {
        log.info("Show home page.");

        // search job by title and city
        ArrayList<Long> jobApplied = new ArrayList<Long>();
        // get all skill of job
        List<CandidateJob> candidateJobs = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equalsIgnoreCase("anonymousUser")){
            Candidate candidate = (Candidate) authentication.getPrincipal();
            candidateJobs = candidateJobService.getAllCandidateJobByCandidateId(candidate.getId());

            for (CandidateJob candidateJob : candidateJobs){
                jobApplied.add(candidateJob.getJob().getId());
            }

        }
        mv.addObject("jobApplied", jobApplied);

        int numberElement = 3;
        Pageable pageable = PageRequest.of(page, numberElement); // 5 jobs per page
        Page<Job> jobs = jobService.getAllJobs(pageable, search, city);
        mv.addObject("jobs", jobs);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("/index");
        return mv;
    }

    @GetMapping("/register-candidate")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("auth/sign-up");
        return modelAndView;
    }

    @GetMapping("/register-company")
    public ModelAndView showRegisterCompanyForm(ModelAndView modelAndView) {
        CompanyRequestDto company = new CompanyRequestDto();
        company.setAddress(new Address());
        modelAndView.addObject("company", company);
        modelAndView.addObject("address", company.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("auth/sign-up-company");
        return modelAndView;
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

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/sign-in";  // Default login form view
    }

    @GetMapping("/apply")
    public String showApplyPage() {
       return "apply";
    }


}
