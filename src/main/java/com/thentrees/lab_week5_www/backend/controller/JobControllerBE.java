//package com.thentrees.lab_week5_www.backend.controller;
//
//import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
//import com.thentrees.lab_week5_www.backend.dto.response.ResponseObject;
//import com.thentrees.lab_week5_www.backend.services.IJobService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("${api.prefix}/jobs")
//@RequiredArgsConstructor
//public class JobControllerBE {
//    private final IJobService jobService;
//
//    @PostMapping
//    public ResponseEntity<ResponseObject> addJob(@Valid @RequestBody JobRequestDto jobRequestDto) {
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Job created successfully")
//                .code(201)
//                .data(jobService.addJob(jobRequestDto))
//                .build());
//    }
//
//    @GetMapping
//    public ResponseEntity<ResponseObject> getAllJobs() {
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Jobs fetched successfully")
//                .code(200)
//                .data(jobService.getAllJobs())
//                .build());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseObject> getJobById(@PathVariable Long id) {
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Job fetched successfully")
//                .code(200)
//                .data(jobService.getJobById(id))
//                .build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseObject> deleteJob(@PathVariable Long id) {
//        jobService.deleteJob(id);
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Job deleted successfully")
//                .code(200)
//                .build());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseObject> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequestDto jobRequestDto) {
//        jobService.updateJob(id, jobRequestDto);
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Job updated successfully")
//                .code(200)
//                .build());
//    }
//
//}
