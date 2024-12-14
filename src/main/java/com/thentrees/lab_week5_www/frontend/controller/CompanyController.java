package com.thentrees.lab_week5_www.frontend.controller;

import com.thentrees.lab_week5_www.backend.models.*;
import com.thentrees.lab_week5_www.backend.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/companies")
@Slf4j
@RequiredArgsConstructor
public class CompanyController {
    private final ICompanyService companyService;
    private final IJobService jobService;
    private final ICandidateJobService candidateJobService;
    private final ICandidateService candidateService;
    private final ISkillService skillService;
    final int NUMBER_ELEMENT = 3;
    @GetMapping("/show")
    public ModelAndView showHomePageCompany(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String city,
            ModelAndView mv) {
        // search job by title and city
        Pageable pageable = PageRequest.of(page, NUMBER_ELEMENT); // 5 jobs per page
        Page<Candidate> candidates = candidateService.getAllCandidates(pageable, search, city);

        List<Skill> skills = skillService.getAllSkills();

        mv.addObject("candidates", candidates);
        mv.addObject("skills", skills);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("company/index");
        return mv;
    }

    @GetMapping("/my")
    public ModelAndView showCompany(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equalsIgnoreCase("anonymousUser")){
            mv.setViewName("redirect:/login");
            return mv;
        }
        Company company = (Company) authentication.getPrincipal();
        List<Job> jobs = jobService.getAllJobByCompanyId(company.getId());

        mv.addObject("jobs", jobs);
        mv.addObject("company", company);
        mv.setViewName("home-company");
        return mv;
    }

    /**
     * Show list company
     * candidate has seen site for find company
     * @param ModelAndView
     * @return page list company
     */

    @GetMapping("")
    public ModelAndView showListCompany(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "") String search,
                                        @RequestParam(defaultValue = "") String city,
                                        ModelAndView mv, Pageable pageable) {
        log.info("Show list company:::");
        Page<Company> companies = companyService.getAllCompanies(pageable, search, city);
        mv.addObject("companies", companies);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("/list-company");
        return mv;
    }

    /**
     * Show detail company
     * candidate has seen detail company
     * @param ModelAndView
     * @return page detail company
     */
    @GetMapping("/{id}")
    public ModelAndView showDetailCompany(ModelAndView mv, @PathVariable("id") Long id) {
        log.info("Show detail company:::");
        Company company = companyService.getCompanyById(id);
        // get all job of company
        List<Job> jobList = jobService.getAllJobByCompanyId(id);
        ArrayList<Long> jobApplied = new ArrayList<Long>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(hasRole(authentication, "ROLE_CANDIDATE")){
            Candidate candidate = (Candidate) authentication.getPrincipal();
            List<CandidateJob> candidateJobs = candidateJobService.getAllCandidateJobByCandidateId(candidate.getId());
            for (CandidateJob candidateJob : candidateJobs){
                jobApplied.add(candidateJob.getJob().getId());
            }
        }

        mv.addObject("jobApplied", jobApplied);

        mv.addObject("company", companyService.getCompanyById(id));
        mv.addObject("jobs", jobList);
        mv.setViewName("/company-detail");
        return mv;
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
