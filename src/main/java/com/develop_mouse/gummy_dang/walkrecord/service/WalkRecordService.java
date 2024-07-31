package com.develop_mouse.gummy_dang.walkrecord.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.walkrecord.domain.request.WalkRecordCreateRequest;
import com.develop_mouse.gummy_dang.walkrecord.domain.request.WalkRecordUpdateRequest;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordListResponse;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordResponse;

public interface WalkRecordService {

	Response<WalkRecordResponse> createWalkRecord(WalkRecordCreateRequest request);

	Response<WalkRecordResponse> readWalkRecordDetail(Long walkRecordId);

	Response<WalkRecordListResponse> readWalkRecords();

	Response<WalkRecordResponse> updateWalkRecord(WalkRecordUpdateRequest request);

	Response<Void> deleteWalkRecord(Long walkRecordId);
}
