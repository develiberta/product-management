package com.project.order.exception;

public class UnauthorizedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* */
	public UnauthorizedException() {
	
	}
	
	/* */
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
    
    
    
    public static UnauthorizedException defaultUnauthorizedException() {
    	return new UnauthorizedException("권한이 없습니다.");
    }
}
