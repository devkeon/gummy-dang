package com.develop_mouse.gummy_dang.common.domain.entity;

import java.time.LocalDateTime;

import com.develop_mouse.gummy_dang.common.domain.ActiveStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@Enumerated(value = EnumType.STRING)
	private ActiveStatus activeStatus;

	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
