package com.revature.ModelLayer.DTO.Exceptions;

public class IncompleteRecieptDTOEntityConversion extends InvalidEntityToDTOConversionException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "Attempted to create a new Reciept empty with some required fields set to null.";
    }

}
