package com.thentrees.lab_week5_www.frontend.controller.admin;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import com.thentrees.lab_week5_www.backend.services.ISkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final ICandidateService candidateService;
    private final IJobService jobService;
    private final ISkillService skillService;
    private final ICompanyService companyService;
    final int NUMBER_ELEMENT = 3;
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/companies")
    public ModelAndView showHomePageCompany(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String city,
            ModelAndView mv) {
        Pageable pageable = PageRequest.of(page, NUMBER_ELEMENT); // 5 jobs per page
        Page<Company> companies = companyService.getAllCompanies(pageable, search, city);
        mv.addObject("companies",
                companies);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("admin/companies");
        return mv;
    }

    @GetMapping("/admin/jobs")
    public ModelAndView showJobsPage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "") String search,
                                     @RequestParam(defaultValue = "") String city,
                                     ModelAndView mv) {
        Pageable pageable = PageRequest.of(page, NUMBER_ELEMENT); // 5 jobs per page
        Page<Job> jobs = jobService.getAllJobs(pageable, search, city);
        mv.addObject("jobs",
                jobs);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("admin/jobs");
        return mv;
    }

    @GetMapping("/admin/candidates")
    public ModelAndView showCandidatesPage(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "") String search,
                                           @RequestParam(defaultValue = "") String city,
                                           ModelAndView mv) {
        Pageable pageable = PageRequest.of(page, 2); // 5 jobs per page
        Page<Candidate> candidates = candidateService.getAllCandidates(pageable, search, city);
        mv.addObject("candidates",
                candidates);
        mv.addObject("search", search);
        mv.addObject("city", city);
        mv.setViewName("admin/candidates");
        return mv;
    }

    @GetMapping("/admin/skills")
    public ModelAndView showSkillsPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "") String search,
                                       ModelAndView mv) {
        Pageable pageable = PageRequest.of(page, NUMBER_ELEMENT); // 5 jobs per page
        Page<Skill> Skills = skillService.getAllSkills(pageable, search);
        mv.addObject("skills",
                Skills);
        mv.addObject("search", search);
        mv.setViewName("admin/skills");
        return mv;
    }
}
