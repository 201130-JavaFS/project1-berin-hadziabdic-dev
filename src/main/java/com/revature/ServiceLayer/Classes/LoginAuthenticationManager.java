package com.revature.ServiceLayer.Classes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Interfaces.WebService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is a service layer class, and it performs user authentication.
 */
public class LoginAuthenticationManager implements WebService<Boolean> {

    private UserEntityRepository userRepo;

    public LoginAuthenticationManager() {
        this.userRepo = new UserEntityRepository();

    }

    /**
     * This method is the entery point into the service layer, and parses out the
     * body into a valid DTO.
     * 
     * @return returns whether or not the authentication was succesful.
     */
    @Override
    public Boolean webServe(HttpServletRequest req, HttpServletResponse res) throws IOException {

        UserDTO loginData = new UserDTO(req);
        Boolean authenticated = authenticate(loginData);

        if (authenticated) {
            HttpSession userSession = req.getSession(true);
            userSession.setMaxInactiveInterval(86400); // sessions last a day.
         
              }
        
        return authenticated;
    }

    /**
     * This method authenticates a users login request. It fetches the user from the
     * database by username, and consequently, it compares the token extracted from
     * the body against the database object.
     * 
     * @return returns true if DTO and database object match in username and
     *         password. Otherwise, returns false.
     */

    private boolean authenticate(UserDTO dto) {
        UserEntity entity = null;
        boolean authenticated = false;
        boolean validDTO = dto != null && dto.username != null && dto.password != null && dto.username.length() > 0
                && dto.password.length() > 0;
        boolean validEntity = false;
        if (validDTO) // if dto does not have null or empty fields. I did not perform any regex
                      // checking for strong/weak passwords as it wasnt a criteria.
        {
            entity = userRepo.findById(dto.username);
            validEntity = entity != null && entity.getErs_username() != null && entity.getErs_password() != null;
        }

        if (validDTO && validEntity) {
            authenticated = dto.username.equals(entity.getErs_username())
                    && this.userRepo.repositoryEncoder.matches(dto.password, entity.getErs_password());
        }

        return authenticated;
    }

}
