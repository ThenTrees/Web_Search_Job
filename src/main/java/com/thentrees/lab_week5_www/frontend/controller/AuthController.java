package com.thentrees.lab_week5_www.frontend.controller;

import com.neovisionaries.i18n.CountryCode;
import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final ICandidateService candidateService;
    private final ICompanyService companyService;
    private final ISkillService skillService;

    @GetMapping("/register-candidate")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        Candidate candidate = Candidate.builder()
                .address(new Address())
                .build();
        List<Skill> skills = skillService.getAllSkills();
        modelAndView.addObject("skills", skills);
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("auth/sign-up");
        return modelAndView;
    }

    /*
     * Đăng ký tài khoản (POST request)
     * @ModelAttribute("candidate") CandidateRequestDto candidateRequestDto: Lấy thông tin người dùng từ form
     * @ModelAttribute("address") AddressRequestDto addressRequestDto: Lấy thông tin địa chỉ từ form
     * @Valid BindingResult result: Kiểm tra lỗi
     */
    @PostMapping("/register-candidate")
    public ModelAndView registerAccount(
            @ModelAttribute("candidate") @Valid CandidateRequestDto candidateRequestDto,
            @Valid BindingResult result,
            @RequestParam Set<String> skills,
            ModelAndView modelAndView,
            Model model) {
        try {
            // Nếu có lỗi validate, chuyển về trang lỗi
            if (result.hasErrors()) {
                log.error("Validation errors: {}", result.getAllErrors());
                modelAndView.addObject("errorMessage", "Invalid input data. Please check the fields.");
                modelAndView.setViewName("error");
                return modelAndView; // Tên của file HTML trang lỗi
            }
            candidateRequestDto.setSkills(skills);
            log.info("Candidate ::: {}", candidateRequestDto);
            candidateService.createCandidate(candidateRequestDto);
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        } catch (Exception ex) {
            log.error("Error during registration: {}", ex.getMessage());
            modelAndView.addObject("errorMessage", ex.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }


    @GetMapping("/register-company")
    public ModelAndView showRegisterCompanyForm(ModelAndView modelAndView) {
        CompanyRequestDto company = new CompanyRequestDto();
        company.setAddress(new Address());
        modelAndView.addObject("company", company);
        modelAndView.addObject("address", company.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("auth/sign-up-company");
        return modelAndView;
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
