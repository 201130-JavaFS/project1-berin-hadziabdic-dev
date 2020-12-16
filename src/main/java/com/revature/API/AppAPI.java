package com.revature.API;

public abstract class AppAPI {

    public static final String API = "/apiroot/";
    public static final String USER = API + "/user";

    public static final String LOGIN_USER = USER + "/login";
    public static final String LOGOUT_USER = USER + "/logout";
    public static final String CREATE_USER = USER + "/create";

    public static final String RECIEPTS_SERVICE = "/servicereciepts";
    public static final String RECIEPTS_SERVICE_GET_ALL = RECIEPTS_SERVICE + "/getalltickets";
    public static final String RECIEPTS_SERVICE_CHANGE_RECIEPT_STATUS = RECIEPTS_SERVICE + "/updatestatus";
    public static final String RECIEPTS_SERVICE_CREATE_TICKET = RECIEPTS_SERVICE + "/createticket";

}
