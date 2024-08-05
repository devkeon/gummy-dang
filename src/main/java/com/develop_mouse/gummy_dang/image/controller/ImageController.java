package com.develop_mouse.gummy_dang.image.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.image.domain.response.ImageResponse;
import com.develop_mouse.gummy_dang.image.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageUploadService imageUploadService;

	@PostMapping("/profile")
	public Response<ImageResponse<String>> uploadProfileImage(@RequestPart("file") MultipartFile file) throws IOException {

		return imageUploadService.uploadProfileImage(file);
	}

	@PostMapping("/post")
	public Response<ImageResponse<String>> uploadPostImages(
		@RequestParam("postId") Long postId,
		@RequestPart("file") MultipartFile file
		) throws IOException {

		return imageUploadService.uploadPostImage(file, postId);
	}

	@PostMapping("/record")
	public Response<ImageResponse<String>> uploadRecordImage(
		@RequestPart("file") MultipartFile file,
		@RequestParam("recordId") Long recordId
	)  throws IOException {

		return imageUploadService.uploadWalkRecordImage(file, recordId);
	}

}
