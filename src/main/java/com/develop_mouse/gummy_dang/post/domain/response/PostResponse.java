package com.develop_mouse.gummy_dang.post.domain.response;

import java.time.LocalDate;
import java.util.List;

import com.develop_mouse.gummy_dang.post.DTO.PostCoordinateDTO;
import com.develop_mouse.gummy_dang.post.domain.entity.PostCoordinate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostResponse {

	private Long postId;
	private String title;
	private String description;
	private String imageUrl;
	private LocalDate createdAt;
	@Builder.Default
	private Boolean liked = false;

	private List<PostCoordinateDTO> postCoordinates;

}
