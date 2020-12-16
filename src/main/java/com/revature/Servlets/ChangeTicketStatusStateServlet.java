package com.revature.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ServiceLayer.Classes.RecieptService;
import com.revature.ServiceLayer.Interfaces.WebService;

public class ChangeTicketStatusStateServlet extends AbstractExceptionBoundaryHttpServlet {

    private WebService<Boolean> recieptService;

    public ChangeTicketStatusStateServlet() {
        this.recieptService = new RecieptService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

        this.exceptionBoundary.ExceptionBoundaryService(req, res, recieptService);

    }

}
