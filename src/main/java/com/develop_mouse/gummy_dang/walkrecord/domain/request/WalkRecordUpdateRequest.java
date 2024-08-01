package com.develop_mouse.gummy_dang.walkrecord.domain.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkRecordUpdateRequest {

	@NotNull
	private Long walkRecordId;
	private LocalDate recordDate;
	private Double departureLat;
	private Double departureLon;
	private Double arrivalLat;
	private Double arrivalLon;

}
