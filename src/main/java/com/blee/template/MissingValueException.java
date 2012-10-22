package com.blee.template;

public class MissingValueException extends RuntimeException {

    private static final long serialVersionUID = -8234139482803839292L;

    public MissingValueException() {
    }

    public MissingValueException(String message) {
        super(message);
    }

}
