package com.develop_mouse.gummy_dang.image.domain.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageResponse<T> {
	
	private T imageUrl;

	public void updateImageUrl(T imageUrl){
		this.imageUrl = imageUrl;
	}

	public static <T> ImageResponse <T> of(T data) {
		return new ImageResponse<>(data);
	}

}
