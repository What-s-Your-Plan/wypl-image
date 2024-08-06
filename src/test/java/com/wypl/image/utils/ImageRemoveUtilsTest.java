package com.wypl.image.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageRemoveUtilsTest {

	private File prepareImageCopy() throws IOException {
		Files.createDirectories(Path.of("src/test/magick"));
		File originalImageFile = new File("src/test/resources/image/image.avif");
		File copyFile = new File("src/test/magick/image.avif");
		Files.copy(originalImageFile.toPath(), copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return copyFile;
	}

	@DisplayName("이미지 파일을 삭제하는데 성공한다.")
	@Test
	void imageRemoveTest() throws IOException {
		/* Given */
		File imageFile = prepareImageCopy();

		/* When & Then */
		Assertions.assertThatCode(() -> ImageRemoveUtils.removeImages(imageFile))
				.doesNotThrowAnyException();
	}
}