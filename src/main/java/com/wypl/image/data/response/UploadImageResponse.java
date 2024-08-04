package com.wypl.image.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UploadImageResponse(
		@JsonProperty("image_url") String imageUrl
) {
}
