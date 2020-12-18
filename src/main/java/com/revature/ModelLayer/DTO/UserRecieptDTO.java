package com.revature.ModelLayer.DTO;

import java.io.IOException;
import java.sql.Timestamp;

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
    public Timestamp dateSubmitted;
    public Timestamp dateResolved;
    public String description;
    public double amount;
    public int status;
    public Integer requestedBy;
    public Integer processedBy;
    public int type;

    private static ObjectMapper jsonPackerUnpacker = new ObjectMapper();

    public UserRecieptDTO() {
        this.status = 1;
        this.type = 1;
    }

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

        if (buffer.ticketNumber < 0 || amount < 0 || buffer.type <= 0)
            throw new InvalidEntityToDTOConversionException();

        if (buffer != null) {
            // all nested if fields are optional, but in some cases will be populated in the
            // DTO
            if (ticketNumber > 0)
                this.ticketNumber = buffer.ticketNumber;
            if (dateSubmitted != null)
                this.dateSubmitted = buffer.dateSubmitted;
            if (dateResolved != null)
                this.dateResolved = buffer.dateResolved;
            if (buffer.description != null)
                this.description = buffer.description;
            if (buffer.processedBy != null)
                this.processedBy = buffer.processedBy;
            if (buffer.requestedBy != null)
                this.requestedBy = buffer.requestedBy;

            this.type = buffer.type;
            this.amount = buffer.amount;
            this.status = 1;

        }

    }

    private boolean isValidDTO_ForCreation() {

        boolean validStatus = this.status > 0;
        boolean validRequestedBy = this.requestedBy != null && this.requestedBy > 0;
        boolean validReimbType = this.type > 0;
        boolean validAmount = this.amount >= 0;

        return validAmount && validStatus && validRequestedBy && validReimbType;
    }
}
