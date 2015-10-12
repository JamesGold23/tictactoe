package jg.tictactoe.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

	private String errorMessage;
	private int errorCode;
	
	public ErrorMessage(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
	@JsonProperty
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@JsonProperty
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
