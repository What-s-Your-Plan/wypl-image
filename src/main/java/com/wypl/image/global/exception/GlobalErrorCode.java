package com.wypl.image.global.exception;

import lombok.Getter;

@Getter
public enum GlobalErrorCode implements ErrorCode {
	PARENT_PROCESS_DEAD(500, "GLOBAL_001", "정상적으로 요청을 처리하지 못하였습니다.");
	private final int statusCode;
	private final String errorCode;
	private final String message;

	GlobalErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
