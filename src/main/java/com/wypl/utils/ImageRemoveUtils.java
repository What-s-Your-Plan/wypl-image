package com.wypl.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class ImageRemoveUtils {

	private ImageRemoveUtils() {
		// TODO: 해당 클래스는 유틸이므로 생성자가 호출되면 안됩니다.
	}

	public static void removeImages(final File file) {
		try {
			Path directoryPath = Paths.get(file.getAbsolutePath().replace(file.getName(), ""));
			deleteDirectoryRecursively(directoryPath);
		} catch (IOException e) {
			throw new RuntimeException("파일 삭제에 실패하였습니다.");
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