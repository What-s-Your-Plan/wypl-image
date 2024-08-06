package com.wypl.image.service;

import org.springframework.web.multipart.MultipartFile;

import com.wypl.image.data.request.DeleteImageRequest;

public interface ImageService {
	String saveImage(final MultipartFile file);

	void removeImages(final DeleteImageRequest request);
}
