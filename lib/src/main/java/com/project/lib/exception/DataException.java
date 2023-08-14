package com.project.lib.exception;

public class DataException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* */
	public DataException() {
	
	}
	
	/* */
    public DataException(String message) {
        super(message);
    }
    
    public DataException(Throwable cause) {
        super(cause);
    }
}
