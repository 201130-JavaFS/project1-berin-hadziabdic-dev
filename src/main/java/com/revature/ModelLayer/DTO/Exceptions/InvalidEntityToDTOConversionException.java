package com.revature.ModelLayer.DTO.Exceptions;

public class InvalidEntityToDTOConversionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "Attempting to unpack a null or otherwise invalid Entity into a DTO. ";
    }

}
