package com.revature.ServiceLayer.Interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebService {

    public void webServe(HttpServletRequest req, HttpServletResponse res);

}
