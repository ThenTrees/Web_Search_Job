package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final HttpSession httpSession;
    private final ICandidateService candidateService;
    private final ICompanyService companyService;

    @GetMapping("/home")
    public ModelAndView showHomePage(ModelAndView mv) {
            CandidateResponseDto candidate = (CandidateResponseDto) httpSession.getAttribute("candidate");
            log.info("Login:::{}", candidate.getFullName());
            mv.addObject("candidate", candidate);
            mv.setViewName("/index");
            return mv;
    }

    @GetMapping("/profile")
    public ModelAndView showProfilePage(ModelAndView mv) {
        CandidateResponseDto candidateDto = (CandidateResponseDto) httpSession.getAttribute("candidate");
        Candidate candidate = candidateService.getCandidateById(candidateDto.getId());
        log.info("Profile:::{}", candidate.getFullName());
        mv.addObject("candidate", candidate);
        mv.addObject("address", candidate.getAddress());
        mv.addObject("countries", CountryCode.values());
        mv.setViewName("candidate/profile");
        return mv;
    }

    /**
     * Show list company
     * candidate has seen site for find company
     * @param ModelAndView
     * @return page list company
     */
    @GetMapping("/companies")
    public ModelAndView showListCompany(ModelAndView mv) {
        log.info("Show list company:::");
        mv.addObject("companies", companyService.getAllCompanies());
        mv.setViewName("/list-company");
        return mv;
    }
}
