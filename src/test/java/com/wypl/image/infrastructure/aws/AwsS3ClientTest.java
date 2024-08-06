package com.wypl.image.infrastructure.aws;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.wypl.image.properties.AwsS3Properties;

@ExtendWith(MockitoExtension.class)
class AwsS3ClientTest {
	@InjectMocks
	private AwsS3Client awsS3Client;

	@Mock
	private AmazonS3Client amazonS3Client;

	@Mock
	private AwsS3Properties properties;

	@BeforeEach
	void setUp() {
		given(properties.getBucket()).willReturn("bucket");
	}

	private File prepareImageCopy() throws IOException {
		Files.createDirectories(Path.of("src/test/magick"));
		File originalImageFile = new File("src/test/resources/image/image.avif");
		File copyFile = new File("src/test/magick/image.avif");
		Files.copy(originalImageFile.toPath(), copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return copyFile;
	}

	@DisplayName("AWS S3에 파일을 업로드한다.")
	@Test
	void fileUploadTest() throws IOException {
		/* Given */
		File uploadImage = prepareImageCopy();

		given(amazonS3Client.putObject(any(String.class), eq(uploadImage.getName()), any(File.class)))
				.willReturn(null);
		given(amazonS3Client.getUrl(any(String.class), eq(uploadImage.getName()))).willReturn(
				new URL("https://s3.aws.com/image.avif"));

		/* When & Then */
		assertThatCode(() -> awsS3Client.fileUpload(uploadImage))
				.doesNotThrowAnyException();
	}

	@DisplayName("AWS S3의 파일을 삭제한다.")
	@Test
	void filesRemoveTest() {
		/* Given */
		List<String> fileNames = new ArrayList<>(List.of("image01.avif", "image02.avif"));
		given(amazonS3Client.deleteObjects(any(DeleteObjectsRequest.class))).willReturn(null);

		/* When & Then */
		Assertions.assertThatCode(() -> awsS3Client.filesRemove(fileNames))
				.doesNotThrowAnyException();
	}
}