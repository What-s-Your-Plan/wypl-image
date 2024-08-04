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

	@Override
	public String imageUpload(final File file) {
		amazonS3Client.putObject(bucket, file.getName(), file);
		String uploadImageUrl = amazonS3Client.getUrl(bucket, file.getName()).toString();
		ImageRemoveUtils.removeImages(file);
		return uploadImageUrl;
	}
}
