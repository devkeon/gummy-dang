package com.develop_mouse.gummy_dang.image.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.image.domain.response.ImageResponse;

public interface ImageUploadService {

	Response<ImageResponse<String>> uploadProfileImage(MultipartFile multipartFile) throws IOException;

	Response<ImageResponse<String>> uploadPostImage(MultipartFile multipartFiles, Long postId) throws IOException;

	Response<ImageResponse<String>> uploadWalkRecordImage(MultipartFile multipartFile, Long walkRecordId) throws
		IOException;

}
