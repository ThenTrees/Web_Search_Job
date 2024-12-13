package com.thentrees.lab_week5_www.frontend.controller;

import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
import com.thentrees.lab_week5_www.backend.models.*;
import com.thentrees.lab_week5_www.backend.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final IJobService jobService;
    private final IJobSkillService jobSkillService;
    private final ICandidateJobService candidateJobService;
    private final ISkillService skillService;

    @GetMapping("/new-job")
    public ModelAndView ShowFormNewJob(ModelAndView mv) {

        mv.addObject("skills", skillService.getAllSkills());
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
        log.info("Dda xoa");
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteJob(@PathVariable("id") Long id, ModelAndView mv){
        jobService.deleteJob(id);
        mv.setViewName("redirect:/companies/my");
        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showJobModal(@PathVariable("id") Long jobId, ModelAndView mv) {
        // Lấy job từ database
        Job job = jobService.getJobById(jobId);
        // Lấy danh sách kỹ năng từ database
        List<Skill> skills = skillService.getAllSkills();
        // Thêm job và skills vào model
        mv.addObject("job", job);
        mv.addObject("skills", skills);
        mv.setViewName("edit-job");
        return mv;
    }

//    @PostMapping("/edit")
//    public ModelAndView editJob(
//           @ModelAttribute("job") JobRequestDto jobRequestDto,
//           @RequestParam("id") Long id
//    ) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        log.info("id: {}", id);
//        log.info("title: {}", jobRequestDto.getName());
//        log.info("description: {}", jobRequestDto.getDescription());
//        log.info("level: {}", jobRequestDto.getLevel());
//        log.info("skills: {}", jobRequestDto.getSkills());
//        log.info("salary: {}", jobRequestDto.getSalary());
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Company company = (Company) authentication.getPrincipal();
//
//        String cleanedInput = salary.replaceAll(",", "");
//
//        JobRequestDto jobRequestDto = JobRequestDto.builder()
//                .name(title)
//                .level(Integer.parseInt(level))
//                .description(description)
//                .salary(Double.parseDouble(cleanedInput))
//                .companyId(company.getId())
//                .skills(skills)
//                .build();
//
//        jobService.updateJob(Long.parseLong(jobId),jobRequestDto); // lam toi day roi ne
//
//        modelAndView.setViewName("redirect:/companies/my");
//        return modelAndView;
//    }

    @PostMapping("/edit")
    public ModelAndView editJob(ModelAndView modelAndView, @ModelAttribute("job") JobRequestDto jobRequestDto, @Valid BindingResult result,
           @RequestParam("id") Long id){
        try {
            if (result.hasErrors()) {
                log.error("Validation errors: {}", result.getAllErrors());
                modelAndView.addObject("errorMessage", "Invalid input data. Please check the fields.");
                modelAndView.setViewName("error");
                return modelAndView; // Tên của file HTML trang lỗi
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Company company = (Company) authentication.getPrincipal();
            jobService.updateJob(id, jobRequestDto);
            modelAndView.setViewName("redirect:/companies/my");
            return modelAndView;
        } catch (Exception ex) {
            log.error("Error during registration: {}", ex.getMessage());
            modelAndView.addObject("errorMessage", ex.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }
}
