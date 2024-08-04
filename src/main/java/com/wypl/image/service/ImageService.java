package com.wypl.image.service;

import java.io.File;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.image.global.exception.ImageErrorCode;
import com.wypl.image.global.exception.ImageException;
import com.wypl.image.infrastructure.aws.AwsS3Client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	private final MagickImageConvert imageConvert;
	private final AwsS3Client imageUploadClient;

	/**
	 * 사용자가 요청한 이미지를 업로드한 뒤 업로드된 이미지의 URL 을 반환한다.<p>
	 *
	 * </br>
	 *    {@code ImageException}<p>
	 *	1. {@link #validateImageExtension(MultipartFile)}	이미지의 확장자가 잘못되었으면 예외를 던진다.
	 *
	 * @param file    사용자가 업로드 요청한 이미지 파일
	 * @return 업로드한 이미지의 URL
	 */
	public String saveImage(final MultipartFile file) {
		validateImageExtension(file);
		File avifImage = imageConvert.imageConvert(file);
		return imageUploadClient.imageUpload(avifImage);
	}

	private void validateImageExtension(final MultipartFile file) {
		String originalFileName = Optional.ofNullable(file.getOriginalFilename())
				.orElseThrow();
		String extension = originalFileName.substring(
						originalFileName.lastIndexOf("."))
				.toLowerCase();
		if (ImageExtension.notContains(extension)) {
			throw new ImageException(ImageErrorCode.NOT_ALLOWED_EXTENSION);
		}
	}

	private static class ImageExtension {
		private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif", ".avif");

		public static boolean notContains(final String extension) {
			return !ALLOWED_EXTENSIONS.contains(extension);
		}
	}
}
