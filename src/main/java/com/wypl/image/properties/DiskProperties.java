package com.wypl.image.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "working.directory")
public class DiskProperties {
	private String absolutePath;
}
