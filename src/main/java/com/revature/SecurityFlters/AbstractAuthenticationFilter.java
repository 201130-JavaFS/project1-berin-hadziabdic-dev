package com.revature.SecurityFlters;

import javax.servlet.http.HttpFilter;

import com.revature.ServiceLayer.Classes.LoginAuthenticationManager;
import com.revature.ServiceLayer.Classes.ServletExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.ExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.WebService;

public abstract class AbstractAuthenticationFilter extends HttpFilter {

    protected ExceptionBoundary<Object, Boolean> filterExceptionBoundary;

    public AbstractAuthenticationFilter() {
        super();
        this.filterExceptionBoundary = new ServletExceptionBoundary();
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected static WebService<Boolean> userAuthenticationService = new LoginAuthenticationManager();

}
