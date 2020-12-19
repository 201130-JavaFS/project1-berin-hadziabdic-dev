package com.revature.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ServiceLayer.Classes.RecieptService;
import com.revature.ServiceLayer.Interfaces.WebService;

public class GetAllTicketsByUsernameServlet extends AbstractExceptionBoundaryHttpServlet {

    private WebService<Boolean> recieptService;

    public GetAllTicketsByUsernameServlet() {
        super();
        this.recieptService = new RecieptService();
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

        boolean success = false;

        success = this.exceptionBoundary.ExceptionBoundaryService(req, res, recieptService);

        if (success)
            res.setStatus(200);
        else if (res.getStatus() < 500)
            res.setStatus(400);

    }

}
