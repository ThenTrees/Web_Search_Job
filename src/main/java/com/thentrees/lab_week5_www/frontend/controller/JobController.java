package com.thentrees.lab_week5_www.frontend.controller;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.CandidateJob;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import com.thentrees.lab_week5_www.backend.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final IJobService jobService;
    private final IJobSkillService jobSkillService;
    private final ICandidateJobService candidateJobService;

    @GetMapping("/new-job")
    public ModelAndView ShowFormNewJob(ModelAndView mv) {
        mv.setViewName("/new-post");
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getJobById(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        ArrayList<Long> jobApplied = new ArrayList<Long>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equalsIgnoreCase("anonymousUser") && authentication.getPrincipal() instanceof Candidate){
            Candidate candidate = (Candidate) authentication.getPrincipal();
            List<CandidateJob> candidateJobs = candidateJobService.getAllCandidateJobByCandidateId(candidate.getId());
            for (CandidateJob candidateJob : candidateJobs){
                jobApplied.add(candidateJob.getJob().getId());
            }
        } else if (!authentication.getName().equalsIgnoreCase("anonymousUser") && authentication.getPrincipal() instanceof Company) {
            jobApplied = null;
            List<CandidateJob> listCandidateByJobId = candidateJobService.getAllCandidatesByJobId(id);
            List<Candidate> candidateOfJob = new ArrayList<>();
            for (CandidateJob candidateJob : listCandidateByJobId){
                candidateOfJob.add(candidateJob.getCandidate());
            }
            mv.addObject("candidateOfJob", candidateOfJob);
        }
        List<JobSkill> jobSkills = jobSkillService.getAllSkillsByJobId(id);
        mv.addObject("jobApplied", jobApplied);
        mv.addObject("jobSkills", jobSkills);
        mv.addObject("job", jobService.getJobById(id));
        mv.setViewName("job-detail");
        return mv;
    }

    @GetMapping("/apply/{id}")
    public ModelAndView applyJob(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equals("anonymousUser")) {
            mv.setViewName("redirect:/login");
            return mv;
        }
        Candidate candidate = (Candidate) auth.getPrincipal();
        candidateJobService.addCandidateToJob(candidate.getId(), id);
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("/un-apply/{id}")
    public ModelAndView unApplyJob(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equals("anonymousUser")) {
            mv.setViewName("redirect:/login");
            return mv;
        }
        Candidate candidate = (Candidate) auth.getPrincipal();
        candidateJobService.removeCandidateFromJob(candidate.getId(), id);
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteJob(@PathVariable("id") Long id, ModelAndView mv){
        jobService.deleteJob(id);
        mv.setViewName("redirect:/companies/my");
        return mv;
    }
}
