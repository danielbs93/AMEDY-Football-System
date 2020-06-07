package IntegrationTesting;


import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.Authentication;
import BuisinessLayer.LogicalOperations.User;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBase;
import DataLayer.DataBaseReturnTrueAndAdding;
import UnitTesting.AMEDYSystemTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AMEDYSystemIntegrationTests {

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

    //DBRelated
    @Test
    /***
     *
     * AMEDYSystem T-07
     *
     * Check that build DB Struct working (depend on database).
     */
    public void buildDBStruct() {

        try {
            system.buildDBStruct();

            assertTrue(true);
        }
        catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    /***
     * AMEDYSystem T-08
     *
     * Check user insertion to DB (depend on database).
     */
    public void registerUserToDB() {

        User user = new User("username", "password", system);

        try {
            assertTrue(system.registerUserToDB(user));

        } catch (Exception e) {
            assertTrue(false);
        }
    }
}