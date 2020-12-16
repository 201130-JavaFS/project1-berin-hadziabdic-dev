package com.revature.Servlets;

import javax.servlet.http.HttpServlet;

import com.revature.ServiceLayer.Classes.ServletExceptionBoundary;
import com.revature.ServiceLayer.Interfaces.ExceptionBoundary;

public abstract class AbstractExceptionBoundaryHttpServlet extends HttpServlet {

    protected ExceptionBoundary<Object, Boolean> exceptionBoundary;

    public AbstractExceptionBoundaryHttpServlet() {

        this.exceptionBoundary = new ServletExceptionBoundary();

    }

}
