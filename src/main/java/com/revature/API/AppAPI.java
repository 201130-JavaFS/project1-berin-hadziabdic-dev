package com.revature.API;

public abstract class AppAPI {

        public static final String API = "/apiroot/";
        public static final String USER = API + "/user";
        public static final String EMPLOYEE = USER + "/employee";
        public static final String FINANCE_MANAGER = USER + "/financemanager";

        public static final String LOGIN_USER = USER + "/login";
        public static final String LOGOUT_USER = USER + "/logout";
        public static final String CREATE_USER = EMPLOYEE + "/create"; // finance manager creation cannot be done
                                                                       // through
                                                                       // api.

        public static final String RECIEPTS_SERVICE = "/servicereciepts";
        public static final String RECIEPTS_SERVICE_GET_ALL = FINANCE_MANAGER + RECIEPTS_SERVICE + "/getalltickets";
        public static final String RECIEPTS_SERVICE_CHANGE_RECIEPT_STATUS = FINANCE_MANAGER + RECIEPTS_SERVICE
                        + "/updatestatus";
        public static final String RECIEPTS_SERVICE_CREATE_TICKET = EMPLOYEE + RECIEPTS_SERVICE + "/createticket";
        public static final String RECIEPTS_SERVICE_GET_ALL_BY_USERNAME = EMPLOYEE + RECIEPTS_SERVICE
                        + "/getallbyusername";
}
