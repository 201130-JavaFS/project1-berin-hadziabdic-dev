package com.revature;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

public class TestHibernate {

    public static void main(String[] args) {

        try {
            UserEntity testInser = new UserEntity();
            PasswordEncoder encodr = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(6);
            testInser.setErs_username("berin4114");
            testInser.setErs_password("berin3");
            testInser.setUser_email("1b1erin@email.com");
            testInser.setUser_first_name("berin");
            testInser.setUser_last_name("bebea");
            testInser.setUser_role_id(1);

            UserEntityRepository repo = new UserEntityRepository();
            ReceiptEntityRepository recieptRepo = new ReceiptEntityRepository();
            repo.findById("berin"); // repo.create(testInser);
            repo.deleteById("berin44");

            RecieptEntity re = recieptRepo.findById(1l);

            int x = 4;
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.getMessage());
        }
    }

}
