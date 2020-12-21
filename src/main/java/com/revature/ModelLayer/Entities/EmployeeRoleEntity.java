package com.revature.ModelLayer.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** This entity is mapped to tables found in the employee type lookup table */
@Entity(name = "ers_user_roles")
@Table(name = "ers_user_roles")
public final class EmployeeRoleEntity {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // can never change/update this value through our JPA
    private int ers_user_role_id;

    @Column(insertable = false, updatable = false) // can never change/update this value through our JPA
    private String user_role;

    public Object getErs_user_role_id() {
        return this.ers_user_role_id;
    }

    public Object getUser_role() {
        return this.user_role;
    }

}
