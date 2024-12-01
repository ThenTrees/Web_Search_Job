package com.thentrees.lab_week5_www.frontend.controller;

import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
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

    @GetMapping("/home")
    public ModelAndView showHomePage() {
        CandidateResponseDto candidate = (CandidateResponseDto) httpSession.getAttribute("candidate");
        log.info("User info:::{}", candidate);
        ModelAndView mv = new ModelAndView();
        mv.addObject("candidate", candidate);
        mv.setViewName("/index");
        return mv;
    }
}
