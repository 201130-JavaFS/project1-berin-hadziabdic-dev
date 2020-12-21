package com.revature;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpServletRequest;

import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Entities.UserEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Classes.LoginAuthenticationManager;
import com.revature.ServiceLayer.Classes.RecieptService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestSuite {

    private static ReceiptEntityRepository testRepo;
    private static UserEntityRepository userTestRepo;
    private static LoginAuthenticationManager loginMgr;
    private static RecieptService recieptService;

    @BeforeAll
    public static void InitDeps() {
        testRepo = new ReceiptEntityRepository();
        userTestRepo = new UserEntityRepository();
        loginMgr = new LoginAuthenticationManager();
        recieptService = new RecieptService();

    }

    @Test
    public void TestReposNotNull() {
        assertNotNull(testRepo);
        assertNotNull(userTestRepo);
        assertNotNull(loginMgr);
    }

    @Test

    public void TestRecieptExists() {

        RecieptEntity existingUser = testRepo.findById(1);
        assertNotNull(existingUser);
    }

    @Test
    public void TestRecieptDoesNotExist() {

        RecieptEntity existingUser = testRepo.findById(-1);
        assertNull(existingUser);
    }

    @Test
    public void TestEmptyList() {

        assertNull(testRepo.getAllRecieptsByUser(-1));
    }

    @Test
    public void TestNotNullExistingList() {

        assertNotNull(testRepo.findById(1));

    }

    @Test
    public void TestGetAllNotNull() {
        assertNotNull(testRepo.getAllReciepts());
    }

    @Test
    public void TestGetAllByDNEuserNull() {
        assertNull(testRepo.getAllRecieptsByUser(-1));

    }

    @Test
    public void TestGetAllByExisitngUserNotNull() {
        assertNotNull(testRepo.getAllRecieptsByUser(1));
    }

    @Test
    public void TestUserNameNotNull() {

        String username = userTestRepo.getUsernameFromId(1);
        assertNotNull(username);
    }

    @Test
    public void TestUserNameNull() {

        String username = userTestRepo.getUsernameFromId(null);
        assertNull(username);
    }

    @Test
    public void TestUserNameNullWithNegative() {

        String username = userTestRepo.getUsernameFromId(-1);
        assertNull(username);
    }

    @Test
    public void TestInvalidDeletionFalse() {

        assertFalse(userTestRepo.deleteById(null));

    }

    @Test
    public void TestInvalidDeletionFalseTwo() {

        assertFalse(userTestRepo.deleteById(""));

    }

    @Test
    public void TestNullUpdateFalse() {

        assertFalse(userTestRepo.update(null));

    }

    @Test
    public void TestExceptionThrowFromUserDTO() {
        Exception test = null;
        UserDTO user;

        try {
            user = new UserDTO(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestExceptionThrowFromRecieptDTO() {
        Exception test = null;
        UserRecieptDTO urd = null;
        RecieptEntity recieptEntityTest = null;

        try {
            urd = new UserRecieptDTO(recieptEntityTest);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestExceptionThrowFromUnpackingDTO() {
        Exception test = null;
        UserRecieptDTO urd = new UserRecieptDTO();

        try {
            urd.unpackReqAndConstructUpdateTicketStatusDTO(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestNullHttpServletRequestOnOrdinarySessionThrowsException() {

        Exception test = null;
        try {
            this.loginMgr.authenticateSession(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestNullHttpServletRequestThrowsOnManagerSessionException() {

        Exception test = null;
        try {
            this.loginMgr.authenticateFinanceManagerPermissionsAndSession(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    // I can't really test with anything more granular than null unless I integrate
    // with Tomcat somehow.
    @Test
    public void TestRecieptServiceWithNulls() {

        Exception test = null;
        try {
            this.recieptService.webServe(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

}
