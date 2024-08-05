package com.wypl.image.service;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.image.fixture.ImageFixture;
import com.wypl.image.utils.ImageRemoveUtils;

@SpringBootTest
class MagickImageConvertTest {

	@Autowired
	private MagickImageConvert magickImageConvert;

	private File avifImageFile;

	@AfterEach
	void removeTestImageFile() {
		ImageRemoveUtils.removeImages(avifImageFile);
	}

	@DisplayName("MagickImage 를 사용하여 이미지를 변환 및 압축한다.")
	@ParameterizedTest
	@EnumSource(ImageFixture.class)
	void imageTest(ImageFixture fixture) {
		Assertions.assertThatCode(
						() -> avifImageFile = magickImageConvert.imageConvert(fixture.getMockMultipartFile()))
				.doesNotThrowAnyException();
	}
}