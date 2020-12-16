package com.revature.ModelLayer.EntityManager;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.revature.Patterns.Singleton;

public class EntityMgrSingleton implements Singleton<EntityManager> {

    private static final EntityManager singleton = Persistence.createEntityManagerFactory("projectOne")
            .createEntityManager();

    @Override
    public EntityManager GetSingleton() {
        // TODO Auto-generated method stub

        return singleton;
    }

}
