package com.revature.ModelLayer.Entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
ers_reimbursment(remb_id serial primary key, reimb_amount numeric(12,2), 
			 reimb_submitted timestamp, reimb_resolved timestamp, 
			 reimb_description text, reimb_receipt bytea, reimb_author serial not null, 
			 reimb_resolver serial not null, reimb_status_id serial not null, 
			 reimb_type_id serial not null,*/
@Entity
@Table(name = "ers_reimbursment")
public class RecieptEntity {

    @Id
    private int remb_id;
    private double reimb_amount;
    private Date reimb_submitted;
    private Date reimb_resolved;
    private String reimb_description;
    private byte[] reimb_receipt;
    private String reimb_author;
    private String reimb_resolver;
    private int reimb_status_id;
    private int reimb_type_id;

    public double getReimb_amount() {
        return this.reimb_amount;
    }

    public Date getReimb_submitted() {
        return this.reimb_submitted;
    }

    public Date getReimb_resolved() {
        return this.reimb_resolved;
    }

    public String getReimb_description() {
        return this.reimb_description;
    }

    public byte[] getReimb_receipt() {
        return this.reimb_receipt;
    }

    public String getReimb_author() {
        return this.reimb_author;
    }

    public String getReimb_resolver() {
        return this.reimb_resolver;
    }

    public int getReimb_status_id() {
        return this.reimb_status_id;
    }

    public int getReimb_type_id() {
        return this.reimb_type_id;
    }

}
