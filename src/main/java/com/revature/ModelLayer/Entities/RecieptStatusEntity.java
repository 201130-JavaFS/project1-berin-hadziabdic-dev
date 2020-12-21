package com.revature.ModelLayer.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//create table ers_reimbursment_status(reimb_status_id serial primary key, reimb_status text not null unique);

@Entity(name = "ers_reimbursment_status")
@Table(name = "ers_reimbursment_status")
public class RecieptStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer reimb_status_id;
    @Column(insertable = false, updatable = false)
    public String reimb_status;

    public RecieptStatusEntity() {
    }

}
