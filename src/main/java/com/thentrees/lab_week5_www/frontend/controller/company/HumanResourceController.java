package com.thentrees.lab_week5_www.frontend.controller.company;

import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
import com.thentrees.lab_week5_www.backend.email.MailService;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.repositories.JobSkillRepository;
import com.thentrees.lab_week5_www.backend.repositories.SkillRepository;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import com.thentrees.lab_week5_www.backend.services.IJobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("${api.prefix}/hrs")
@RequiredArgsConstructor
@Slf4j
public class HumanResourceController {

    private final IJobService jobService;
    private final MailService mailService;

    @GetMapping("")
    public ModelAndView homePageForHr() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home-company");
        return modelAndView;
    }

    @GetMapping("/new-job")
    public ModelAndView showNewJobPage(ModelAndView mv) {
        mv.setViewName("new-post");
        return mv;
    }

    @PostMapping("/new-job")
    public ModelAndView handleNewJob(@Valid
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("level") String level,
            @RequestParam List<String> skills,
            @RequestParam("salary") String salary,
            ModelAndView modelAndView
            ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Company company = (Company) authentication.getPrincipal();

            String cleanedInput = salary.replaceAll(",", "");

            JobRequestDto jobRequestDto = JobRequestDto.builder()
                    .name(title)
                    .level(Integer.parseInt(level))
                    .description(description)
                    .salary(Double.parseDouble(cleanedInput))
                    .companyId(company.getId())
                    .skills(skills)
                    .build();
            log.info("job request dto {}", jobRequestDto);

            jobService.addJob(jobRequestDto); // lam toi day roi ne

            modelAndView.setViewName("redirect:/companies/my");
            return modelAndView;
        } catch (Exception ex) {
            log.error("Error during post new job: {}", ex.getMessage());
            modelAndView.addObject("errorMessage", ex.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @PostMapping("/send-mail")
    public ModelAndView sendMail(
            @RequestParam("email") String email
    ) {
        ModelAndView modelAndView = new ModelAndView();

        log.info("email: {}", email);

        String to = email;
        String subject = "Lien he voi ung vien";
        String body = "Chuc mung ban da duoc nhan viec";

        try
            {
            mailService.sendEmail(to,subject,body);
        }catch (Exception e){
            log.error("Error when sending email: {}", e.getMessage());
            e.printStackTrace();
        }
        modelAndView.setViewName("redirect:/companies/my");
        return modelAndView;
    }


}
