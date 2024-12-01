package com.thentrees.lab_week5_www.backend.controller;

import com.thentrees.lab_week5_www.backend.dto.request.SkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.ResponseObject;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.services.ISkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/skills")
@RequiredArgsConstructor
public class SkillController {

    private final ISkillService skillService;

    @PostMapping
    public ResponseEntity<ResponseObject> createSkill(@Valid SkillRequestDto skillRequestDto) {
        Skill skill = skillService.addSkill(skillRequestDto);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Skill created successfully")
                .code(201)
                .data(skill)
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllSkills() {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Skills fetched successfully")
                .code(200)
                .data(skillService.getAllSkills())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getSkillById(Long id) {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Skill fetched successfully")
                .code(200)
                .data(skillService.getSkillById(id))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteSkill(Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Skill deleted successfully")
                .code(200)
                .build());
    }


}
