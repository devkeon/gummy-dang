package com.develop_mouse.gummy_dang.post.DTO;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String description;
    private String imageUrl;

    private Set<PostCoordinateDTO> postCoordinates;
}