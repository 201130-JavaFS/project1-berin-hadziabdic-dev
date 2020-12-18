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
public class FinanceManagerAccountPermissionFilter extends AbstractAuthenticationFilter {

    /**
     * This method ensures that an authenticated user also has the correct account
     * permission level for a requested resource.
     * 
     * @return this method returns nothing but will set the status to 403 forbidden
     *         status code if a user does not have the correct permissions.
     * @throws ServletException
     * @throws IOException
     */

    public FinanceManagerAccountPermissionFilter() {
        super();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // Perform authentication checking inside exceptionboundary
        boolean authenticateSessionIsManagerSession = this.filterExceptionBoundary.ExceptionBoundaryService(req, res,
                userAuthenticationService);

        if (authenticateSessionIsManagerSession) // if authenitcation passes, pass of request to servlet
            chain.doFilter(req, res);
        else {
            if (res.getStatus() < 500) // if authenitcation did not fail due to 500
                res.setStatus(401); // user not authorized

        }
    }

}
