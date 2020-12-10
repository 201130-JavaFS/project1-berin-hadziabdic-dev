package com.revature.ModelLayer.EntityManagers;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.revature.Patterns.Singleton;

public class ReceiptEntityManager implements Singleton<EntityManager> {

    private static EntityManager entityManager = Persistence
            .createEntityManagerFactory("com.revature.ModelLayer.Entities.RecieptEntity").createEntityManager();

    private ReceiptEntityManager() {

    }

    @Override
    public EntityManager GetSingleton() {
        return entityManager;
    }

}
