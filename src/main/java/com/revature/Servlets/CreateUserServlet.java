package com.revature.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.revature.ServiceLayer.Classes.CreateUserService;
import com.revature.ServiceLayer.Interfaces.WebService;

public class CreateUserServlet extends AbstractExceptionBoundaryHttpServlet {

    private WebService<Boolean> creationService;

    public CreateUserServlet() {
        this.creationService = new CreateUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

        if (this.exceptionBoundary.ExceptionBoundaryService(req, res, this.creationService)) {
            res.setStatus(201);
        } else {

            res.setStatus(400);
        }

    }
}