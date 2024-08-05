package com.develop_mouse.gummy_dang.post.domain.request;

import java.util.List;

import com.develop_mouse.gummy_dang.post.DTO.PostCoordinateDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequest {

	private Long postId;
	private String title;
	private String description;

	private List<PostCoordinateDTO> postCoordinates;

}
