package com.blee.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -9118864468540055821L;
    
    public ServiceException() {
        super();
    }
    
    public ServiceException(String msg) {
        super(msg);
    }
    
    public ServiceException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public ServiceException(Throwable t) {
        super(t);
    }

}
