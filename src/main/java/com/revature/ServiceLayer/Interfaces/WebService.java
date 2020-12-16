package com.revature.ServiceLayer.Interfaces;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ModelLayer.DTO.Exceptions.InvalidEntityToDTOConversionException;

public interface WebService<ReturnType> {

    public ReturnType webServe(HttpServletRequest req, HttpServletResponse res)
            throws IOException, InvalidEntityToDTOConversionException;

}
