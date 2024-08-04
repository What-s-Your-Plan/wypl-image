package com.wypl.image.global.exception;

import lombok.Getter;

@Getter
public enum ImageErrorCode implements ErrorCode {
	NOT_ALLOWED_EXTENSION(400, "IMAGE_001", "이미지의 확장자가 잘못되었습니다.");
	private final int statusCode;
	private final String errorCode;
	private final String message;

	ImageErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
