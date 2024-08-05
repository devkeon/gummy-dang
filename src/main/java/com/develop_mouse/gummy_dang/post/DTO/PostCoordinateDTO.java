package com.develop_mouse.gummy_dang.post.DTO;

import com.develop_mouse.gummy_dang.post.domain.entity.PostCoordinate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCoordinateDTO {

    private Long id;
    private Double latitude; // 위도
    private Double longitude; // 경도

    public static PostCoordinateDTO fromEntity(PostCoordinate postCoordinate) {
        return new PostCoordinateDTO(postCoordinate.getId(), postCoordinate.getLatitude(),
            postCoordinate.getLongitude());
    }

}