package com.revature.SecurityFlters;

import java.io.IOException;

import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

public class JsonFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String contentTypeHeader = request.getHeader("content-type");

        if (contentTypeHeader != null && contentTypeHeader.toLowerCase().equals("application/json"))
            chain.doFilter(request, response);
        else
            response.sendError(400, "API does not accept non JSON/input at this endpoint.");
    }

}
