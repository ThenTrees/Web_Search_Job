package com.thentrees.lab_week5_www.backend.controller;

import com.thentrees.lab_week5_www.backend.dto.request.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.ResponseObject;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final ICandidateService candidateService;


    @PostMapping
    public ResponseEntity<ResponseObject> addCandidate(@Valid @RequestBody CandidateRequestDto candidateRequestDto){
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Candidate added successfully")
                .code(HttpStatus.CREATED.value())
                .data(candidateService.createCandidate(candidateRequestDto))
                .build());
    }
//
//    @GetMapping
//    public ResponseEntity<ResponseObject> getAllCandidates(){
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Candidates fetched successfully")
//                .code(HttpStatus.OK.value())
//                .data(candidateService.getAllCandidates())
//                .build());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseObject> getCandidateById(@PathVariable Long id){
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Candidate fetched successfully")
//                .code(HttpStatus.OK.value())
//                .data(candidateService.getCandidateById(id))
//                .build());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseObject> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateRequestDto candidateRequestDto){
//        candidateService.updateCandidate(id, candidateRequestDto);
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Candidate updated successfully")
//                .code(HttpStatus.OK.value())
//                .data(null)
//                .build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseObject> deleteCandidate(@PathVariable Long id){
//        candidateService.deleteCandidate(id);
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Candidate deleted successfully")
//                .code(HttpStatus.OK.value())
//                .data(null)
//                .build());
//    }
}
