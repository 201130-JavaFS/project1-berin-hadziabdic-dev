package com.revature;

import javax.persistence.EntityManager;

import com.revature.ModelLayer.EntityManagers.UserEntityManager;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class TestHibernate {

    public static void main(String[] args) {

        EntityManager mgr = new UserEntityManager().GetSingleton();
    }

}
