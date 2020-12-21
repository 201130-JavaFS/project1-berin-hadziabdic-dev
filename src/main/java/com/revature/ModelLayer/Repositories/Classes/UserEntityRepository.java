package com.revature.ModelLayer.Repositories.Classes;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.EntityManager.EntityMgrSingleton;
import com.revature.ModelLayer.NamedQueries.QueryBank;
import com.revature.ModelLayer.Repositories.Interfaces.PoneRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//I chosee to use String as my id even though the table id column is int,
//this is because I don't anticipate query by int id very much, if at all.
//likewise, username is not null and unique, so it is certainly a candidate key and thus unique.
public class UserEntityRepository implements PoneRepository<String, UserEntity> {

    private EntityManager manager;
    public static final PasswordEncoder repositoryEncoder = new BCryptPasswordEncoder(10);

    public UserEntityRepository() {
        manager = new EntityMgrSingleton().GetSingleton();
    }

    public String getUsernameFromId(Integer userId) {
        UserEntity discoveredUser = null;
        EntityTransaction txn = manager.getTransaction();
        String username = null;

        if (userId != null && userId > 0) {

            TypedQuery<UserEntity> matchingUser = manager.createNamedQuery(QueryBank.FIND_BY_ERS_ID, UserEntity.class);
            matchingUser.setParameter("ers_users_id", userId);
            discoveredUser = matchingUser.getSingleResult();
            username = discoveredUser.getErs_username();
        }

        return username;

    }

    @Override
    public UserEntity findById(String id) {
        // TODO Auto-generated method stub
        UserEntity foundUser = null;
        EntityTransaction t;
        // no sense in doing query in the case of nulls and empty strings.
        if (id != null && id.length() > 0) {
            t = manager.getTransaction();
            t.begin();
            TypedQuery<UserEntity> userEntityQuery = manager.createNamedQuery(QueryBank.FIND_BY_USERNAME,
                    UserEntity.class);
            userEntityQuery.setParameter("ers_username", id);
            foundUser = userEntityQuery.getSingleResult();
            t.commit();
        }

        return foundUser;
    }

    @Override
    public boolean deleteById(String id) {
        // TODO Auto-generated method stub
        boolean deleted = false;
        UserEntity foundUser = null;
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        // no sense in doing query in the case of nulls and empty strings.
        if (id != null && id.length() > 0) {
            Query userEntityQuery = manager.createNamedQuery(QueryBank.DELETE_BY_USERNAME);
            userEntityQuery.setParameter("ers_username", id);
            deleted = userEntityQuery.executeUpdate() > 0;

        }
        tx.commit();

        return deleted;
    }

    @Override
    public boolean update(UserEntity entity) {
        // TODO Auto-generated method stub
        EntityTransaction txn = manager.getTransaction();
        boolean success = false;

        txn.begin();

        if (entity != null) {
            manager.refresh(entity);
            success = true;
        }

        txn.commit();

        return success;
    }

    @Override
    public boolean create(UserEntity entity) {
        // TODO Auto-generated method stub
        boolean success = false;
        EntityTransaction persistTransaction = manager.getTransaction();
        persistTransaction.begin();

        if (entity != null) {
            // encode the entity password
            String passwordToEncode = entity.getErs_password();
            passwordToEncode = repositoryEncoder.encode(passwordToEncode);
            entity.setErs_password(passwordToEncode);

            manager.persist(entity);
        }

        persistTransaction.commit();

        return success;
    }

}
