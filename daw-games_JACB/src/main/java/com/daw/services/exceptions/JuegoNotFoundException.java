package com.daw.services.exceptions;

public class JuegoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6123456789098765432L;

	public JuegoNotFoundException(String message) {
		super(message);
	}

}