package com.revature.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.revature.ServiceLayer.Classes.CreateUserService;
import com.revature.ServiceLayer.Interfaces.WebService;

public class CreateUserServlet extends AbstractExceptionBoundaryHttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final WebService<Boolean> creationService = new CreateUserService();

    public CreateUserServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

        boolean serviceSuccess = this.exceptionBoundary.ExceptionBoundaryService(req, res, creationService);

        if (serviceSuccess) {
            res.setStatus(201);
        } else {

            res.setStatus(400);
        }

    }
}