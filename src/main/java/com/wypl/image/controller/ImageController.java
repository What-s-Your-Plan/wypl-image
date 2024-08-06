package com.wypl.image.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.image.data.response.UploadImageResponse;
import com.wypl.image.global.common.ResponseMessage;
import com.wypl.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/file")
@RestController
public class ImageController {

	private final ImageService imageService;

	@PostMapping("/v2/images")
	public ResponseEntity<ResponseMessage<UploadImageResponse>> uploadImage(
			// TODO: 인증 기능 추가 필요
			@RequestPart("image") MultipartFile file
	) {
		String savedImageUrl = imageService.saveImage(file);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ResponseMessage.withBody(
						"사진 업로드가 정상적으로 처리되었습니다.",
						new UploadImageResponse(savedImageUrl)));
	}
}
