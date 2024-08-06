package com.wypl.image.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseMessage<T> {
	@JsonProperty("message")
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("body")
	private T body;

	public ResponseMessage() {
		this.message = null;
		this.body = null;
	}

	//메세지만 있는 경우
	public ResponseMessage(final String message) {
		this.message = message;
		this.body = null;
	}

	//메세지와 데이터 모두 있는 경우
	public ResponseMessage(final String message, T body) {
		this.message = message;
		this.body = body;
	}

	public static ResponseMessage<Void> onlyMessage(
			final String message
	) {
		return new ResponseMessage<>(message);
	}

	public static <T> ResponseMessage<T> withBody(
			final String message,
			final T body
	) {
		return new ResponseMessage<>(message, body);
	}
}
