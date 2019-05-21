package com.w2g.exception;

/**
 * 业务异常
 * Created by W2G on 2017/2/4.
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 201705081629L;

	private String message;

    private Exception e;

    public ServiceException(String message) {
        super(message);
        this.setMessage(message);
    }

    public ServiceException(Exception e) {
        this.e = e;
    }

    public ServiceException(String message, String message1) {
        super(message);
        this.message = message1;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
    
}
