package com.wypl.image.service;

import static org.mockito.BDDMockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.image.data.request.DeleteImageRequest;
import com.wypl.image.fixture.ImageFixture;
import com.wypl.image.global.exception.ImageErrorCode;
import com.wypl.image.global.exception.ImageException;
import com.wypl.image.infrastructure.aws.AwsS3Client;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

	@InjectMocks
	private ImageServiceImpl imageService;

	@Mock
	private ImageMagickConvert convert;

	@Mock
	private AwsS3Client client;

	@DisplayName("이미지 확장자 검증에 성공한다.")
	@ParameterizedTest
	@EnumSource(ImageFixture.class)
	void validateImageExtensionSuccess(ImageFixture fixture) {
		/* Given */
		MockMultipartFile file = fixture.getMockMultipartFile();
		given(convert.imageConvert(any(MultipartFile.class))).willReturn(new File("MOCK_FILE"));
		given(client.fileUpload(any(File.class))).willReturn("MOCK_IMAGE_URL");

		/* When & Then */
		Assertions.assertThatCode(() -> imageService.saveImage(file))
				.doesNotThrowAnyException();
	}

	@DisplayName("이미지 확장자 검증에 실패한다.")
	@Test
	void validateImageExtensionFailure() {
		try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/image/image.HEIF")) {
			/* Given */
			MockMultipartFile file = new MockMultipartFile("image", "image.HEIF", "image/HEIF", fileInputStream);

			/* When & Then */
			Assertions.assertThatThrownBy(() -> imageService.saveImage(file))
					.isInstanceOf(ImageException.class)
					.hasMessageContaining(ImageErrorCode.NOT_ALLOWED_EXTENSION.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@DisplayName("이미지를 삭제한다.")
	@Test
	void removeImagesTest() {
		/* Given */
		DeleteImageRequest request = new DeleteImageRequest(new ArrayList<>(List.of(
				"https://bucket.region.s3.aws.com/image1.avif",
				"https://bucket.region.s3.aws.com/image2.avif"
		)));
		doNothing().when(client).filesRemove(any());

		/* When & Then */
		Assertions.assertThatCode(() -> imageService.removeImages(request))
				.doesNotThrowAnyException();
	}
}