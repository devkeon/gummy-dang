package com.develop_mouse.gummy_dang.walkrecord.domain.response;

import java.time.LocalDate;

import com.develop_mouse.gummy_dang.walkrecord.domain.entity.WalkRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkRecordResponse {

	private Long walkRecordId;
	private LocalDate recordTime;
	private Double distance;
	private Double departureLat;
	private Double departureLon;
	private Double arrivalLat;
	private Double arrivalLon;
	private String imageUrl;

	public static WalkRecordResponse fromEntity(WalkRecord walkRecord){
		return WalkRecordResponse.builder()
			.walkRecordId(walkRecord.getId())
			.recordTime(walkRecord.getRecordDate())
			.departureLat(walkRecord.getDepartureLat())
			.departureLon(walkRecord.getDepartureLon())
			.arrivalLat(walkRecord.getArrivalLat())
			.arrivalLon(walkRecord.getArrivalLon())
			.build();
	}

	public void updateImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void updateDistance(Double distance) {
		this.distance = distance;
	}

}
