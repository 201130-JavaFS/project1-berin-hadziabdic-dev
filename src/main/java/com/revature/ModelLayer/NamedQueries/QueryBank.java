package com.revature.ModelLayer.NamedQueries;

public final class QueryBank {

    public static final String FIND_BY_USERNAME = "findByUsername";
    public static final String FIND_BY_USERNAME_JPA_QUERY = "select user from ers_users as user where user.ers_username  = :ers_username";

    public static final String DELETE_BY_USERNAME = "deleteByUsername";
    public static final String DELETE_BY_USERNAME_JPA_QUERY = "delete from ers_users e where e.ers_username = :ers_username";

    public static final String CREATE_USER = "createUser";
    public static final String CREATE_USE_JPA_QUERY = "insert into ers_users values :user ";

    public static final String DELETE_RECIEPT = "deleteRecieptById";
    public static final String DELETE_RECIEPT_JPA_QUERY = "delete from ers_reimbursment e where e.remb_id= :remb_id ";

    public static final String FIND_ALL_RECIEPTS = "findAllReciepts";
    public static final String FIND_ALL_RECIEPTS_JPA_QUERY = "select r from ers_reimbursment r";

    public static final String FIND_ALL_RECIEPTS_BY_USERNAME = "findAllRecieptsByUsername";
    public static final String FIND_ALL_RECIEPTS_BY_USERNAME_JPA_QUERY = "select e from ers_reimbursment e where e.reimb_author = :reimb_author";

    public static final String UPDATE_RECIEPT_STATUS = "updateRecieptStatus";
    public static final String UPDATE_RECIEPT_STATUS_JPA_QUERY = "update ers_reimbursment r set r.reimb_status_id=:reimb_status_id where r.remb_id=:remb_id";

    private QueryBank() {
    }

}
