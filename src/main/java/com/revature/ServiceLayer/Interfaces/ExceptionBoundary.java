package com.revature.ServiceLayer.Interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The ExceptionBoundary interface is a contract that wraps a WebService and
 * catches all exceptions that propagate up the stack related to Services and
 * Models. It is a generic that accepts one type, LoggerReference, and in the
 * case of an exception, said logger is used, to persist the exception to the
 * appropriate location.
 * 
 * @param <LoggerReference>     the logger type (JDBC, FileSys, and etc) the
 *                              application uses to log Exceptions.
 * @param <ReturnTypeOfService> this indicates the return type of the service
 *                              underlying service typically booleans are
 *                              returned to indicate success or failure
 *                              conditions. Or, an exception may also be throw
 *                              to be stopped from propagating up to main in the
 *                              implementation of the ExceptionBoundaryService
 *                              method.
 * 
 */
public interface ExceptionBoundary<LoggerReference, ReturnTypeOfService> {

    public ReturnTypeOfService ExceptionBoundaryService(HttpServletRequest req, HttpServletResponse res,
            WebService<ReturnTypeOfService> service);

}
