package com.thentrees.lab_week5_www.frontend.controller;

import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.User;
import com.thentrees.lab_week5_www.backend.services.IAddressService;
import com.thentrees.lab_week5_www.backend.services.IAuthenticationService;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
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
    private final IAuthenticationService authenticationService;
    private final ICandidateService candidateService;
    private final IAddressService addressService;
    private final ICompanyService companyService;
    /*
        * Đăng ký tài khoản (POST request)
        * @ModelAttribute("candidate") CandidateRequestDto candidateRequestDto: Lấy thông tin người dùng từ form
        * @ModelAttribute("address") AddressRequestDto addressRequestDto: Lấy thông tin địa chỉ từ form
        * @Valid BindingResult result: Kiểm tra lỗi
     */
    @PostMapping("/register-candidate")
    public String registerAccount(
            @ModelAttribute ("candidate") CandidateRequestDto candidateRequestDto,
            @Valid BindingResult result
    ) {
        log.info("begin register");
        log.info("Candidate ::: {}", candidateRequestDto);
        log.info("Address ::: {}", candidateRequestDto.getAddress());
        candidateService.createCandidate(candidateRequestDto);
        log.info("end register");
        return "redirect:/login";
    }

    @PostMapping("/register-company")
    public String registerCompany(
            @ModelAttribute ("company") CompanyRequestDto companyRequestDto,
            @Valid BindingResult result
    ) {
        log.info("begin register");
        log.info("Address ::: {}", companyRequestDto.getAddress());
        companyService.addCompany(companyRequestDto);
        log.info("end register");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // Chuyển hướng về trang login
    }

//    @PostMapping("/login")
//    public String login(
//            @RequestParam("username") String phone,
//            @RequestParam("password") String password
//    ) {
//        log.info("begin login");
//        log.info("phone: {}", phone);
//        log.info("password: {}", password);
//        // Gọi hàm login từ backend
//        User user = authenticationService.login(phone, password);
//        log.info("end login:: user: {}", user);
//        return "redirect:/"; // Chuyển hướng về trang chủ
//    }
}
