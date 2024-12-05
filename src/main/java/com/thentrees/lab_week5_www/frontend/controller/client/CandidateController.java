package com.thentrees.lab_week5_www.frontend.controller.client;

import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.services.IAddressService;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${api.prefix}/candidates")
@RequiredArgsConstructor
@Slf4j
public class CandidateController {

    private final ICandidateService candidateService;
    private final IAddressService addressService;

    @PostMapping("/update/{id}")
    public String updateProfile(
            @ModelAttribute ("candidate") CandidateRequestDto candidateRequestDto,
            @ModelAttribute ("address") AddressRequestDto addressRequestDto,
            @Valid BindingResult result,
            @PathVariable("id") Long id) {
        log.info("Register with candidate info::::{}", candidateRequestDto);
        log.info("Register with candidate address::::{}", addressRequestDto);
        return "redirect:/login";
    }
}
