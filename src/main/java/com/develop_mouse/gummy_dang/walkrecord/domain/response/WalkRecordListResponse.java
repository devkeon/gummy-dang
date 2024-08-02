package com.develop_mouse.gummy_dang.walkrecord.domain.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkRecordListResponse {

	private List<WalkRecordDetailListResponse> walkRecordInfos;

}
