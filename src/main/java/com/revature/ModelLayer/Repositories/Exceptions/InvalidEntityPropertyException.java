package com.revature.ModelLayer.Repositories.Exceptions;

/**
 * The IEPE is thrown when the application passes column params to execute a
 * query. but said params are invalid states. An example of an appropriate
 * context to throw this exception in is a null parameter bound to query using
 * said parameter in a PK role.
 */
public class InvalidEntityPropertyException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidEntityPropertyException() {
        super("The application attempted to invoke a programmatic or hybrid query with a query paramater in an invalid state. I.e, a serial was set to a negative number or a username string was null, as examples.");
    }

    public InvalidEntityPropertyException(String msg) {
        super(msg);
    }

}
