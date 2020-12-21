package com.revature.ServiceLayer.Classes;

import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Exceptions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class PseudoMappingService {

    private static UserEntityRepository userRepo = new UserEntityRepository();

    public static Integer getErsIdFromHttpSession(HttpServletRequest req)
            throws NullHttpSessionException, InvalidHttpSessionStateException {

        HttpSession usersSession = req.getSession(false);
        Integer ersId = null;
        Object userIdObject = null;

        if (usersSession == null) {
            throw new NullHttpSessionException();
        }

        userIdObject = usersSession.getAttribute("ers_users_id");

        if (userIdObject == null)
            throw new InvalidHttpSessionStateException();

        ersId = Integer.parseInt(userIdObject.toString());

        return ersId;

    }

    public static String getUsernameFromHttpSession(HttpServletRequest req)
            throws NullHttpSessionException, InvalidHttpSessionStateException {

        HttpSession usersSession = req.getSession(false);
        String username = null;
        Object usernameObject = null;

        if (usersSession == null) {
            throw new NullHttpSessionException();
        }

        usernameObject = usersSession.getAttribute("ers_username");

        if (usernameObject == null)
            throw new InvalidHttpSessionStateException();

        username = usernameObject.toString();

        return username;

    }

    public static Integer getUserRoleFromHttpSession(HttpServletRequest req)
            throws NullHttpSessionException, InvalidHttpSessionStateException {

        HttpSession usersSession = req.getSession(false);
        Integer role = null;
        Object roleObject = null;

        if (usersSession == null) {
            throw new NullHttpSessionException();
        }

        roleObject = usersSession.getAttribute("role");

        if (roleObject == null)
            throw new InvalidHttpSessionStateException();

        role = Integer.parseInt(roleObject.toString());

        if (role != 1 || role != 2)
            throw new InvalidHttpSessionStateException("A user's session was in an inconsistent state.");

        return role;

    }

    public static String getUserRoleStringFromSession(HttpServletRequest req)
            throws NullHttpSessionException, InvalidHttpSessionStateException {

        Integer role = getUserRoleFromHttpSession(req);
        String userRole = null;
        switch (role) {
            case 1:
                userRole = "Employee";
                break;
            case 2:
                userRole = "Finance Manager";
        }

        return userRole;

    }

    public static String getReimbStringFromId(Integer id) throws InvalidPropertyValueException {
        String mapping = "Lodging";

        if (id < 1 || id > 4) {
            throw new InvalidPropertyValueException(
                    "The application attempted to use a reimbursment mapping that is out of the supported range.");
        }
        switch (id) {
            case 2:
                mapping = "Travel";
                break;
            case 3:
                mapping = "Food";
                break;
            case 4:
                mapping = "Other";
                break;

        }

        return mapping;

    }

    public static String getStatusStringFromStatusId(Integer id) {
        String statusString = "Unknown";

        switch (id) {
            case 1:
                statusString = "Pending";
                break;
            case 2:
                statusString = "Approved";
                break;
            case 3:
                statusString = "Denied";
                break;
        }

        return statusString;

    }

    public static String getDateStringFromSqlTimeStamp(Timestamp sqlStamp) {
        String dateString = null;

        if (sqlStamp == null)
            dateString = "No date found. Check back soon.";
        else
            dateString = sqlStamp.toString();

        return dateString;
    }

    public static List<UserRecieptDTO> MapRecieptEntityListToDtoList(List<RecieptEntity> allRecieptsResultsList)
            throws InvalidEntityToDTOConversionException, InvalidPropertyValueException {

        List<UserRecieptDTO> userList = new ArrayList<UserRecieptDTO>();

        if (allRecieptsResultsList == null) {
            throw new NullPointerException(
                    "A null RecieptEntity list was passed to a function expecting a non null list of RecieptEntites");
        }

        if (allRecieptsResultsList.size() > 0) {
            for (RecieptEntity recieptRequested : allRecieptsResultsList) {
                UserRecieptDTO dto = new UserRecieptDTO(recieptRequested);

                dto.dateSubmittedString = PseudoMappingService.getDateStringFromSqlTimeStamp(dto.dateSubmitted);
                dto.dateResolvedString = PseudoMappingService.getDateStringFromSqlTimeStamp(dto.dateResolved);

                dto.processedByString = userRepo.getUsernameFromId(dto.processedBy) != null
                        ? userRepo.getUsernameFromId(dto.processedBy)
                        : "Not processed.";
                dto.requestedByString = userRepo.getUsernameFromId(dto.requestedBy);

                dto.typeString = PseudoMappingService.getReimbStringFromId(dto.type);
                dto.statusString = PseudoMappingService.getStatusStringFromStatusId(dto.status);

                userList.add(dto);

            }
        }
        return userList;
    }

}
