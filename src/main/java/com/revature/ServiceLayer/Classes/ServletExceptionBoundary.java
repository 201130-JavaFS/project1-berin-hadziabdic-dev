package com.revature.ServiceLayer.Classes;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.revature.ServiceLayer.Interfaces.ExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.WebService;

/**
 * This class is used as a boundary to Exception propagation. The
 * ExceptionBoundaryService should be invoked inside the appropriate servlet
 * method. Ideally, each servlet ought to have a reference to its matched
 * webservice and should pass said service to this method in order to fulfill
 * its role.
 * 
 */
public class ServletExceptionBoundary implements ExceptionBoundary<Object, Boolean> {

    /**
     * This method acts as an exception boundary where all exceptions will propagate
     * up to this functions. Exceptions are caught in the catch block and are
     * written out to the log location specified by the generic log paramater.
     */

    @Override
    public Boolean ExceptionBoundaryService(HttpServletRequest req, HttpServletResponse res,
            WebService<Boolean> serviceToInvoke) {
        // TODO Auto-generated method stub
        Boolean success = false;

        try {
            success = serviceToInvoke.webServe(req, res);

        } catch (InvalidDefinitionException invalidUserDTO) {
            // loggin here

        } catch (Exception e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());
            System.out.println(e.toString());

            res.setStatus(500);
            // log here
        }

        return success;

    }

}
