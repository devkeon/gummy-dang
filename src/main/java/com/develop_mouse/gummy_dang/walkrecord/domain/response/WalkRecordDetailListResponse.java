package com.develop_mouse.gummy_dang.walkrecord.domain.response;

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
public class WalkRecordDetailListResponse {

	private Long walkRecordId;
	private String gummyUrl;

	public static WalkRecordDetailListResponse fromEntity(WalkRecord walkRecord) {
		return new WalkRecordDetailListResponse(walkRecord.getId(), walkRecord.getGummy().getImageUrl());
	}

}
