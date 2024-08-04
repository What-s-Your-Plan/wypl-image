package com.wypl.image.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.wypl.image.global.exception.CallConstructorException;
import com.wypl.image.global.exception.GlobalErrorCode;
import com.wypl.image.global.exception.GlobalException;

@Component
public class ImageRemoveUtils {

	private ImageRemoveUtils() {
		throw new CallConstructorException();
	}

	/**
	 *	해당 파일이 있는 위치의 파일과 폴더를 삭제합니다.
	 *
	 * @param file    삭제 요청하는 파일
	 *
	 * @throws GlobalException 파일 삭제에 실패하면 예외를 던진다.
	 */
	public static void removeImages(final File file) {
		try {
			Path directoryPath = Paths.get(file.getAbsolutePath().replace(file.getName(), ""));
			deleteDirectoryRecursively(directoryPath);
		} catch (IOException e) {
			throw new GlobalException(GlobalErrorCode.FAILURE_DELETE_FILE);
		}
	}

	private static void deleteDirectoryRecursively(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (var stream = Files.list(path)) {
				for (Path subPath : (Iterable<Path>)stream::iterator) {
					deleteDirectoryRecursively(subPath);
				}
			}
			Files.delete(path);
		} else {
			Files.delete(path);
		}
	}
}