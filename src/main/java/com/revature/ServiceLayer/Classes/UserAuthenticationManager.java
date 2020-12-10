package com.revature.ServiceLayer.Classes;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.EntityManagers.UserEntityManager;
import com.revature.ModelLayer.NamedQueries.QueryBank;
import com.revature.ServiceLayer.Interfaces.WebService;

public class UserAuthenticationManager implements WebService {

    private static EntityManager userEntityManager = new UserEntityManager().GetSingleton();

    @Override
    public void webServe(HttpServletRequest req, HttpServletResponse res) {
        // TODO Auto-generated method stub
        TypedQuery<UserEntity> query = userEntityManager.createNamedQuery(QueryBank.FIND_BY_USERNAME_AND_PASSWORD,
                UserEntity.class);
        System.out.println("Created query");
    }

}
