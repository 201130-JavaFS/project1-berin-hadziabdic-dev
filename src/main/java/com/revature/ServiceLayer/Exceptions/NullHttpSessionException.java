package com.revature.ServiceLayer.Exceptions;

public class NullHttpSessionException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "A null session was detected indicating the session has been destroyed or the user is session spoofing.";
    }
}
