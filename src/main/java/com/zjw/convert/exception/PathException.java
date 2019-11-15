package com.zjw.convert.exception;

public class PathException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PathException() {
		super();
	}
	
	public PathException(String msg) {
		super(msg);
	}
	
	public PathException(String msg, Exception ex) {
		super(msg, ex);
	}
}
