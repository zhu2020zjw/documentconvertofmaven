package com.zjw.convert.exception;

public class SuffixException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SuffixException() {
		super();
	}
	
	public SuffixException(String msg) {
		super(msg);
	}
	
	public SuffixException(String msg, Exception ex) {
		super(msg, ex);
	}
}
