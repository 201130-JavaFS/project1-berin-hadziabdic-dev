package com.revature.ServiceLayer.Interfaces;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationMgr {

    public boolean AuthenticateRequest(HttpServletRequest req);

}
