package com.revature.SecurityFlters;

import javax.servlet.http.HttpFilter;

import com.revature.ServiceLayer.Classes.LoginAuthenticationManager;
import com.revature.ServiceLayer.Interfaces.ExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.WebService;

public abstract class AbstractAuthenticationFilter extends HttpFilter {

    protected ExceptionBoundary<Object, Boolean> filterExceptionBoundary;

    public AbstractAuthenticationFilter() {
        this.filterExceptionBoundary = new ExceptionBoundary<Object, Boolean>();
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected static WebService<Boolean> userAuthenticationService = new LoginAuthenticationManager();

}
