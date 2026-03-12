package com.hana8.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hana8.demo.dto.MemberImageDTO;
import com.hana8.demo.entity.Member;
import com.hana8.demo.entity.MemberImage;
import com.hana8.demo.mapper.MemberImageMapper;
import com.hana8.demo.repository.MemberImageRepository;
import com.hana8.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImageService {

    private final MemberImageRepository repository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final MemberImageMapper mapper;

    public MemberImageDTO saveMemberImage(Long memberId, MultipartFile file) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        String today = fileService.getTodayPath();
        String savedFilename = fileService.upload(file, today, false);

        MemberImage memberImage = MemberImage.builder()
                .member(member)
                .orgname(file.getOriginalFilename())
                .savename(savedFilename)
                .savedir(today)
                .build();

        return mapper.toDTO(repository.save(memberImage));
    }

    // 다중 이미지 업로드 추가
    public List<MemberImageDTO> saveMemberImages(Long memberId, List<MultipartFile> files) {
        return files.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> saveMemberImage(memberId, file))
                .toList();
    }

    public void deleteMemberImage(Long imageId) {
        MemberImage image = repository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found: " + imageId));
        
        // 실제 물리적 파일 삭제 (원본 + 썸네일)
        fileService.deleteFile(image.getSavedir() + "/" + image.getSavename(), false);
        
        // DB 레코드 삭제
        repository.delete(image);
    }

    public List<MemberImageDTO> getMemberImages(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
        return mapper.toDTOList(member.getImages());
    }
}
