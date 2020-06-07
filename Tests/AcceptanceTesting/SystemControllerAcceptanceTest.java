package AcceptanceTesting;

import BuisinessLayer.LogicalOperations.AMEDYSystem;
import BuisinessLayer.LogicalOperations.Authentication;
import CrossCuttingLayer.*;
import DataLayer.*;

import Exceptions.*;
import ServiceLayer.SystemController;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemControllerAcceptanceTest {

    //Fields
    String basePath = "Tests/Files/AcceptanceTesting/SystemControllerAcceptanceTest/";

    @Test
    /***
     * Test-U.C.-1.1-01
     */
    void bootSimpleTest() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-1.1-01.txt", basePath);

        DataBase DB = new DataBaseReturnTrueAndAdding();
        Authentication aut;
        new CRUD(DB);

        try {

            System.setIn(new FileInputStream(path));

            aut = new Authentication(DB);
            AMEDYSystem amedy = new AMEDYSystem(aut);

            SystemController.boot(amedy);

            assertEquals(amedy.getSystemManagerSize(),1);
        }
        catch(ConnectException conExc) {

            assertTrue(false);
        }
        catch (IOException ioExc) {

            assertTrue(false);

        } catch (FailConnectUnionAccountSystemException e) {

            assertTrue(false);

        } catch (UserAddException e) {

            assertTrue(false);

        } catch (BadUsernameException e) {

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
     * Test-U.C.-1.1-02
     */
    void bootCheckSystemManagerAddTest() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-1.1-02.txt", basePath);

        DataBase DB = new DataBaseReturnTrueAndAdding();
        Authentication aut;
        new CRUD(DB);

        try {

            System.setIn(new FileInputStream(path));

            aut = new Authentication(DB);
            AMEDYSystem amedy = new AMEDYSystem(aut);

            SystemController.boot(amedy);
        }
        catch(ConnectException conExc) {

            assertTrue(false);
        }
        catch (IOException ioExc) {

            assertTrue(false);

        } catch (FailConnectUnionAccountSystemException e) {

            assertTrue(false);

        } catch (UserAddException e) {

            assertTrue(false);

        } catch (BadUsernameException e) {

            assertTrue(false);

        } catch (NotValidPasswordException e) {

            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }

        Collection users = DB.select("User", "JohnSmith1");

        assertEquals(1, users.size());
    }

    @Test
    /***
     * Test-U.C.-1.1-03
     */
    void bootCheckWrongSystemManagerPasswordTest() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-1.1-03.txt", basePath);

        DataBase DB = new DataBaseReturnTrueAndAdding();
        Authentication aut;
        new CRUD(DB);

        try {

            System.setIn(new FileInputStream(path));

            aut = new Authentication(DB);
            AMEDYSystem amedy = new AMEDYSystem(aut);

            SystemController.boot(amedy);
        }
        catch(ConnectException conExc) {

            assertTrue(false);
        }
        catch (IOException ioExc) {

            assertTrue(false);

        } catch (FailConnectUnionAccountSystemException e) {

            assertTrue(false);

        } catch (UserAddException e) {

            assertTrue(false);

        } catch (BadUsernameException e) {

            assertTrue(false);

        } catch (NotValidPasswordException e) {

            assertTrue(true);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {

            System.setIn(originSystemIn);
        }

//        Collection users = DB.select("User", "Admina");
//
//        assertEquals(1, users.size());
    }

    @Test
    /***
     * Test-U.C.-1.1-04
     */
    void bootCheckWrongSystemManagerUsernameTest() {

        InputStream originSystemIn = System.in;

        String path = String.format("%sTest-UC-1.1-04.txt", basePath);

        DataBase DB = new DataBaseReturnTrueAndAdding();
        Authentication aut;
        new CRUD(DB);

        try {

            System.setIn(new FileInputStream(path));

            aut = new Authentication(DB);
            AMEDYSystem amedy = new AMEDYSystem(aut);

            SystemController.boot(amedy);
        }
        catch(ConnectException conExc) {

            assertTrue(false);
        }
        catch (IOException ioExc) {

            assertTrue(false);

        } catch (FailConnectUnionAccountSystemException e) {

            assertTrue(false);

        } catch (UserAddException e) {

            assertTrue(false);

        } catch (BadUsernameException e) {

            assertTrue(true);

        } catch (NotValidPasswordException e) {

            assertTrue(false);

        } catch (NotValidNameException e) {

            assertTrue(false);

        } finally {
            System.setIn(originSystemIn);
        }

//        Collection users = DB.select("User", "Admin");
//
//        assertEquals(1, users.size());
    }

}
