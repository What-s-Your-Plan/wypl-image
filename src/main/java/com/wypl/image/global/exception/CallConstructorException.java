package com.wypl.image.global.exception;

public class CallConstructorException extends RuntimeException {
	public CallConstructorException() {
		super("유틸 클래스는 생성자를 호출할 수 없습니다.");
	}
}
