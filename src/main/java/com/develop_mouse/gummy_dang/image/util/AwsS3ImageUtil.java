package com.develop_mouse.gummy_dang.image.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AwsS3ImageUtil {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket-name}")
	private String bucket;

	public String uploadProfileImage(MultipartFile multipartFile, String filePath) throws IOException {

		if (multipartFile == null) {
			throw new BusinessException(ResponseCode.IMAGE_NOT_FOUND);
		}

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

		amazonS3.putObject(bucket, filePath, multipartFile.getInputStream(), metadata);

		return amazonS3.getUrl(bucket, filePath).toString();
	}

}
