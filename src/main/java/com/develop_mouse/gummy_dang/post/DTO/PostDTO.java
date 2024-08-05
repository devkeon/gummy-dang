package com.develop_mouse.gummy_dang.post.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;

    private Set<PostCoordinateDTO> postCoordinates;
}