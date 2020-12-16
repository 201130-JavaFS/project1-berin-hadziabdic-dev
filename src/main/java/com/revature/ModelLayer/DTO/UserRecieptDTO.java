package com.revature.ModelLayer.DTO;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ModelLayer.DTO.Exceptions.IncompleteRecieptDTOEntityConversion;
import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;
import com.revature.ModelLayer.Entities.RecieptEntity;

import org.apache.commons.io.IOUtils;

public class UserRecieptDTO {
    public long ticketNumber;
    public Date dateSubmitted;
    public Date dateResolved;
    public String description;
    public double amount;
    public int status;
    public String requestedBy;
    public String processedBy;
    public int type;

    private static ObjectMapper jsonPackerUnpacker = new ObjectMapper();

    public UserRecieptDTO(RecieptEntity recieptEntity) throws InvalidEntityToDTOConversionException {
        if (recieptEntity == null)
            throw new InvalidEntityToDTOConversionException();

        this.ticketNumber = recieptEntity.getRemb_id();
        this.dateSubmitted = recieptEntity.getReimb_submitted();
        this.dateResolved = recieptEntity.getReimb_resolved();
        this.description = recieptEntity.getReimb_description();
        this.amount = recieptEntity.getReimb_amount();
        this.status = recieptEntity.getReimb_status_id();
        this.requestedBy = recieptEntity.getReimb_author();
        this.processedBy = recieptEntity.getReimb_resolver();
        this.type = recieptEntity.getReimb_type_id();
    }

    public UserRecieptDTO(HttpServletRequest req, boolean isUsedForCreation)
            throws JsonMappingException, JsonProcessingException, IOException, InvalidEntityToDTOConversionException {
        this(req);

        if (isUsedForCreation && !this.isValidDTO_ForCreation()) {
            throw new IncompleteRecieptDTOEntityConversion();

        }

    }

    public UserRecieptDTO(HttpServletRequest req)
            throws JsonMappingException, JsonProcessingException, IOException, InvalidEntityToDTOConversionException {

        UserRecieptDTO buffer = this.jsonPackerUnpacker.readValue(IOUtils.toString(req.getReader()),
                UserRecieptDTO.class);

        if (buffer.ticketNumber < 0 || buffer.requestedBy == null || buffer.requestedBy.length() == 0)
            throw new InvalidEntityToDTOConversionException();

        if (buffer != null) {
            this.ticketNumber = buffer.ticketNumber;
            this.dateSubmitted = buffer.dateSubmitted;
            this.dateResolved = buffer.dateResolved;
            this.description = buffer.description;
            this.amount = buffer.amount;
            this.status = buffer.status;
            this.requestedBy = buffer.requestedBy;
            this.processedBy = buffer.processedBy;

        }

    }

    private boolean isValidDTO_ForCreation() {
        boolean validDates = this.dateSubmitted != null;
        boolean validStatus = this.status >= 0;
        boolean validRequestedBy = this.requestedBy != null && this.requestedBy.length() > 0;

        return validDates && validStatus && validRequestedBy;
    }
}
