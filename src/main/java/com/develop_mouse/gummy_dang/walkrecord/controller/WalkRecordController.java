package com.develop_mouse.gummy_dang.walkrecord.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.walkrecord.domain.request.WalkRecordCreateRequest;
import com.develop_mouse.gummy_dang.walkrecord.domain.request.WalkRecordUpdateRequest;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordListResponse;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordResponse;
import com.develop_mouse.gummy_dang.walkrecord.service.WalkRecordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WalkRecordController {

	private final WalkRecordService walkRecordService;

	@PostMapping("/record")
	Response<WalkRecordResponse> createWalkRecord(@Validated @RequestBody WalkRecordCreateRequest request) {
		return walkRecordService.createWalkRecord(request);
	}

	@GetMapping("/record")
	Response<WalkRecordResponse> getWalkRecordDetail(@RequestParam(value = "recordId") Long recordId) {
		return walkRecordService.readWalkRecordDetail(recordId);
	}

	@GetMapping("/records")
	Response<WalkRecordListResponse> getWalkRecords() {
		return walkRecordService.readWalkRecords();
	}

	@PatchMapping("/record")
	Response<WalkRecordResponse> updateWalkRecord(@Validated @RequestBody WalkRecordUpdateRequest request) {
		return walkRecordService.updateWalkRecord(request);
	}

	@DeleteMapping("/record")
	Response<Void> deleteWalkRecord(@RequestParam(value = "recordId") Long walkRecordId) {
		return walkRecordService.deleteWalkRecord(walkRecordId);
	}


}
