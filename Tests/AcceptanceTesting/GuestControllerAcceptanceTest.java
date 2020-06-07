package AcceptanceTesting;

import BuisinessLayer.LogicalOperations.*;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBase;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.*;
import ServiceLayer.GuestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.security.auth.login.FailedLoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


public class GuestControllerAcceptanceTest {

    //Fields
    boolean setUpOccured = false;
    private AMEDYSystem system;

    String basePath = "Tests/Files/AcceptanceTesting/GuestControllerAcceptanceTest/";


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

    //U.C. - 2.2
    @Test
    /***
     * GuestController T-U.C.-2.2-01
     *
     * Signup a new user - U.C. 2.2
     */
    public void signup() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-01.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

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

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }

        assertNotNull(result);
        assertEquals(result.getKey(), "JohnSmith1");
    }

    @Test
    /***
     * GuestController T-U.C.-2.2-02
     *
     * Signup a new user - U.C. 2.2 - Not Valid username
     */
    public void signupWithNotValidUsernameShort() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-02.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

            assertTrue(false);

        } catch (FileNotFoundException e) {
            assertTrue(false);

        } catch (UsernameTakenException e) {
            assertTrue(false);

        } catch (BadUsernameException e) {
            assertTrue(true);

        } catch (ConnectException e) {
            assertTrue(false);

        } catch (NotValidPasswordException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.2-03
     *
     * Signup a new user - U.C. 2.2 - Not Valid username contain not a letter
     */
    public void signupWithNotValidUsernameNotLetter() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-03.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

            assertTrue(false);

        } catch (FileNotFoundException e) {
            assertTrue(false);

        } catch (UsernameTakenException e) {
            assertTrue(false);

        } catch (BadUsernameException e) {
            assertTrue(true);

        } catch (ConnectException e) {
            assertTrue(false);

        } catch (NotValidPasswordException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.2-04
     *
     * Signup a new user - U.C. 2.2 - Not Valid password - short
     */
    public void signupWithNotValidPasswordShort() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-04.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

            assertTrue(false);

        } catch (FileNotFoundException e) {
            assertTrue(false);

        } catch (UsernameTakenException e) {
            assertTrue(false);

        } catch (NotValidPasswordException e) {
            assertTrue(true);

        } catch (BadUsernameException e) {
            assertTrue(false);

        } catch (ConnectException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.2-05
     *
     * Signup a new user - U.C. 2.2 - Not match passwords
     */
    public void signupWithNotMatchPassword() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-05.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

            assertTrue(false);

        } catch (FileNotFoundException e) {
            assertTrue(false);

        } catch (UsernameTakenException e) {
            assertTrue(false);

        } catch (NotValidPasswordException e) {
            assertTrue(true);

        } catch (BadUsernameException e) {
            assertTrue(false);

        } catch (ConnectException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.2-06
     *
     * Signup a new user - U.C. 2.2 - Not Valid name - short
     */
    public void signupWithNotValidNameShort() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-06.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

            assertTrue(false);

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

        } catch (NotValidNameException e) {

            assertTrue(true);

        }
        finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.2-07
     *
     * Signup a new user - U.C. 2.2 - Not Valid name - numbers
     */
    public void signupWithNotValidNameContainNumbers() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.2-07.txt", basePath);
        Map<String, String> userDetails = new HashMap() {{
            put("username", "JohnSmith1");
            put("password", "password");
            put("verifyPassword", "password");
            put("name", "John Smith");
        }};

        User result = null;

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
            GuestController.signup(guest);

            //Check user that exists
            result = guest.getUser(userDetails);

            assertTrue(false);

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

        } catch (NotValidNameException e) {

            assertTrue(true);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    //U.C
    @Test
    /***
     * GuestController T-U.C.-2.3-01
     *
     * Check signin function.
     */
    public void signin() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.3-01.txt", basePath);
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
            GuestController.signup(guest);

            //Check user that exists
            profile = GuestController.signin(guest);

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

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }

        assertNotNull(profile);
        assertEquals(profile.getName(), "John Smith");
        assertEquals(profile.getType(), "Fan");
    }

    @Test
    /***
     * GuestController T-U.C.-2.3-02
     *
     *
     */
    public void signinNotValidUsername() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.3-02.txt", basePath);
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
            GuestController.signup(guest);

            //Check user that exists
            profile = GuestController.signin(guest);

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
            assertTrue(true);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } catch (ProfileDoesntExistsException e) {
            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.3-03
     *
     *
     */
    public void signinNotValidPassword() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.3-03.txt", basePath);
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
            GuestController.signup(guest);

            //Check user that exists
            profile = GuestController.signin(guest);

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
            assertTrue(true);

        } catch (ProfileDoesntExistsException e) {
            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }

    @Test
    /***
     * GuestController T-U.C.-2.3-04
     *
     * Profile that dont exists.
     */
    public void signinNotValidProfile() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-2.3-04.txt", basePath);
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
            GuestController.signup(guest);

            //Check user that exists
            profile = GuestController.signin(guest);

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
            assertTrue(true);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }
    }


}