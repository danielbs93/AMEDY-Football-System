package BuisinessLayer.LogicalOperations;

import CrossCuttingLayer.CRUD;
import DataLayer.DataBase;
import DataLayer.DataBaseReturnTrueAndAdding;
import org.junit.Test;

import java.net.ConnectException;

import static org.junit.Assert.*;

public class GuestTest {


    @Test
    /***
     * Guest T-01
     */
    void getSystemNull() {

        Guest guest = new Guest(null);

        assertNull(guest.getSystem());

    }

    @Test
    /***
     * Guest T-02
     */
    void getSystemRealSystem() {

        //initial
        DataBase DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();
        new CRUD(DB);

        try {
            AMEDYSystem system = new AMEDYSystem(new Authentication(DB));
            Guest guest = new Guest(system);

            assertTrue(guest.getSystem() instanceof AMEDYSystem);

        } catch (ConnectException e) {
            assertTrue(false);
        }

    }

}