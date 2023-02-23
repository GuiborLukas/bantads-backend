package br.ufpr.model;

import java.io.Serializable;

public class ResponseSaga implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private String message;
	
	public ResponseSaga() {
		super();
	}
	
	public ResponseSaga(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
        return "ResponseSaga [" +
                "success=" + success +
                ", message='" + message + '\'' +
                ']';
    }
	  
}
