package br.ufpr.model;

import java.io.Serializable;

public class ResponseSaga implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private Object data;
	
	public ResponseSaga() {
		super();
	}
	
	public ResponseSaga(boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString() {
        return "ResponseSaga [" +
                "success=" + success +
                ", data='" + data + '\'' +
                ']';
    }
	  
}
