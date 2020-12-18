package com.revature.ModelLayer.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.DTO.Exceptions.IncompleteOrInvalidUserDTOException;
import com.revature.ModelLayer.NamedQueries.QueryBank;

@NamedQueries({ @NamedQuery(name = QueryBank.FIND_BY_USERNAME, query = QueryBank.FIND_BY_USERNAME_JPA_QUERY),
        @NamedQuery(name = QueryBank.DELETE_BY_USERNAME, query = QueryBank.DELETE_BY_USERNAME_JPA_QUERY), })

@Entity(name = "ers_users")
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
    @Column(columnDefinition = "serial ")
    @GeneratedValue
    private Integer ers_users_id;
    @Column(columnDefinition = "text")
    private String ers_username;
    @Column(columnDefinition = "text")
    private String ers_password;
    @Column(columnDefinition = "text")
    private String user_first_name;
    @Column(columnDefinition = "text")
    private String user_last_name;
    @Column(columnDefinition = "text")
    private String user_email;
    @Column(columnDefinition = "serial")
    private int user_role_id;

    public UserEntity(UserDTO createUserDTO) throws IncompleteOrInvalidUserDTOException {

        boolean validUsername = createUserDTO.username != null && createUserDTO.username.length() > 0;
        boolean validPassword = createUserDTO.password != null & createUserDTO.password.length() != 0;
        boolean validEmail = createUserDTO.email != null && createUserDTO.email.length() != 0;

        if (validUsername && validPassword && validEmail) {
            this.ers_username = createUserDTO.username;
            this.ers_password = createUserDTO.password;
            this.user_email = createUserDTO.email;
            this.user_role_id = 1; // new users are automatically given the title employee
        } else
            throw new IncompleteOrInvalidUserDTOException(
                    "Attempting to unpack DTO into UserEntity in which DTO has one or more required fields empty or set to null.");

    }

    public UserEntity() {
        super();
    }

    public Integer getErs_users_id() {
        return this.ers_users_id;
    }

    public void setErs_users_id(int ers_users_id) {
        this.ers_users_id = ers_users_id;
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

    public int getUser_role_id() {
        return this.user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

}
