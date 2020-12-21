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
import com.revature.ServiceLayer.Classes.PseudoMappingService;
import com.revature.ServiceLayer.Exceptions.InvalidHttpSessionStateException;
import com.revature.ServiceLayer.Exceptions.NullHttpSessionException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.util.Precision;

public class UserRecieptDTO {
    public Integer ticketNumber;

    public Timestamp dateSubmitted;
    public Timestamp dateResolved;

    public String dateSubmittedString;
    public String dateResolvedString;

    public String description;
    public double amount;

    public int status;
    public String statusString;
    public Integer requestedBy;
    public Integer processedBy;

    public String requestedByString;
    public String processedByString;

    public int type;
    public String typeString;

    public byte[] image;

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
        this.amount = Precision.round(recieptEntity.getReimb_amount(), 2);
        this.status = recieptEntity.getReimb_status_id();
        this.requestedBy = recieptEntity.getReimb_author();
        this.processedBy = recieptEntity.getReimb_resolver();
        this.type = recieptEntity.getReimb_type_id();

    }

    public UserRecieptDTO(HttpServletRequest req)
            throws JsonMappingException, JsonProcessingException, IOException, InvalidEntityToDTOConversionException {
        UserRecieptDTO buffer = this.jsonPackerUnpacker.readValue(IOUtils.toString(req.getReader()),
                UserRecieptDTO.class);

        if (buffer.amount <= 0 || buffer.type <= 0)
            throw new InvalidEntityToDTOConversionException();

        if (buffer != null) {

            this.description = buffer.description;
            this.requestedBy = Integer.valueOf(req.getSession(false).getAttribute("ers_users_id").toString());
            this.dateSubmitted = new Timestamp(System.currentTimeMillis());
            this.type = buffer.type;
            this.amount = buffer.amount;
            this.status = 1;

        }

    }

    public void unpackReqAndConstructUpdateTicketStatusDTO(HttpServletRequest req) throws JsonMappingException,
            JsonProcessingException, IOException, NullHttpSessionException, InvalidHttpSessionStateException {
        UserRecieptDTO updateTicketStatusDTO = this.jsonPackerUnpacker.readValue(IOUtils.toString(req.getReader()),
                UserRecieptDTO.class);

        this.ticketNumber = updateTicketStatusDTO.ticketNumber;
        this.status = updateTicketStatusDTO.status;
        this.processedBy = PseudoMappingService.getErsIdFromHttpSession(req);
    }

    private boolean isValidDTO_ForCreation() {

        boolean validStatus = this.status > 0;
        boolean validRequestedBy = this.requestedBy != null && this.requestedBy > 0;
        boolean validReimbType = this.type > 0;
        boolean validAmount = this.amount >= 0;

        return validAmount && validStatus && validRequestedBy && validReimbType;
    }
}
