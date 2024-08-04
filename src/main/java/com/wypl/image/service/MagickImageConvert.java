package com.wypl.image.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MagickImageConvert implements ImageConvertible {

	private static final int SUCCESS_EXIT_NUMBER = 0;

	@Value("${working.directory.absolute-path}")
	private String workingAbsolutePath;

	/**
	 *	이미지를 `avif`확장자로 변환합니다.<p>
	 *	하위 메서드에서 예외를 던지는 경우는 다음과 같습니다.<p>
	 * </br>
	 *    {@code IOException}<p>
	 *	1. {@link #prepareOriginalImage} 원본 이미지 사전작업중 임시 디렉토리를 생성하지 못하거나 파일을 디스크로 복사하지 못하면 예외를 던진다.<p>
	 *	2. {@link #imageConvertProcess} magick 명령어가 잘못되면 예외를 던진다.<p>
	 * </br>
	 *    {@code InterruptedException}<p>
	 *	1. {@link #imageConvertProcess} 작업중인 부모 프로세스가 죽어서 멈추면 예외를 던진다.
	 *
	 * @param file 변환하는 원본 이미지
	 * @return `avif`로 변환된 이미지
	 */
	@Override
	public File imageConvert(final MultipartFile file) {
		String uuid = UUID.randomUUID().toString();
		Path originalImagePath = prepareOriginalImage(uuid, file);
		Path avifImagePath = prepareAvifImage(uuid);
		imageConvertProcess(originalImagePath, avifImagePath);
		return avifImagePath.toFile();
	}

	private Path prepareOriginalImage(final String uuid, final MultipartFile file) {
		try {
			Path workingDirectory = Paths.get(workingAbsolutePath, uuid);
			Files.createDirectories(workingDirectory);
			Path originalImagePath = workingDirectory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), originalImagePath);
			return originalImagePath;
		} catch (IOException e) {
			throw new RuntimeException("내부 서버 오류입니다.");    // TODO: 예외 처리 수정 필요
		}
	}

	private Path prepareAvifImage(final String uuid) {
		Path workingDirectory = Paths.get(workingAbsolutePath, uuid);
		String avifImageName = uuid + ".avif";
		return workingDirectory.resolve(avifImageName);
	}

	private void imageConvertProcess(Path originalImagePath, Path avifImagePath) {
		ProcessBuilder processBuilder = new ProcessBuilder(
				"magick",
				originalImagePath.toAbsolutePath().toString(),
				"-quality", "50",
				avifImagePath.toAbsolutePath().toString()
		);
		try {
			Process process = processBuilder.start();
			if (process.waitFor() != SUCCESS_EXIT_NUMBER) {
				throw new RuntimeException("이미지 처리에 실패하였습니다.");
			}
		} catch (IOException e) {
			throw new RuntimeException("magick 명령어가 잘못되었습니다.");
		} catch (InterruptedException e) {
			throw new RuntimeException("부모 프로세스가 죽었습니다.");
		}
	}
}
