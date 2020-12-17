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
import com.revature.ServiceLayer.Exceptions.InvalidHttpSessionStateException;
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
     */
    @Override
    public Boolean webServe(HttpServletRequest req, HttpServletResponse res) throws IOException,
            InvalidEntityToDTOConversionException, NullHttpSessionException, InvalidHttpSessionStateException {
        // TODO Auto-generated method stub
        String subservice = req.getParameter("subservice");
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
     * @returns Boolea indicating if creation operation was succesful.
     * @throws throws various exceptions which self document the nature of the
     *                failure.
     */

    private Boolean createTicketThroughRepository(HttpServletRequest req, HttpServletResponse res)
            throws JsonMappingException, JsonProcessingException, IOException, InvalidEntityToDTOConversionException {

        Boolean success = false;
        UserRecieptDTO userTicketToCreateInDatabase = new UserRecieptDTO(req);
        RecieptEntity newTicketEntity = new RecieptEntity(userTicketToCreateInDatabase);

        success = this.receiptEntityRepository.create(newTicketEntity);

        return success;
    }

    /**
     * This method getsAllReimbRequest in from this services associatred repository.
     * 
     * @returns Boolean indicating if creation operation was succesful.
     * @throws throws various exceptions which self document the nature of the
     *                failure.
     */
    private Boolean getAllRecieptsThroughRepository(HttpServletRequest req, HttpServletResponse res)
            throws InvalidEntityToDTOConversionException, IOException {

        Boolean success = true; // the only way this method can fail is with an exception. An empty result set
                                // is not a failure
        // but an indcation that there is no reciept in the results.
        List<RecieptEntity> allRecieptsResultsList = this.receiptEntityRepository.getAllReciepts();
        String[] entityToDTOjSONresultsConversion = this.getUserRecieptsJSONArray(allRecieptsResultsList);

        if (entityToDTOjSONresultsConversion != null) {
            res.addHeader("content-type", "application/json");
            res.getWriter().write(entityToDTOjSONresultsConversion.toString());
        }
        return success;

    }

    private Boolean getAllRecieptsByUserThroughRepository(HttpServletRequest req, HttpServletResponse res)
            throws InvalidEntityToDTOConversionException, IOException, NullHttpSessionException,
            InvalidHttpSessionStateException {

        Boolean success = true; // the only way this method can fail is with an exception. An empty result set
                                // is not a failure
        // but an indcation that there is no reciept in the results.
        HttpSession usersSession = req.getSession();
        String username = null;

        if (usersSession == null)
            throw new NullHttpSessionException();

        username = usersSession.getAttribute("username").toString();

        if (username == null)
            throw new InvalidHttpSessionStateException();

        List<RecieptEntity> allRecieptsResultsList = this.receiptEntityRepository.getAllRecieptsByUser(username);
        String[] entityToDTOjSONresultsConversion = this.getUserRecieptsJSONArray(allRecieptsResultsList);

        if (entityToDTOjSONresultsConversion != null) {
            res.addHeader("content-type", "application/json");
            res.getWriter().write(entityToDTOjSONresultsConversion.toString());
        }
        return success;
    }

    /**
     * This object converts a list reciept entites into a json array string.
     * 
     * @returns Boolean indicating if creation operation was succesful.
     * @throws throws various exceptions which self document the nature of the
     *                failure.
     */
    private String[] getUserRecieptsJSONArray(List<RecieptEntity> listing)
            throws InvalidEntityToDTOConversionException, JsonProcessingException {

        String[] userRecieptDTO_JSONarray = null;
        UserRecieptDTO recieptDTOiterator = null;
        String recieptDTO_JSONstring = null;
        int arrayDTOiterator = 0;

        if (listing != null && !listing.isEmpty()) {
            userRecieptDTO_JSONarray = new String[listing.size()];

            for (RecieptEntity entityToConvertToDTO : listing) {
                recieptDTOiterator = new UserRecieptDTO(entityToConvertToDTO);
                recieptDTO_JSONstring = this.jsonParserPacker.writeValueAsString(recieptDTOiterator);
                userRecieptDTO_JSONarray[arrayDTOiterator] = recieptDTO_JSONstring;
                arrayDTOiterator++;
            }
        }

        return userRecieptDTO_JSONarray;

    }

    /**
     * This method changes the status of a ticket in the repository for the
     * particular reimb_req passed tinside of the body.
     * 
     * @return returns a bolean indicating the success or failure of the change
     *         reciept status operation.
     * @throws throws various self documenting exceptions.
     */

    private Boolean changeRecieptStatusThroughRepository(HttpServletRequest req)
            throws IOException, InvalidEntityToDTOConversionException {

        Boolean success = false;

        UserRecieptDTO reciepToChangeData = new UserRecieptDTO(req);

        success = this.receiptEntityRepository.updateRecieptByTicketIdToNewReimbStatus(reciepToChangeData);

        return success;
    }

}
