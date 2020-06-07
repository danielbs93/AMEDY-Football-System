package ServiceLayer;

import BuisinessLayer.LogicalOperations.*;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBase;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/***
 * Class represent all User functionality.
 */
public class UserController extends BaseController {

    //Fields
    boolean setUpOccured = false;
    private AMEDYSystem system;

    String basePath = "Tests/Files/AcceptanceTesting/UserontrollerAcceptanceTest/";


    @BeforeEach
    public void setUp() throws Exception {

        if(!setUpOccured) {

            DataBase DB = new DataBaseReturnTrueAndAdding();
            DB.initialDBStruct();
            new CRUD(DB);
            this.system = new AMEDYSystem(new Authentication(DB));

            this.setUpOccured = true;
        }
    }

    /***
     * Logout user from system.
     * U.C. 3.1.
     *
     * @param user user to logout
     *
     * @return ture if logout seceded, false otherwise.
     */
    public static boolean signout(User user) throws SystemNullPointerException {

        if(user == null) {
            throw new NullPointerException();
        }
        AMEDYSystem system = user.getAMEDYSystem();

        if(system == null) {
            throw new SystemNullPointerException("user point to null instead of system.");
        }

        return system.logout(user);
    }

    /**
     * UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method return to the entity all his personal info in the system. beside password
     * @param user
     * @return
     */
    public static List viewMyPersonalInfo(User user) throws IllegalValueException {
        AMEDYSystem.systemLogger.info("[User][viewMyPersonalInfo] - entered viewMyPersonalInfo method.");
        AMEDYSystem.systemLogger.info("[User][viewMyPersonalInfo] checking null arguments.");
        if(user == null)
        {
            AMEDYSystem.systemLogger.warning("[User][viewMyPersonalInfo] user in null.");
            throw new IllegalValueException("user in null.");
        }

        AMEDYSystem.systemLogger.info("[User][viewMyPersonalInfo] getting the profiles list of the user.");
        List<Profile> allUserProfiles = user.getProfiles();

        AMEDYSystem.systemLogger.info("[User][viewMyPersonalInfo] getting from each profile the personal info.");
        List<List<Pair>> allPersonalInfoDetails = new ArrayList<>();
        for(Profile currentProfile : allUserProfiles)
        {
            List<Pair> currentProfileInfo = null;
            if(currentProfile instanceof Fan)
            {
                Fan fan = (Fan) currentProfile;
                currentProfileInfo = fan.getPersonalInfo();
            }
            else if(currentProfile instanceof ProfileEventMaker)
            {
                ProfileEventMaker profileEventMaker = (ProfileEventMaker) currentProfile;
                currentProfileInfo = profileEventMaker.getPersonalInfo();
            }
            allPersonalInfoDetails.add(currentProfileInfo);
        }
            return allUserProfiles;
    }

    @Test
    /***
     * GuestController T-U.C.-3.1-01
     *
     * Check signout
     */
    public void signout() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-3.1-01.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;
        Profile profile = null;
        try {
            //Change System.in
            System.setIn(new FileInputStream(path));

            DataBase DB = new DataBaseReturnTrueAndAdding();
            DB.initialDBStruct();
            new CRUD(DB);
            this.system = new AMEDYSystem(new Authentication(DB));

            //Check user that not exists
            Guest guest = new Guest(this.system);
            result = guest.getUser(userDetails);

            assertNull(result);

            //signup new user
            result = GuestController.signup(guest);

            //Check user that exists
            profile = GuestController.signin(guest);

            assertTrue(UserController.signout(result));

        } catch (FileNotFoundException e) {
            assertTrue(false);

        } catch (UsernameTakenException e) {
            assertTrue(false);

        } catch (NotValidPasswordException e) {
            assertTrue(false);

        } catch (BadUsernameException e) {
            assertTrue(false);

        } catch (ConnectException e) {
            assertTrue(false);

        } catch (FailedLoginException e) {
            assertTrue(false);

        } catch (ProfileDoesntExistsException e) {
            assertTrue(false);

        } catch (SystemNullPointerException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-3.1-02
     *
     *
     */
    public void signoutFailTwice() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-3.1-01.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;
        Profile profile = null;
        try {
            //Change System.in
            System.setIn(new FileInputStream(path));

            DataBase DB = new DataBaseReturnTrueAndAdding();
            DB.initialDBStruct();
            new CRUD(DB);
            this.system = new AMEDYSystem(new Authentication(DB));

            //Check user that not exists
            Guest guest = new Guest(this.system);
            result = guest.getUser(userDetails);

            assertNull(result);

            //signup new user
            result = GuestController.signup(guest);

            //Check user that exists
            profile = GuestController.signin(guest);

            assertTrue(UserController.signout(result));
            assertFalse(UserController.signout(result));

        } catch (FileNotFoundException e) {
            assertTrue(false);

        } catch (UsernameTakenException e) {
            assertTrue(false);

        } catch (NotValidPasswordException e) {
            assertTrue(false);

        } catch (BadUsernameException e) {
            assertTrue(false);

        } catch (ConnectException e) {
            assertTrue(true);

        } catch (FailedLoginException e) {
            assertTrue(false);

        } catch (ProfileDoesntExistsException e) {
            assertTrue(false);

        } catch (SystemNullPointerException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }
}
