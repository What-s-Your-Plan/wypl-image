package com.wypl.image.infrastructure.aws;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.wypl.image.infrastructure.ImageUploadable;
import com.wypl.utils.ImageRemoveUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AwsS3Client implements ImageUploadable {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 파일을 <a href="https://aws.amazon.com/ko/s3/">AWS S3</a>에 업로드한 후 업로드된 파일의 경로를 반환합니다.
	 *
	 * @param file AWS S3에 업로드할 파일
	 * @return AWS S3에 업로드된 파일의 경로
	 */
	@Override
	public String imageUpload(final File file) {
		amazonS3Client.putObject(bucket, file.getName(), file);
		String uploadImageUrl = amazonS3Client.getUrl(bucket, file.getName()).toString();
		ImageRemoveUtils.removeImages(file);
		return uploadImageUrl;
	}
}
