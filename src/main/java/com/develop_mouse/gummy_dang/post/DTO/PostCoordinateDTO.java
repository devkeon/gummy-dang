package com.develop_mouse.gummy_dang.post.DTO;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Double latitude; // 위도
    private Double longitude; // 경도
}