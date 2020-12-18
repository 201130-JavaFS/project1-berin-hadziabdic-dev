package com.revature.ServiceLayer.Classes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.DTO.Exceptions.IncompleteOrInvalidUserDTOException;
import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Interfaces.ExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.WebService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This service class creates users in the database. It should be wrapped by an
 * exception boundary as it can throw many exceptions from the application
 * layers below to avoid exception propagation to Tomcat.
 */
public class CreateUserService implements WebService<Boolean> {

    private UserEntityRepository userRepo;
    private ObjectMapper jsonParser;

    public CreateUserService() {
        this.userRepo = new UserEntityRepository();
        jsonParser = new ObjectMapper();

    }

    /**
     * This function attempts to create a new user.
     * 
     * @return returns true on success or false on failure. May throw exceptions in
     *         case of bad input, db connectivity issues, and other potentional
     *         unchecked exceptions.
     * @throws IncompleteOrInvalidUserDTOException
     */
    @Override
    public Boolean webServe(HttpServletRequest req, HttpServletResponse res)
            throws IOException, InvalidEntityToDTOConversionException, IncompleteOrInvalidUserDTOException {

        UserDTO createUserDTO = new UserDTO(req);
        UserEntity newUserEntityRepresentation = new UserEntity(createUserDTO);
        // encode the existing password in the entity and set it to

        // TODO Auto-generated method stub
        return userRepo.create(newUserEntityRepresentation);
    }

}
