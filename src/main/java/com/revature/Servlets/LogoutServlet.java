package com.revature.Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;
import com.revature.ServiceLayer.Interfaces.WebService;

/**
 * This servlet performs log out routines of the application. Since the logout
 * method is not complex , this servlet also implements its connected WebService
 * interface and defines the webServe method to perfrom logging out which is
 * more or less session invalidation.
 */
public class LogoutServlet extends AbstractExceptionBoundaryHttpServlet implements WebService<Boolean> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        boolean operationSuccess = this.exceptionBoundary.ExceptionBoundaryService(req, res, this);

        if (operationSuccess)
            res.setStatus(200);
        else if (res.getStatus() < 500) // if there wasn't an internal server error (exception)
            res.setStatus(400); // then

    }

    /**
     * The webServe method of the logout servlet provides the servlet its' logout
     * service
     * 
     * @return returns true if the logout succeeded or false if it failed.
     */
    @Override
    public Boolean webServe(HttpServletRequest req, HttpServletResponse res)
            throws IOException, InvalidEntityToDTOConversionException {
        // TODO Auto-generated method stub
        Boolean success = false;

        HttpSession reqSession = req.getSession(false);
        if (reqSession != null) {
            reqSession.invalidate(); // invalidate session
            success = true;
        }
        return success;
    }

}
