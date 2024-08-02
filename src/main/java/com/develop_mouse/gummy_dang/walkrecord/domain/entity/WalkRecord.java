package com.develop_mouse.gummy_dang.walkrecord.domain.entity;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.develop_mouse.gummy_dang.common.domain.entity.BaseEntity;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE walk_record SET active_status = 'DELETED' WHERE walk_record_id = ?")
@SQLRestriction("active_status <> 'DELETED'")
public class WalkRecord extends BaseEntity {

	@Id
	@Column(name = "walk_record_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "gummy_id")
	private Gummy gummy;

	@OneToMany(mappedBy = "walkRecord")
	private Set<RecordImage> recordImages;

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

	public void updateDepartureLat(Double editDepartureLat) {
		this.departureLat = editDepartureLat;
	}

	public void updateDepartureLon(Double editDepartureLon) {
		this.departureLon = editDepartureLon;
	}

	public void updateArrivalLat(Double editArrivalLat) {
		this.arrivalLat = editArrivalLat;
	}

	public void updateArrivalLon(Double editArrivalLon) {
		this.arrivalLon = editArrivalLon;
	}

	public void updateRecordDate(LocalDate recordDate) {
		this.recordDate = recordDate;
	}

}
