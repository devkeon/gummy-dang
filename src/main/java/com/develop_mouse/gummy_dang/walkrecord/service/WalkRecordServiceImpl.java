package com.develop_mouse.gummy_dang.walkrecord.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.walkrecord.domain.entity.Gummy;
import com.develop_mouse.gummy_dang.walkrecord.domain.entity.RecordImage;
import com.develop_mouse.gummy_dang.walkrecord.domain.entity.WalkRecord;
import com.develop_mouse.gummy_dang.walkrecord.domain.request.WalkRecordCreateRequest;
import com.develop_mouse.gummy_dang.walkrecord.domain.request.WalkRecordUpdateRequest;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordDetailListResponse;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordListResponse;
import com.develop_mouse.gummy_dang.walkrecord.domain.response.WalkRecordResponse;
import com.develop_mouse.gummy_dang.walkrecord.repository.GummyRepository;
import com.develop_mouse.gummy_dang.walkrecord.repository.RecordImageRepository;
import com.develop_mouse.gummy_dang.walkrecord.repository.WalkRecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WalkRecordServiceImpl implements WalkRecordService {

	private final WalkRecordRepository walkRecordRepository;
	private final MemberRepository memberRepository;
	private final GummyRepository gummyRepository;
	private final RecordImageRepository recordImageRepository;
	private final SecurityContextUtil securityContextUtil;

	private static final double EARTH_RADIUS = 6371000;
	private static final Random randomClass = new Random();
	private static final String DEFAULT_IMAGE = "profile/default.jpeg";

	@Value("${aws.s3.image-url}")
	private String DEFAULT_BUCKET_URL;

	@Override
	public Response<WalkRecordResponse> createWalkRecord(WalkRecordCreateRequest request) {

		Member contextMember = contextMember();

		/*
		 * TODO: reward 관련 로직 추가
		 */

		Long randomGummyId = 1 + randomClass.nextLong(5);

		Gummy randomGummy = gummyRepository.findAll()
			.stream()
			.filter(gummy -> gummy.getId().equals(randomGummyId))
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.GUMMY_IMG_NOT_FOUND));

		WalkRecord walkRecord = WalkRecord.builder()
			.gummy(randomGummy)
			.member(contextMember)
			.recordDate(request.getRecordDate())
			.departureLat(request.getDepartureLat())
			.departureLon(request.getDepartureLon())
			.arrivalLat(request.getArrivalLat())
			.arrivalLon(request.getArrivalLon())
			.build();

		WalkRecord savedWalkRecord = walkRecordRepository.save(walkRecord);

		double distance = haversineCalculator(savedWalkRecord.getDepartureLat(), savedWalkRecord.getDepartureLon(),
			savedWalkRecord.getArrivalLat(), savedWalkRecord.getArrivalLon());

		WalkRecordResponse responseDto = WalkRecordResponse.fromEntity(savedWalkRecord);

		String defaultURl = DEFAULT_BUCKET_URL + DEFAULT_IMAGE;

		RecordImage recordImage = RecordImage.builder()
			.walkRecord(savedWalkRecord)
			.imageUrl(defaultURl)
			.build();

		recordImageRepository.save(recordImage);

		responseDto.updateImageUrl(defaultURl);
		responseDto.updateDistance(distance);

		return Response.ok(responseDto);
	}

	@Override
	public Response<WalkRecordResponse> readWalkRecordDetail(Long walkRecordId) {

		WalkRecord walkRecord = walkRecordRepository.findWalkRecordById(walkRecordId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.WALK_RECORD_NOT_FOUND));

		Double distance = haversineCalculator(walkRecord.getDepartureLat(), walkRecord.getDepartureLon(),
			walkRecord.getArrivalLat(),
			walkRecord.getArrivalLon());

		WalkRecordResponse responseDto = WalkRecordResponse.fromEntity(walkRecord);
		responseDto.updateDistance(distance);
		responseDto.updateImageUrl(walkRecord.getRecordImages().stream().map(RecordImage::getImageUrl).findFirst().orElse(null));

		return Response.ok(responseDto);
	}

	@Override
	public Response<WalkRecordListResponse> readWalkRecords() {

		Member contextMember = contextMember();

		List<WalkRecord> walkRecords = walkRecordRepository.findWalkRecordsByMember(contextMember);

		return Response.ok(
			new WalkRecordListResponse(walkRecords.stream().map(WalkRecordDetailListResponse::fromEntity).toList()));
	}

	@Override
	public Response<WalkRecordResponse> updateWalkRecord(WalkRecordUpdateRequest request) {

		Member contextMember = contextMember();

		WalkRecord walkRecord = walkRecordRepository.findWalkRecordById(request.getWalkRecordId()).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.WALK_RECORD_NOT_FOUND));

		if (walkRecord.getMember() != contextMember){
			throw new BusinessException(ResponseCode.MEMBER_UNAUTHORIZED);
		}

		walkRecord.updateDepartureLat(request.getDepartureLat());
		walkRecord.updateDepartureLon(request.getDepartureLon());
		walkRecord.updateArrivalLat(request.getArrivalLat());
		walkRecord.updateArrivalLon(request.getArrivalLon());

		WalkRecordResponse responseDto = WalkRecordResponse.fromEntity(walkRecord);
		responseDto.updateImageUrl(walkRecord.getRecordImages().stream().map(RecordImage::getImageUrl).findFirst().orElse(null));
		responseDto.updateDistance(haversineCalculator(walkRecord.getDepartureLat(), walkRecord.getDepartureLon(),
			walkRecord.getArrivalLat(), walkRecord.getArrivalLon()));

		return Response.ok(responseDto);
	}

	@Override
	public Response<Void> deleteWalkRecord(Long walkRecordId) {

		Member contextMember = contextMember();

		WalkRecord walkRecord = walkRecordRepository.findWalkRecordById(walkRecordId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.WALK_RECORD_NOT_FOUND));

		if (walkRecord.getMember() != contextMember){
			throw new BusinessException(ResponseCode.MEMBER_UNAUTHORIZED);
		}

		walkRecordRepository.delete(walkRecord);

		return Response.ok();
	}

	private Member contextMember() {
		Long contextMemberId = securityContextUtil.getContextMemberInfo().getMemberId();
		return memberRepository.findById(contextMemberId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));
	}

	private Double haversineCalculator(double departureLat, double departureLon, double arrivalLat, double arrivalLon) {

		double dLat = Math.toRadians(arrivalLat - departureLat);
		double dLon = Math.toRadians(arrivalLon - departureLon);

		departureLat = Math.toRadians(departureLat);
		arrivalLat = Math.toRadians(arrivalLat);

		double a = Math.pow(Math.sin(dLat / 2), 2)
			+ Math.pow(Math.sin(dLon / 2), 2) * Math.cos(departureLat) * Math.cos(arrivalLat);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}

}
