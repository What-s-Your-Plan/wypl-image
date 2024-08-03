package com.wypl.image;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface ImageConvertible {
	/**
	 * 이미지를 `avif`확장자로 변환한다.
	 *
	 * @param file 변환하는 원본 이미지
	 * @return `avif`로 변환된 이미지
	 */
	File imageConvert(final MultipartFile file);
}
