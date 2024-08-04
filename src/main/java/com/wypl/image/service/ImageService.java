package com.wypl.image.service;

import java.io.File;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.image.infrastructure.aws.AwsS3Client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	private final MagickImageConvert imageConvert;
	private final AwsS3Client imageUploadClient;

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
			throw new RuntimeException("올바르지 않은 이미지 확장자입니다.");
		}
	}

	private static class ImageExtension {
		private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif", ".avif");

		public static boolean notContains(final String extension) {
			return !ALLOWED_EXTENSIONS.contains(extension);
		}
	}
}
