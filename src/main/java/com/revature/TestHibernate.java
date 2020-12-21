package com.revature;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;

import com.ibm.icu.text.DecimalFormat;
import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Entities.RecieptStatusEntity;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;

import org.apache.commons.math3.util.Precision;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class TestHibernate {

    public static void main(String[] args) {

        try {

            double d = 12.333444;
            double answer = 5.0;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.getMessage());
        }
    }

}
