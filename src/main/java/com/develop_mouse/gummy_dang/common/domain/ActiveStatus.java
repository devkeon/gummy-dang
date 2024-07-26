package com.develop_mouse.gummy_dang.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActiveStatus {

	ACTIVATED("ACTIVATED"), DELETED("DELETED");

	private final String activeStatus;

}
