package com.wypl.image.service;

import static org.mockito.BDDMockito.*;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wypl.image.fixture.ImageFixture;
import com.wypl.image.properties.DiskProperties;
import com.wypl.image.utils.ImageRemoveUtils;

@ExtendWith(MockitoExtension.class)
class MagickImageConvertTest {

	@InjectMocks
	private MagickImageConvert magickImageConvert;

	@Mock
	private DiskProperties properties;

	private File avifImageFile;

	@BeforeEach
	void setUp() {
		given(properties.getAbsolutePath()).willReturn("src/test/magick");
	}

	@DisplayName("MagickImage 를 사용하여 이미지를 변환 및 압축한다.")
	@ParameterizedTest
	@EnumSource(ImageFixture.class)
	void imageTest(ImageFixture fixture) {
		/* When & Then */
		Assertions.assertThatCode(
						() -> avifImageFile = magickImageConvert.imageConvert(fixture.getMockMultipartFile()))
				.doesNotThrowAnyException();

		/* After */
		ImageRemoveUtils.removeImages(avifImageFile);
	}
}