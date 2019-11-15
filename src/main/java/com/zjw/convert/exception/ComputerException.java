package com.zjw.convert.exception;

public class ComputerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ComputerException() {
		super();
	}
	
	public ComputerException(String msg) {
		super(msg);
	}
	
	public ComputerException(String msg, Exception ex) {
		super(msg, ex);
	}
}
