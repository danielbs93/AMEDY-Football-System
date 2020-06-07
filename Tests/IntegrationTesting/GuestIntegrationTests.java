//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package IntegrationTesting;

import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.Authentication;
import BuisinessLayer.LogicalOperations.Guest;
import BuisinessLayer.LogicalOperations.User;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBase;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.UsernameTakenException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestIntegrationTests {
    public GuestIntegrationTests() {
    }

    @Test
    /***
     * Guest-T-01.
     *
     * check createNewUser functionality.
     */
    public void createNewUser() {

        //initial
        DataBase DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();
        new CRUD(DB);

        Guest guest = new Guest(null);
        User user = new User("username", "password", null);

        List result = new ArrayList();

        try {
            //trying to select user that doesnt exists
            result = CRUD.selectUser("System", user.getKey());
            assertEquals(result.size(), 0);

            //add user to the system and trying to select it.
            guest.createNewUser(user);
            result = CRUD.selectUser("System", user.getKey());

        } catch (UsernameTakenException exc) {
            exc.printStackTrace();
        }

        assertEquals(result.size(), 1);
    }

    @Test
    /***
     * Guest T-02.
     *
     * Check getSystem function.
     */
    public void getSystem() {

        //initial guest with null system
        Guest guest = new Guest(null);
        assertNull(guest.getSystem());

        //initial variables
        DataBase DB = new DataBaseReturnTrueAndAdding();
        AMEDYSystem system = null;

        try {
            system = new AMEDYSystem(new Authentication(DB));
        } catch (ConnectException exc) {
            exc.printStackTrace();
        }

        guest = new Guest(system);
        assertNotNull(guest.getSystem());
        assertTrue(guest.getSystem() instanceof AMEDYSystem);

        //set null as system after system is already initialize correctly
        guest.setSystem((AMEDYSystem)null);
        assertNotNull(guest.getSystem());
        assertTrue(guest.getSystem() instanceof AMEDYSystem);
    }

    @Test
    /***
     * Guest T-03.
     *
     *
     */
    public void getUser() {
        DataBase DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();
        new CRUD(DB);
        Guest guest = new Guest((AMEDYSystem)null);
        User user = new User("userinsertiontest", "password", (AMEDYSystem)null);
        User resultUser = null;
        HashMap userDetails = new HashMap() {
            {
                this.put("username", "userinsertiontest");
                this.put("password", "password");
            }
        };

        try {
            AMEDYSystem system = new AMEDYSystem(new Authentication(DB));
            guest.setSystem(system);
            resultUser = guest.getUser(userDetails);
            assertEquals(resultUser, (Object)null);
            guest.createNewUser(user);
            resultUser = guest.getUser(userDetails);
        } catch (UsernameTakenException var7) {
            var7.printStackTrace();
        } catch (ConnectException var8) {
            var8.printStackTrace();
        }

        assertEquals(resultUser, user);
    }
}
