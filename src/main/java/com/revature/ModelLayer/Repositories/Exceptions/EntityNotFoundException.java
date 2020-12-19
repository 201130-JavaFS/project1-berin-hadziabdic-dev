package com.revature.ModelLayer.Repositories.Exceptions;

/**
 * The ENFE exception is thrown in circumctances where we are sure an entity
 * will exist but does not. This could indicate the user is tampering with
 * values they send from the front end, or there are concurrency issues in the
 * database.
 */
public class EntityNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException() {
        super("An entity which was expected to exist was not discovered indicating a user is tampering with data or concurrency issues.");
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }

}
