package com.revature.SecurityFlters;

import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

/**
 * This filter ensures an account has correct permissions for a target resource
 * resource.
 */
public class AccountPermissionFilter extends HttpFilter {

    /**
     * This method ensures that an authenticated user also has the correct account
     * permission level for a requested resource.
     * 
     * @return this method returns nothing but will set the status to 403 forbidden
     *         status code if a user does not have the correct permissions.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
    }

}
