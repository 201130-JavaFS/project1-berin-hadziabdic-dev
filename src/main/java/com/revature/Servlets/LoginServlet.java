package com.revature.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;
import com.revature.ServiceLayer.Classes.LoginAuthenticationManager;
import com.revature.ServiceLayer.Interfaces.ExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.WebService;

public class LoginServlet extends AbstractExceptionBoundaryHttpServlet {

    public LoginServlet() {
        super();
    }

    ExceptionBoundary<Object, Boolean> boundary;

    private static WebService<Boolean> usrAuthenticationService = new LoginAuthenticationManager();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Boolean authenticated = false;
        authenticated = this.exceptionBoundary.ExceptionBoundaryService(req, res, usrAuthenticationService);

        if (authenticated) {
            res.setStatus(200);
        } else {
            res.sendError(401);
        }
    }

}
