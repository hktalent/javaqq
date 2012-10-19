package com.blee.exception;

public class HttpException extends RuntimeException {

    private static final long serialVersionUID = 3031800521507456906L;

    public HttpException() {
        super();
    }
    
    public HttpException(String msg) {
        super(msg);
    }
    
    public HttpException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public HttpException(Throwable t) {
        super(t);
    }
    
}
