package com.wypl.image.infrastructure.aws;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.wypl.image.infrastructure.CloudStorageProvider;
import com.wypl.image.properties.AwsS3Properties;
import com.wypl.image.utils.ImageRemoveUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AwsS3Client implements CloudStorageProvider {

	private final AmazonS3Client amazonS3Client;

	private final AwsS3Properties awsS3Properties;

	/**
	 * 파일을 <a href="https://aws.amazon.com/ko/s3/">AWS S3</a>에 업로드한 후 업로드된 파일의 경로를 반환합니다.
	 *
	 * @param file AWS S3에 업로드할 파일
	 * @return AWS S3에 업로드된 파일의 경로
	 */
	@Override
	public String fileUpload(final File file) {
		amazonS3Client.putObject(awsS3Properties.getBucket(), file.getName(), file);
		String uploadImageUrl = amazonS3Client.getUrl(awsS3Properties.getBucket(), file.getName()).toString();
		ImageRemoveUtils.removeImages(file);
		return uploadImageUrl;
	}

	@Override
	public void filesRemove(List<String> fileNames) {
		List<DeleteObjectsRequest.KeyVersion> list = fileNames.stream()
				.map(DeleteObjectsRequest.KeyVersion::new)
				.toList();
		DeleteObjectsRequest deleteObjectRequest = new DeleteObjectsRequest(awsS3Properties.getBucket());
		deleteObjectRequest.setKeys(list);
		amazonS3Client.deleteObjects(deleteObjectRequest);
	}
}
