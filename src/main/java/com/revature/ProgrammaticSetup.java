package com.revature;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.logging.FileHandler;

import com.ibm.icu.text.DecimalFormat;
import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Entities.RecieptStatusEntity;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Classes.CreateUserService;

import org.apache.commons.math3.util.Precision;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProgrammaticSetup {

    public static void CreateUser(UserEntityRepository creationRepo) {
        UserDTO employee = new UserDTO();
        UserDTO financemanager = new UserDTO();

        employee.username = "employee";
        employee.password = "employee";
        employee.email = "employee@ers.com";

        financemanager.email = "financemanager@fers.com";
        financemanager.password = "financemanager";
        financemanager.username = "financemanager";

        try {
            UserEntity employeeEntity = new UserEntity(employee);
            UserEntity managerEntity = new UserEntity(financemanager);

            creationRepo.create(employeeEntity);
            creationRepo.create(managerEntity);
        } catch (Exception e) {
        }

    }

    public static void main(String[] args) {
        UserEntityRepository creationRepo = new UserEntityRepository();

        try {
            CreateUser(creationRepo);

        } catch (Exception e) {

        }
    }

}
