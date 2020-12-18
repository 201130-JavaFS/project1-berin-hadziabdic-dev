package com.revature;

import java.sql.Date;
import java.sql.Timestamp;

import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

public class TestHibernate {

    public static void main(String[] args) {

        try {

            UserEntityRepository repo = new UserEntityRepository();

            UserRecieptDTO urd = new UserRecieptDTO();
            urd.amount = 999.12;
            urd.description = "hi from tester";
            urd.requestedBy = 10;
            urd.dateSubmitted = new Timestamp(System.currentTimeMillis());
            urd.type = 1;

            RecieptEntity re = new RecieptEntity(urd);
            ReceiptEntityRepository rep = new ReceiptEntityRepository();
            rep.create(re);

            int x = 4;
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.getMessage());
        }
    }

}
