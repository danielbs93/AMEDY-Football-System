package UnitTesting;

import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.Authentication;
import BuisinessLayer.LogicalOperations.SystemManager;
import BuisinessLayer.LogicalOperations.User;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBase;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.FailConnectUnionAccountSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.ConnectException;
import java.util.Scanner;


public class AMEDYSystemTests {

    //Fields
    public static boolean setUpIsDone = false;
    public static AMEDYSystem system;

    @BeforeEach
    public void setUp() throws ConnectException {

        if (setUpIsDone) {
            return;
        }

        DataBase DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();
        Authentication aut;
        new CRUD(DB);

        aut = new Authentication(DB);
        AMEDYSystemTests.system = new AMEDYSystem(aut);

        setUpIsDone = true;
    }


    @Test
    /***
     * AMEDYSystem T-01
     *
     * Check getter Scanner Object.
     */
    public void getScanner() {

        Scanner sc = system.getScanner();

        assertTrue(sc instanceof Scanner);
    }

    @Test
    /***
     * AMEDYSystem T-02
     *
     * Check system manager After insertion.
     */
    public void getSystemManagerAfterInsertion() {

        setUpIsDone = false;

        try {
            setUp();

        } catch (ConnectException e) {
            e.printStackTrace();
        }

        SystemManager systemManager = new SystemManager("admin","123456", "123456", system);

        system.setSystemManager(systemManager);

        assertEquals(system.getSystemManagerSize(), 1);
    }

    @Test
    /***
     * AMEDYSystem T-03
     *
     * Check insertion system manager.
     */
    public void setSystemManager() {

        int systemManagerSize = system.getSystemManagerSize();

        SystemManager systemManager = new SystemManager("admin","123456", "123456", system);

        system.setSystemManager(systemManager);

        assertEquals(systemManagerSize + 1, system.getSystemManagerSize());
    }

    @Test
    /***
     * AMEDYSystem T-04
     *
     * Check system manager before insertion.
     */
    public void getSystemManagerBeforeInsertion() {

        setUpIsDone = false;

        try {
            setUp();

        } catch (ConnectException e) {
            e.printStackTrace();
        }

        assertEquals(system.getSystemManagerSize(), 0);
    }


    //STUBs
    @Test
    /***
     *
     * AMEDYSystem T-05
     *
     * Check connection to law system.
     */
    public void connectToCountryLawSystem() {

        try {
            assertTrue(system.connectToCountryLawSystem());

        } catch (FailConnectUnionAccountSystemException e) {
            assertTrue(false);
        }
    }

    @Test
    /***
     *
     * AMEDYSystem T-06
     *
     * Check connection to Accounting system.
     */
    public void connectToUnionAccountingSystem() {

        try {
            assertTrue(system.connectToUnionAccountingSystem());

        } catch (FailConnectUnionAccountSystemException e) {
            assertTrue(false);
        }
    }

    @Test
    /***
     * AMEDYSystem T-05
     *
     * Check get number of users.
     */
    public void getUserNumber() {

        setUpIsDone = false;

        try {
            setUp();

        } catch (ConnectException e) {
            e.printStackTrace();
        }

        assertEquals(system.getNumberOfUsers(), 0);
    }

    @Test
    /***
     * AMEDYSystem T-06
     *
     * Check login user.
     */
    public void loginTest() {

        setUpIsDone = false;

        User user = new User("uername", "password", this.system);

        try {
            setUp();

            this.system.login(user);

        } catch (ConnectException e) {
            e.printStackTrace();
        }

        assertEquals(system.getNumberOfUsers(), 1);
    }

    @Test
    /***
     * AMEDYSystem T-07
     *
     * Check logout user.
     */
    public void logoutTest() {

        setUpIsDone = false;

        User user = new User("uername", "password", this.system);

        try {
            setUp();
            assertEquals(system.getNumberOfUsers(), 0);
            this.system.login(user);
            assertEquals(system.getNumberOfUsers(), 1);
            this.system.logout(user);
            assertEquals(system.getNumberOfUsers(), 0);

        } catch (ConnectException e) {
            assertTrue(false);
        }
    }

}