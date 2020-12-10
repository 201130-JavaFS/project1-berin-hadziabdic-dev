package com.revature.ModelLayer.NamedQueries;

public final class QueryBank {

    public static final String FIND_BY_USERNAME_AND_PASSWORD = "findByUsernameAndPassword";
    public static final String FIND_BY_USERNAME_AND_PASSWORD_HQL = "select ers_user from ers_users where ers_username=ers_username and ers_password=ers_password";

    private QueryBank() {
    }

}
