package com.revature.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ServiceLayer.Classes.UserAuthenticationManager;
import com.revature.ServiceLayer.Interfaces.WebService;

public class LoginServlet extends HttpServlet {
    private WebService usrAuthenticationService = new UserAuthenticationManager();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        usrAuthenticationService.webServe(req, res);
    }

}
