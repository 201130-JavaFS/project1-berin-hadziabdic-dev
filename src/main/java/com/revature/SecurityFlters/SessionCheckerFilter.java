package com.revature.SecurityFlters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ModelLayer.DTO.UserDTO;

/**
 * This filter ensures that a user accessing a resource requiring authentication
 * is assigned a vald session.
 */
public class SessionCheckerFilter extends HttpFilter {

    /**
     * Performs basic session checking for null Ids and empty sessions.
     * 
     * @return returns true if boolean checks pass or false if they don't. If false
     *         is returned, assume an invalid JSESSION id.
     */
    private boolean sessionCheck(HttpSession sessToCheck) {
        return sessToCheck != null && sessToCheck.getId().length() > 0;
    }

    /**
     * Rejects also requests with 403, if no valid session information is embedded
     * in the request.
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        // Get session from request, but do not create one if one does not exist.
        HttpSession jSession = request.getSession(false);
        if (sessionCheck(jSession))
            chain.doFilter(request, response);
        else
            response.sendError(403, "You're not authorized to access this resource. Please login first.");

    }

}
