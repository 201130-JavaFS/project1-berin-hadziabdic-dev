package com.revature.ModelLayer.Entities;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.NamedQueries.QueryBank;

/*
ers_reimbursment(remb_id serial primary key, reimb_amount numeric(12,2), 
			 reimb_submitted timestamp, reimb_resolved timestamp, 
			 reimb_description text, reimb_receipt bytea, reimb_author serial not null, 
			 reimb_resolver serial not null, reimb_status_id serial not null, 
             reimb_type_id serial not null,*/

@NamedQueries({ @NamedQuery(name = QueryBank.DELETE_RECIEPT, query = QueryBank.DELETE_RECIEPT_JPA_QUERY),
        @NamedQuery(name = QueryBank.FIND_ALL_RECIEPTS, query = QueryBank.FIND_ALL_RECIEPTS_JPA_QUERY),
        @NamedQuery(name = QueryBank.UPDATE_RECIEPT_STATUS, query = QueryBank.UPDATE_RECIEPT_STATUS_JPA_QUERY),
        @NamedQuery(name = QueryBank.FIND_ALL_RECIEPTS_BY_USERNAME, query = QueryBank.FIND_ALL_RECIEPTS_BY_USERNAME_JPA_QUERY) })

@Entity(name = "ers_reimbursment")
@Table(name = "ers_reimbursment")
public class RecieptEntity {

    @Id
    @GeneratedValue
    private Integer remb_id;

    @Column(columnDefinition = "numeric(10,2)")
    private double reimb_amount;
    @Column(columnDefinition = "timestamp")
    @GeneratedValue
    private Timestamp reimb_submitted;
    @Column(columnDefinition = "timestamp")
    private Timestamp reimb_resolved;
    @Column(columnDefinition = "text")
    private String reimb_description;
    @Column(columnDefinition = "bytea")
    private byte[] reimb_receipt;
    @Column(columnDefinition = "text")
    private Integer reimb_author;
    @Column(columnDefinition = "serial")
    private Integer reimb_resolver;
    @Column(columnDefinition = "serial")
    private Integer reimb_status_id;
    @Column(columnDefinition = "serial")
    private Integer reimb_type_id;

    public RecieptEntity() {
        super();
    }

    public RecieptEntity(UserRecieptDTO userTicketToCreateInDatabase) {

        this.reimb_amount = userTicketToCreateInDatabase.amount;
        this.reimb_submitted = null;
        this.reimb_resolved = null;
        this.reimb_description = userTicketToCreateInDatabase.description;
        this.reimb_receipt = null; // added ticket later.
        this.reimb_author = userTicketToCreateInDatabase.requestedBy;
        this.reimb_resolver = null;
        this.reimb_status_id = 1;
        this.reimb_type_id = userTicketToCreateInDatabase.type;

    }

    public int getRemb_id() {
        return this.remb_id;
    }

    public void setRemb_id(int remb_id) {
        this.remb_id = remb_id;
    };

    public void setRemb_status(int reimb_status) {
        this.reimb_status_id = reimb_status;
    }

    public double getReimb_amount() {
        return this.reimb_amount;
    }

    public Timestamp getReimb_submitted() {
        return this.reimb_submitted;
    }

    public Timestamp getReimb_resolved() {
        return this.reimb_resolved;
    }

    public String getReimb_description() {
        return this.reimb_description;
    }

    public byte[] getReimb_receipt() {
        return this.reimb_receipt;
    }

    public Integer getReimb_author() {
        return this.reimb_author;
    }

    public Integer getReimb_resolver() {
        return this.reimb_resolver;
    }

    public Integer getReimb_status_id() {
        return this.reimb_status_id;
    }

    public Integer getReimb_type_id() {
        return this.reimb_type_id;
    }

}
