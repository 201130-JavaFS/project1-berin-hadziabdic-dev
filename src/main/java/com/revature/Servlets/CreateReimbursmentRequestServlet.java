package com.revature.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ServiceLayer.Classes.RecieptService;
import com.revature.ServiceLayer.Interfaces.WebService;

public class CreateReimbursmentRequestServlet extends AbstractExceptionBoundaryHttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static WebService<Boolean> recieptService = new RecieptService();

    public CreateReimbursmentRequestServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        this.exceptionBoundary.ExceptionBoundaryService(req, res, recieptService);

    }

}
