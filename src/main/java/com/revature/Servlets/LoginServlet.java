package com.revature.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    private void SetUserResponseSuccessStatus(HttpServletRequest req, HttpServletResponse res) {

        HttpSession userSession = req.getSession(false);
        Integer accountRole = Integer.parseInt(userSession.getAttribute("role").toString());

        if (accountRole == 1) // user is employee
            res.setStatus(200);
        else if (accountRole == 2) // user is manager
            res.setStatus(209); // use code 209 to communicate the authenticated user is also a mgr.
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Boolean authenticated = false;
        authenticated = this.exceptionBoundary.ExceptionBoundaryService(req, res, usrAuthenticationService);

        if (authenticated) {
            SetUserResponseSuccessStatus(req, res);
        } else {
            res.sendError(401);
        }
    }

}
