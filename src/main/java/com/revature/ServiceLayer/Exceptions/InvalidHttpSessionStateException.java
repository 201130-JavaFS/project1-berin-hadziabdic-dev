package com.revature.ServiceLayer.Exceptions;

public class InvalidHttpSessionStateException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidHttpSessionStateException(String string) {
        super(string);
    }

    public InvalidHttpSessionStateException() {
        super("A stored session object was found lacking a necessary attribute.");
    }

}
