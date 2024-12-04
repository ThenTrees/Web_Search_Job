package com.thentrees.lab_week5_www.frontend.controller;

import com.thentrees.lab_week5_www.backend.models.JobSkill;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import com.thentrees.lab_week5_www.backend.services.IJobSkillService;
import com.thentrees.lab_week5_www.backend.services.ISkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final IJobService jobService;
    private final IJobSkillService jobSkillService;
//    private final ICompanyService companyService;
    @GetMapping("/{id}")
    public ModelAndView getJobById(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        // get all skill of job
        List<JobSkill> jobSkills = jobSkillService.getAllSkillsByJobId(id);
        mv.addObject("jobSkills", jobSkills);
        mv.addObject("job", jobService.getJobById(id));
        mv.setViewName("job-detail");
        return mv;
    }
}
