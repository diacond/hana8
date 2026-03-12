package com.hana8.demo.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hana8.demo.dto.MemberImageDTO;
import com.hana8.demo.service.MemberImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members/{memberId}/images")
@RequiredArgsConstructor
public class MemberImageController {

    private final MemberImageService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberImageDTO> uploadImage(@PathVariable Long memberId, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(service.saveMemberImage(memberId, file));
    }

    @PostMapping(value = "/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<MemberImageDTO>> uploadImages(@PathVariable Long memberId, @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(service.saveMemberImages(memberId, files));
    }

    @GetMapping
    public ResponseEntity<List<MemberImageDTO>> getImages(@PathVariable Long memberId) {
        return ResponseEntity.ok(service.getMemberImages(memberId));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long memberId, @PathVariable Long imageId) {
        service.deleteMemberImage(imageId);
        return ResponseEntity.noContent().build();
    }
}
