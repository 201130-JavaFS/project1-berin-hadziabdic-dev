package com.revature.ServiceLayer.Exceptions;

public class InvalidPropertyValueException extends Exception {

    public InvalidPropertyValueException() {
        super("An application property was discovered to be an invalid state and thus could not be processed. Any associated requests or responses with said property have been terminated.");
    }

    public InvalidPropertyValueException(String msg) {
        super(msg);
    }

}
