package com.thentrees.lab_week5_www.frontend.controller;

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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ICandidateService candidateService;
    private final IAddressService addressService;
    /*
        * Đăng ký tài khoản (POST request)
        * @ModelAttribute("candidate") CandidateRequestDto candidateRequestDto: Lấy thông tin người dùng từ form
        * @ModelAttribute("address") AddressRequestDto addressRequestDto: Lấy thông tin địa chỉ từ form
        * @Valid BindingResult result: Kiểm tra lỗi
     */
    @PostMapping("/register")
    public String registerAccount(
            @ModelAttribute ("candidate") CandidateRequestDto candidateRequestDto,
            @ModelAttribute ("address") AddressRequestDto addressRequestDto,
            @Valid BindingResult result
    ) {
        log.info("Register with candidate info::::{}", candidateRequestDto);
        log.info("Register with candidate address::::{}", addressRequestDto);
        Address addressRs = addressService.addAddress(addressRequestDto);
        candidateRequestDto.setAddress(addressRs);
        Candidate candidate = candidateService.createCandidate(candidateRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // Chuyển hướng về trang login
    }
}
