package com.thentrees.lab_week5_www.frontend.controller.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${api.prefix}/candidates")
@RequiredArgsConstructor
@Slf4j
public class CandidateController {
}
