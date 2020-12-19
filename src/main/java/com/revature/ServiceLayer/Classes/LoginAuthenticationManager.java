package com.revature.ServiceLayer.Classes;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.API.AppAPI;
import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Exceptions.FilterFoundNoAuthenticationHandlerForAuthenticatedResourceException;
import com.revature.ServiceLayer.Interfaces.AuthenticationHandler;
import com.revature.ServiceLayer.Interfaces.WebService;

/**
 * This class is a service layer class, and it performs user authentication.
 */
public class LoginAuthenticationManager implements WebService<Boolean> {

    private final String USERNAME = "ers_username";
    private UserEntityRepository userRepo;
    private Map<String, AuthenticationHandler> apiAuthenticationHandlers;

    public LoginAuthenticationManager() {

        super();
        this.userRepo = new UserEntityRepository();
        apiAuthenticationHandlers = new HashMap<String, AuthenticationHandler>();

        AuthenticationHandler loginHandler = (HttpServletRequest req, HttpServletResponse res) -> {
            UserDTO loginData = new UserDTO(req);
            UserEntity logInUserData = userRepo.findById(loginData.username);

            if (logInUserData == null)
                throw new UnexpectedException(
                        "A user entity was discovered null inside the login authentication manager at a point where it was impossible for the entity to be in a null state by design.");
            boolean authenticated = false;

            authenticated = authenticateUserCredentials(loginData);
            if (authenticated) {
                HttpSession userSession = req.getSession(true);
                userSession.setAttribute(USERNAME, loginData.username);
                userSession.setAttribute("ers_users_id", logInUserData.getErs_users_id().toString());
                userSession.setMaxInactiveInterval(86400); // sessions last a day.
            }
            return authenticated;
        };
        AuthenticationHandler sessionAuthenticationHandler = (HttpServletRequest req, HttpServletResponse res) -> {
            return this.authenticateSession(req);
        };
        AuthenticationHandler financeAuthenticationHandler = (HttpServletRequest req, HttpServletResponse res) -> {
            return this.authenticateFinanceManagerPermissionsAndSession(req);
        };

        this.apiAuthenticationHandlers.put(AppAPI.LOGIN_USER, loginHandler);
        this.apiAuthenticationHandlers.put(AppAPI.RECIEPTS_SERVICE_CREATE_TICKET, sessionAuthenticationHandler);
        this.apiAuthenticationHandlers.put(AppAPI.RECIEPTS_SERVICE_CHANGE_RECIEPT_STATUS, financeAuthenticationHandler);
        this.apiAuthenticationHandlers.put(AppAPI.RECIEPTS_SERVICE_GET_ALL, financeAuthenticationHandler);
    }

    /**
     * This method is the entery point into the service layer, and parses out the
     * body into a valid DTO.
     * 
     * @return returns whether or not the authentication was succesful.
     * @throws FilterFoundNoAuthenticationHandlerForAuthenticatedResource
     */
    @Override
    public Boolean webServe(HttpServletRequest req, HttpServletResponse res)
            throws IOException, FilterFoundNoAuthenticationHandlerForAuthenticatedResourceException {

        Boolean authenticated = false;
        String endPoint = req.getRequestURI();
        AuthenticationHandler handler = null;

        handler = this.apiAuthenticationHandlers.get(endPoint);

        if (handler != null)
            authenticated = handler.handleAuthentication(req, res);
        else
            throw new FilterFoundNoAuthenticationHandlerForAuthenticatedResourceException();

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

    private boolean authenticateUserCredentials(UserDTO dto) {
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
                    && UserEntityRepository.repositoryEncoder.matches(dto.password, entity.getErs_password());
        }

        return authenticated;
    }

    public boolean authenticateSession(HttpServletRequest req) throws IOException {

        String usernameOfSessionUserFromRequest = new UserDTO(req).username;
        HttpSession sessionObtainedFromRequet = req.getSession(false);
        String sessionObjectUsernameAttribute = null;
        Boolean authenticSession = sessionObtainedFromRequet != null && usernameOfSessionUserFromRequest != null
                && usernameOfSessionUserFromRequest.length() > 0;

        if (authenticSession) {
            sessionObjectUsernameAttribute = sessionObtainedFromRequet.getAttribute(USERNAME).toString();
            authenticSession = usernameOfSessionUserFromRequest.equals(sessionObjectUsernameAttribute);
        }
        return authenticSession;
    }

    /**
     * This method should be used to authenticate users who are requesting account
     * manager services.
     * 
     * @return returns a boolean indicating if session owner is a finance manager.
     */

    public boolean authenticateFinanceManagerPermissionsAndSession(HttpServletRequest req) {

        HttpSession usersSession = req.getSession();
        Boolean authenticateFinanceManager = false;
        String usernameSessionAttribute = null;
        UserEntity usersEntity = null;

        if (usersSession != null) {
            usernameSessionAttribute = usersSession.getAttribute(USERNAME).toString();

            if (usernameSessionAttribute.length() > 0) {
                usersEntity = this.userRepo.findById(usernameSessionAttribute);

                if (usersEntity != null && usersEntity.getUser_role_id() == 2)
                    authenticateFinanceManager = true;
            }
        }

        return authenticateFinanceManager;
    }

}
