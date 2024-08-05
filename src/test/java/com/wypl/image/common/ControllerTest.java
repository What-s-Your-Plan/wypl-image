package com.wypl.image.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.wypl.image.controller.ImageController;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest({
		ImageController.class
})
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;
}
