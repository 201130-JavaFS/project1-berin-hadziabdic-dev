package com.revature.ModelLayer.EntityManagers;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.revature.Patterns.Singleton;

public class UserEntityManager implements Singleton<EntityManager> {

    private static EntityManager entityManager = Persistence
            .createEntityManagerFactory("com.revature.ModelLayer.Entities.UserEntity").createEntityManager();

    public UserEntityManager() {

    }

    @Override
    public EntityManager GetSingleton() {
        return entityManager;
    }

}