package com.develop_mouse.gummy_dang.post.domain.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.develop_mouse.gummy_dang.common.domain.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE post_coordinate SET active_status = 'DELETED' WHERE post_coordinate_id = ?")
@SQLRestriction("active_status <> 'DELETED'")
public class PostCoordinate extends BaseEntity {

	@Id @Column(name = "post_coordinate_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;

	public Long getId() {
		return id;
	}

	public Post getPost() {
		return post;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
}
