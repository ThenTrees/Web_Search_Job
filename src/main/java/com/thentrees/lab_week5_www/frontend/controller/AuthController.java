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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ICandidateService candidateService;
    private final IAddressService addressService;
    private final AuthServiceImpl authServiceImpl;
    private final HttpSession session;

    @GetMapping("/show-login-form")
    public String showLoginForm() {
        return "auth/sign-in";
    }

    @GetMapping("/show-register-form")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("/auth/sign-up");
        return modelAndView;
    }

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
        return "redirect:/api/v1/auth/show-login-form";
    }

    // Xử lý đăng nhập (POST request)
    @PostMapping("/login")
    public String login(@RequestParam("phone") String phone,
                        @RequestParam("password") String password,
                        Model model) {
        log.info("Login with phone::::{}", phone);
        CandidateResponseDto candidateResponseDto = authServiceImpl.login(phone, password);
        if (candidateResponseDto!=null) {
            session.setAttribute("candidate", candidateResponseDto);
            return "redirect:/home";  // Chuyển hướng đến trang chủ sau khi đăng nhập thành công, bo api v1 di nhu o product site
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "auth/sign-in";  // Quay lại trang login nếu đăng nhập thất bại
        }
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // Xóa toàn bộ session
        return "redirect:/home"; // Chuyển hướng về trang login
    }
}
