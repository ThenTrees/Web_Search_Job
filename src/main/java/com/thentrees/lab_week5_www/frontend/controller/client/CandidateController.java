package com.thentrees.lab_week5_www.frontend.controller.client;

import com.thentrees.lab_week5_www.backend.dto.request.AddressRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateUpdateRequestDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.services.IAddressService;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("${api.prefix}/candidates")
@RequiredArgsConstructor
@Slf4j
public class CandidateController {

    private final ICandidateService candidateService;
    private final IAddressService addressService;


    /**
     * lay thong tin can can update, luu thong tin dia chi cua can,
     * @param candidateUpdateRequestDto lay thong tin nguoi dung thay doi
     * @param addressRequestDto lay thong tin address cua nguoi dung thay doi
     * @param result
     * @return
     */
    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute ("candidate") CandidateUpdateRequestDto candidateUpdateRequestDto,
            @ModelAttribute ("address") AddressRequestDto addressRequestDto,
            @Valid BindingResult result
            ) {
        log.info("begin update profile");
        Long canId = candidateUpdateRequestDto.getId();
        Address address = candidateService.getAddressByCanId(canId); // dia chi cu
        addressService.updateAddress(address.getId(), addressRequestDto); // dia chi moi
        candidateService.updateCandidate(candidateUpdateRequestDto);
        log.info("end update profile");
        return "redirect:/logout";
    }

    @PostMapping("/apply-job")
    public ModelAndView applyJob(ModelAndView mv, Long jobId, Long canId) {

        return mv;
    }

}
