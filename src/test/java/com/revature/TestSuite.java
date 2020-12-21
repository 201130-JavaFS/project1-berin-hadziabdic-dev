package com.revature;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.revature.ModelLayer.DTO.UserDTO;
import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.Repositories.Classes.ReceiptEntityRepository;
import com.revature.ModelLayer.Repositories.Classes.UserEntityRepository;
import com.revature.ServiceLayer.Classes.LoginAuthenticationManager;
import com.revature.ServiceLayer.Classes.PseudoMappingService;
import com.revature.ServiceLayer.Classes.RecieptService;
import com.revature.ServiceLayer.Classes.ServletExceptionBoundary;
import com.revature.Servlets.ChangeTicketStatusStateServlet;
import com.revature.Servlets.CreateReimbursmentRequestServlet;
import com.revature.Servlets.CreateUserServlet;
import com.revature.Servlets.GetAllTicketsByUsernameServlet;
import com.revature.Servlets.LoginServlet;
import com.revature.Servlets.LogoutServlet;

import org.junit.jupiter.api.BeforeAll;
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
    public void TestRecieptDoesNotExist() {

        RecieptEntity existingUser = testRepo.findById(-1);
        assertNull(existingUser);
    }

    @Test
    public void TestEmptyList() {

        assertNull(testRepo.getAllRecieptsByUser(-1));
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

    @Test
    public void TestGetAllTicketsServThrowException() {
        Exception test = null;
        GetAllTicketsByUsernameServlet getAllTicketsServletTests = new GetAllTicketsByUsernameServlet();

        try {
            getAllTicketsServletTests.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestLoginServletThrowsException() {
        Exception test = null;
        LoginServlet loginServlet = new LoginServlet();

        try {
            loginServlet.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestLogoutServletThrowsException() {
        Exception test = null;
        LogoutServlet logOut = new LogoutServlet();

        try {
            logOut.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestCreateUserServletThrowsException() {
        Exception test = null;
        CreateUserServlet createUserTestServlet = new CreateUserServlet();

        try {
            createUserTestServlet.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestGetAllTIcketsByUsernameServletThrowsException() {
        Exception test = null;
        GetAllTicketsByUsernameServlet createUserTestServlet = new GetAllTicketsByUsernameServlet();

        try {
            createUserTestServlet.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestCreateReimbRequestServletThrowsException() {
        Exception test = null;
        CreateReimbursmentRequestServlet createReimbRequestServlet = new CreateReimbursmentRequestServlet();

        try {
            createReimbRequestServlet.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestChangeTicketStatusStateServletThrowsException() {
        Exception test = null;
        ChangeTicketStatusStateServlet changeStatusStateServletThrowsException = new ChangeTicketStatusStateServlet();

        try {
            changeStatusStateServletThrowsException.service(null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }
    }

    @Test
    public void TestExceptionBoundaryThrowsExceptionOnNullReqResPair() {
        Exception test = null;
        ServletExceptionBoundary boundary = new ServletExceptionBoundary();

        try {
            boundary.ExceptionBoundaryService(null, null, null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestPseudoMappingServiceOnNullListMappingThrowsException() {
        Exception test = null;

        try {
            PseudoMappingService.MapRecieptEntityListToDtoList(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestPseudoMappingServiceOnNullHttpServletRequestIdFetchThrowsException() {
        Exception test = null;

        try {
            PseudoMappingService.getErsIdFromHttpSession(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestPseudoMappingServiceOnNullReimbIdFetchThrowsException() {
        Exception test = null;

        try {
            PseudoMappingService.getReimbStringFromId(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestPseudoMappingServiceOnNullUserRoleFetchFromNulLSessionThrowsException() {
        Exception test = null;

        try {
            PseudoMappingService.getUserRoleFromHttpSession(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestGetUserRoleRoleFromNullHttpSessionReturnsNull() {
        Exception test = null;

        try {
            PseudoMappingService.getUserRoleFromHttpSession(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }

    @Test
    public void TestPseudoMappingServiceOnNullTimeStampThrowsNoException() {
        Exception test = null;

        try {
            PseudoMappingService.getDateStringFromSqlTimeStamp(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNull(test);
        }

    }

    @Test
    public void TestPseudoMappingOnNullUsernamThrowsException() {
        Exception test = null;

        try {
            PseudoMappingService.getUsernameFromHttpSession(null);
        } catch (Exception e) {
            test = e;
        } finally {
            assertNotNull(test);
        }

    }
}
