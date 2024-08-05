package com.wypl.image.infrastructure.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.wypl.image.properties.AwsCredentialsProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AwsS3Configuration {
	private final AwsCredentialsProperties awsCredentialsProperties;

	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(
				awsCredentialsProperties.getAccessKey(),
				awsCredentialsProperties.getSecretKey()
		);
		return (AmazonS3Client)AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_NORTHEAST_2)
				.build();
	}
}
