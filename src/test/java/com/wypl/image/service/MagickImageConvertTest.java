package com.wypl.image.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.image.fixture.ImageFixture;

@SpringBootTest
class MagickImageConvertTest {

	@Autowired
	private MagickImageConvert magickImageConvert;

	@DisplayName("MagickImage 를 사용하여 이미지를 변환 및 압축한다.")
	@ParameterizedTest
	@EnumSource(ImageFixture.class)
	void imageTest(ImageFixture fixture) {
		Assertions.assertThatCode(() -> magickImageConvert.imageConvert(fixture.getMockMultipartFile()))
				.doesNotThrowAnyException();
	}
}