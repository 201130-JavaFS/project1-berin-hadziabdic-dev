package com.revature.ModelLayer.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.revature.ModelLayer.NamedQueries.QueryBank;

@NamedQueries({
        @NamedQuery(name = QueryBank.FIND_BY_USERNAME_AND_PASSWORD, query = QueryBank.FIND_BY_USERNAME_AND_PASSWORD_HQL) })
@Entity(name = "ers_user")
@Table(name = "ers_users")
public class UserEntity {

    /*
     * (ers_users_id serial primary key, ers_username text not null unique,
     * ers_password text not null, user_first_name text, user_last_name text,
     * user_email text not null unique, user_role_id serial not null, constraint
     * user_roles_fk foreign key (user_role_id) references
     * ers_user_roles(ers_user_role_id));
     */

    @Id
    private int users_id;
    private String ers_username;
    private String ers_password;
    private String user_first_name;
    private String user_last_name;
    private String user_email;
    private int user_rold_id;

    public int getUsers_id() {
        return this.users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getErs_username() {
        return this.ers_username;
    }

    public void setErs_username(String ers_username) {
        this.ers_username = ers_username;
    }

    public String getErs_password() {
        return this.ers_password;
    }

    public void setErs_password(String ers_password) {
        this.ers_password = ers_password;
    }

    public String getUser_first_name() {
        return this.user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return this.user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_email() {
        return this.user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_rold_id() {
        return this.user_rold_id;
    }

    public void setUser_rold_id(int user_rold_id) {
        this.user_rold_id = user_rold_id;
    }

}
