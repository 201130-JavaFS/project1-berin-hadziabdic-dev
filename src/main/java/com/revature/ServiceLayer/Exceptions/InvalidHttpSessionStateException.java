package com.revature.ServiceLayer.Exceptions;

public class InvalidHttpSessionStateException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "A stored session object was found lacking a necessary attribute.";
    }

}
