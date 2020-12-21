package com.revature.ModelLayer.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** This entity maps to the reimbursment type lookup database table. */
@Entity(name = "ers_reimbursment_type")
@Table(name = "ers_reimbursment_type")
public class ReimbursmentTypeEntity {
    @Id
    @Column(updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int reimb_type_id;

    @Column(updatable = false, insertable = false)
    private String remb_type;

    public String getRemb_type() {
        return this.remb_type;
    }

    public Object get_reimb_type_id() {
        return this.reimb_type_id;
    }

}
