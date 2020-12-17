package com.revature.ServiceLayer.Interfaces;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This functional interface allows is intended to map API endpoints to
 * authentication Handlers
 * 
 * @return boolean indicating success or failure of authentication
 */
public interface AuthenticationHandler {

    public boolean handleAuthentication(HttpServletRequest req, HttpServletResponse res) throws IOException;

}
