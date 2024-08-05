package com.wypl.image.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class AwsS3Properties {
	private String bucket;
}
