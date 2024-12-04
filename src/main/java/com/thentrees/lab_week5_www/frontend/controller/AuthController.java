package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.services.IAddressService;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import com.thentrees.lab_week5_www.backend.services.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ICandidateService candidateService;
    private final IAddressService addressService;
    private final AuthServiceImpl authServiceImpl;
    private final AuthenticationManager authenticationManager;
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
        return "redirect:/show-login-form";
    }

    // Xử lý đăng nhập (POST request)
    @PostMapping("/login")
    public String login(@RequestParam("phone") String phone, @RequestParam("password") String password, Model model) {
        CandidateResponseDto candidateResponseDto = authServiceImpl.login(phone, password);
        if (candidateResponseDto != null) {
            Candidate candidate = candidateService.getCandidateById(candidateResponseDto.getId());
            return "redirect:/";  // Redirect to home page after successful login
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "auth/sign-in";  // Return to login page if authentication fails
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // Chuyển hướng về trang login
    }
}
