package com.zjw.convert.exception;

public class NoPropertiesException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoPropertiesException() {
		super();
	}
	
	public NoPropertiesException(String msg) {
		super(msg);
	}

	public NoPropertiesException(String msg, Exception ex) {
		super(msg, ex);
	}
}
