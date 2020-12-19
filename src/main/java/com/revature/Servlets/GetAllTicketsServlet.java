package com.revature.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ServiceLayer.Classes.LoginAuthenticationManager;
import com.revature.ServiceLayer.Classes.RecieptService;
import com.revature.ServiceLayer.Interfaces.WebService;

/**
 * This servlet is in charge of servicing the endpoint assigned to the get all
 * tickets feature.
 */
public class GetAllTicketsServlet extends AbstractExceptionBoundaryHttpServlet {

    private static WebService<Boolean> recieptService = new RecieptService();
    private static WebService<Boolean> financeManagerSessionAuthenticatorManager = new LoginAuthenticationManager();
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GetAllTicketsServlet() {
        super();

    }

    /**
     * The doGet returns a list of all tickets inside the database, if and only if,
     * the requestor has a valid sessionid and the requestor's session is tied to a
     * finance managers user account
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {

        boolean getAllRecieptsSuccesful = false;

        getAllRecieptsSuccesful = this.exceptionBoundary.ExceptionBoundaryService(req, res, recieptService);

        if (res.getStatus() < 500 && getAllRecieptsSuccesful) {
            res.setStatus(200);
        } else {
            res.setStatus(400);
        }

    }

}
