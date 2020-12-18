package com.revature.ServiceLayer.Exceptions;

public class FilterFoundNoAuthenticationHandlerForAuthenticatedResourceException extends Exception {

    @Override
    public String getMessage() {
        return "A security filter was appointed to a subset of authenticated apis, but it could not find a handler for said endpoint.";
    }

}
