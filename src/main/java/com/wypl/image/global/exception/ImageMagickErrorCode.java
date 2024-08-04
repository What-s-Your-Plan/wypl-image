package com.wypl.image.global.exception;

import lombok.Getter;

@Getter
public enum ImageMagickErrorCode implements ErrorCode {
	ORIGINAL_IMAGE_PREPARE_ERROR(500, "IMAGE_MAGICK_001", "이미지 처리중 오류가 발생했습니다."),
	INVALID_IMAGE_PATH(500, "IMAGE_MAGICK_002", "이미지 처리중 오류가 발생했습니다."),
	INVALID_FILE_PATH(500, "IMAGE_MAGICK_003", "이미지 처리중 오류가 발생했습니다."),
	NOT_EXISTED_COMMAND(500, "IMAGE_MAGICK_004", "이미지 처리중 오류가 발생했습니다.");

	private final int statusCode;
	private final String errorCode;
	private final String message;

	ImageMagickErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
