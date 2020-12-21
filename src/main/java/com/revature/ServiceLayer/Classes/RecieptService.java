package com.revature.ServiceLayer.Classes;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.API.AppAPI;
import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Exceptions.InvalidEntityPropertyException;
import com.revature.ServiceLayer.Exceptions.InvalidHttpSessionStateException;
import com.revature.ServiceLayer.Exceptions.InvalidPropertyValueException;
import com.revature.ServiceLayer.Exceptions.NullHttpSessionException;
import com.revature.ServiceLayer.Interfaces.WebService;

/**
 * The RecieptService class provides all required application operations for
 * User reciepts.
 */
public class RecieptService implements WebService<Boolean> {

    private ReceiptEntityRepository receiptEntityRepository;
    private ObjectMapper jsonParserPacker;

    public RecieptService() {
        this.receiptEntityRepository = new ReceiptEntityRepository();
        jsonParserPacker = new ObjectMapper();
    }

    /**
     * The webserve method provides the service implementation for the
     * RecieptService class. Based upon the uri of the request, this method forwards
     * control to the appropriate submethod which will perform the actual service
     * computations requested by the user.
     * 
     * @return returns true if the service was succesfull in completing its request
     *         or false otherwise.
     * @throws InvalidHttpSessionStateException
     * @throws NullHttpSessionException
     * @throws InvalidEntityPropertyException
     * @throws InvalidPropertyValueException
     */
    @Override
    public Boolean webServe(HttpServletRequest req, HttpServletResponse res)
            throws IOException, InvalidEntityToDTOConversionException, NullHttpSessionException,
            InvalidHttpSessionStateException, InvalidEntityPropertyException, InvalidPropertyValueException {

        String subservice = req.getRequestURI();
        Boolean success = false;

        switch (subservice) {
            case AppAPI.RECIEPTS_SERVICE_GET_ALL:
                success = this.getAllRecieptsThroughRepository(req, res);
                break;
            case AppAPI.RECIEPTS_SERVICE_CHANGE_RECIEPT_STATUS:
                success = this.changeRecieptStatusThroughRepository(req);
                break;
            case AppAPI.RECIEPTS_SERVICE_CREATE_TICKET:
                success = this.createTicketThroughRepository(req, res);
                break;
            case AppAPI.RECIEPTS_SERVICE_GET_ALL_BY_USERNAME:
                success = this.getAllRecieptsByUserThroughRepository(req, res);
                break;
            default:
                break;

        }
        return success;
    }

    /**
     * This method creates a new ticket via using the privately nested repository.
     * 
     * @throws InvalidHttpSessionStateException
     * @throws NullHttpSessionException
     * 
     * @returns Boolea indicating if creation operation was succesful.
     * @throws throws various exceptions which self document the nature of the
     *                failure.
     */

    private Boolean createTicketThroughRepository(HttpServletRequest req, HttpServletResponse res)
            throws JsonMappingException, JsonProcessingException, IOException, InvalidEntityToDTOConversionException,
            InvalidHttpSessionStateException, NullHttpSessionException {

        Boolean success = false;
        HttpSession usersSession = req.getSession(false);

        // dbl check session in case it becomes invalidated at this point.
        // unlikely to happen since its been validated bhy authenitcation but could be
        // an edge case where a session invalidates
        // right after validation
        if (usersSession == null)
            throw new InvalidHttpSessionStateException();

        Integer usersSessionId = Integer.valueOf(usersSession.getAttribute("ers_users_id").toString()); // get
        String usernameFromSession = PseudoMappingService.getUsernameFromHttpSession(req);
        // id
        // from
        // session

        if (usersSessionId == null || usersSessionId < 1 || usernameFromSession == null
                || usernameFromSession.length() == 0)
            throw new InvalidHttpSessionStateException();

        UserRecieptDTO userTicketToCreateInDatabase = new UserRecieptDTO(req);
        userTicketToCreateInDatabase.requestedBy = usersSessionId;// set dto id to id from session

        RecieptEntity newTicketEntity = new RecieptEntity(userTicketToCreateInDatabase); // create new reciept entity

        success = this.receiptEntityRepository.create(newTicketEntity);

        return success;
    }

    /**
     * This method getsAllReimbRequest in from this services associatred repository.
     * 
     * @throws InvalidPropertyValueException
     * 
     * @returns Boolean indicating if creation operation was succesful.
     * @throws throws various exceptions which self document the nature of the
     *                failure.
     */
    private Boolean getAllRecieptsThroughRepository(HttpServletRequest req, HttpServletResponse res)
            throws InvalidEntityToDTOConversionException, IOException, InvalidPropertyValueException {

        Boolean success = true; // the only way this method can fail is with an exception. An empty result set
                                // is not a failure
        // but an indcation that there is no reciept in the results.
        List<RecieptEntity> allRecieptsResultsList = this.receiptEntityRepository.getAllReciepts();
        List<UserRecieptDTO> allRecieptUserDtoList = PseudoMappingService
                .MapRecieptEntityListToDtoList(allRecieptsResultsList);
        String entityToDTOjSONresultsConversion = this.jsonParserPacker.writeValueAsString(allRecieptUserDtoList);

        if (entityToDTOjSONresultsConversion != null) {
            res.addHeader("content-type", "application/json");
            res.getWriter().write(entityToDTOjSONresultsConversion);
            success = true;
        }
        return success;

    }

    private Boolean getAllRecieptsByUserThroughRepository(HttpServletRequest req, HttpServletResponse res)
            throws InvalidEntityToDTOConversionException, IOException, NullHttpSessionException,
            InvalidHttpSessionStateException, InvalidPropertyValueException {

        Boolean success = true; // the only way this method can fail is with an exception. An empty result set
                                // is not a failure
        // but an indcation that there is no reciept in the results.
        HttpSession usersSession = req.getSession();
        Integer userId = null;

        if (usersSession == null)
            throw new NullHttpSessionException();

        userId = Integer.valueOf(usersSession.getAttribute("ers_users_id").toString());

        if (userId == null || userId < 0)
            throw new InvalidHttpSessionStateException();

        List<RecieptEntity> allRecieptsResultsList = this.receiptEntityRepository.getAllRecieptsByUser(userId);
        List<UserRecieptDTO> allRecieptsResultsToDTO = PseudoMappingService
                .MapRecieptEntityListToDtoList(allRecieptsResultsList);
        String entityToDTOjSONresultsConversion = this.jsonParserPacker.writeValueAsString(allRecieptsResultsToDTO);

        if (entityToDTOjSONresultsConversion != null) {
            res.addHeader("content-type", "application/json");
            res.getWriter().write(entityToDTOjSONresultsConversion);
        }
        return success;
    }

    /**
     * This method changes the status of a ticket in the repository for the
     * particular reimb_req passed tinside of the body.
     * 
     * @return returns a bolean indicating the success or failure of the change
     *         reciept status operation.
     * @throws InvalidEntityPropertyException
     * @throws InvalidHttpSessionStateException
     * @throws NullHttpSessionException
     * @throws throws                           various self documenting exceptions.
     */

    private Boolean changeRecieptStatusThroughRepository(HttpServletRequest req)
            throws IOException, InvalidEntityToDTOConversionException, InvalidEntityPropertyException,
            NullHttpSessionException, InvalidHttpSessionStateException {

        boolean success = false;
        UserRecieptDTO reciepToChangeData = new UserRecieptDTO();
        reciepToChangeData.unpackReqAndConstructUpdateTicketStatusDTO(req);

        success = this.receiptEntityRepository.updateTicketStatusByIdWithStatus(reciepToChangeData);

        return success;
    }

}
