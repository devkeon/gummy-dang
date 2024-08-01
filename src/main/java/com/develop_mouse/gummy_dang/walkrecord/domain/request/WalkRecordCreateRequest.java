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
public class WalkRecordCreateRequest {

	@NotNull
	private Double departureLat;
	@NotNull
	private Double departureLon;
	@NotNull
	private Double arrivalLat;
	@NotNull
	private Double arrivalLon;
	@NotNull
	private LocalDate recordDate;

}
