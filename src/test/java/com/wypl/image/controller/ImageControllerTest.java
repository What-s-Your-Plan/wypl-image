package com.wypl.image.controller;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import com.wypl.image.common.ControllerTest;
import com.wypl.image.fixture.ImageFixture;
import com.wypl.image.service.ImageService;

class ImageControllerTest extends ControllerTest {

	@MockBean
	private ImageService imageService;

	@DisplayName("이미지 업로드, POST - file/v2/images")
	@Test
	void uploadImage() throws Exception {
		/* Given */
		given(imageService.saveImage(any(MultipartFile.class)))
				.willReturn("https://bucket.s3.aws.com/image.avif");

		/* When */
		ResultActions actions = mockMvc.perform(
				RestDocumentationRequestBuilders.multipart("/file/v2/images")
						.file(ImageFixture.PNG_IMAGE.getMockMultipartFile())
		);

		/* Then */
		actions.andDo(MockMvcResultHandlers.print())
				.andDo(MockMvcRestDocumentation.document("file/v2/images",
						Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
						Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
						RequestDocumentation.requestParts(
								RequestDocumentation.partWithName("image").description("업로드 요청한 이미지 파일")
						),
						PayloadDocumentation.responseFields(
								PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
										.description("응답 메시지"),
								PayloadDocumentation.fieldWithPath("body.image_url").type(JsonFieldType.STRING)
										.description("업로드한 이미지 URL")
						)
				)).andExpect(MockMvcResultMatchers.status().isCreated());
	}
}