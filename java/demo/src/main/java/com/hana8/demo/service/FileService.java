package com.hana8.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class FileService {
	@Value("${upload.path}")
	private String uploadPath;

	@Value("${upload.secure}")
	private String securePath;

	public String upload(MultipartFile file) {
		return upload(file, getTodayPath(), false);
	}

	public String upload(MultipartFile file, boolean isSecure) {
		return upload(file, getTodayPath(), isSecure);
	}

    public String getTodayPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

	public String upload(MultipartFile file, String subPath, boolean isSecure) {
		if (file.isEmpty() || file.getOriginalFilename() == null)
			throw new IllegalArgumentException("파일이 비어있습니다.");

		// 원본 파일명
		String originalFilename = file.getOriginalFilename();

		// 확장자 추출
		String ext = originalFilename.substring(
			originalFilename.lastIndexOf("."));

		// UUID로 파일명 중복 방지
		String savedFilename = UUID.randomUUID() + ext;

		// 저장 경로를 절대 경로로 처리
		Path rootPath = Paths.get(isSecure ? securePath : uploadPath).toAbsolutePath();
		Path targetDir = rootPath.resolve(subPath);
		Path savePath = targetDir.resolve(savedFilename);
		Path thumbPath = targetDir.resolve("thumb_" + savedFilename);
		
		try {
			// 디렉토리 없으면 생성
			Files.createDirectories(targetDir);

			// 파일 저장
			file.transferTo(savePath);

			String contentType = file.getContentType();
			if (contentType != null && contentType.startsWith(("image/"))) {
				Thumbnails.of(savePath.toFile())
					.size(200, 200)
					.crop(Positions.CENTER)
					.outputQuality(0.8)
					.toFile(thumbPath.toFile());
			}
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패", e);
		}
		return savedFilename;
	}

	public ResponseEntity<Resource> download(String filename, boolean inline, boolean isSecure) {
		Path rootPath = Paths.get(isSecure ? securePath : uploadPath).toAbsolutePath();
		Path filePath = rootPath.resolve(filename).normalize();
		Resource resource = new FileSystemResource(filePath);

		if (!resource.exists())
			throw new NoSuchElementException("파일을 찾을 수 없습니다: " + filename);

		// Content-Type 자동 감지
		String contentType;
		try {
			contentType = Files.probeContentType(filePath);
		} catch (IOException e) {
			contentType = "application/octet-stream";
		}

		String disposition = (inline ? "inline" : "attachment") + "; filename=\"" + filename + "\"";
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, disposition)
			.body(resource);
	}

    public void deleteFile(String relativePath, boolean isSecure) {
        // 절대 경로로 변환하여 정확한 위치 파악
        Path rootPath = Paths.get(isSecure ? securePath : uploadPath).toAbsolutePath();
        Path filePath = rootPath.resolve(relativePath).normalize();
        
        try {
            // 원본 파일 삭제
            boolean deleted = Files.deleteIfExists(filePath);
            
            // 썸네일 파일 삭제 (파일명 앞에 thumb_ 붙임)
            Path thumbPath = filePath.getParent().resolve("thumb_" + filePath.getFileName());
            Files.deleteIfExists(thumbPath);
            
            System.out.println("파일 삭제 시도: " + filePath + " (결과: " + deleted + ")");
        } catch (IOException e) {
            // 삭제 에러 발생 시 로그 출력 (윈도우에서 파일 잠금 등)
            System.err.println("파일 삭제 실패: " + e.getMessage());
        }
    }
}
