package AcceptanceTesting;

import BuisinessLayer.LogicalOperations.*;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.IllegalValueException;
import ServiceLayer.UserController;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerAcceptanceTest
{
    private static User firstUser;
    private static Coach coach;
    private static Player player;
    private static Fan fan;


    @BeforeEach
    void initial() throws ConnectException
    {
        firstUser = new User("avivams", "12345678", new AMEDYSystem(new Authentication(new DataBaseReturnTrueAndAdding())));
        coach = (Coach) myFactory.Generate("coach", "MainReferee");
        player = (Player) myFactory.Generate("player", "Yarden");
        fan = (Fan) myFactory.Generate("fan", "Michal");
    }


    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     * test for checking if the UseCase throw IllegalValueException when inserting null argument.
     * @throws IllegalValueException
     */
    @Test
    void test_viewMyPersonalInfo_check_Null_Insertion()
    {
        Exception exception = assertThrows(IllegalValueException.class, () ->
                UserController.viewMyPersonalInfo(null)
        );
        assertEquals("user in null." ,exception.getMessage());
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     * test for checking if the UseCase return all the user's profile.
     * @throws IllegalValueException
     */
    @Test
    void test_viewMyPersonalInfo_check_adding() throws IllegalValueException {
        this.firstUser.addProfile(this.coach);
        this.firstUser.addProfile(this.fan);
        this.firstUser.addProfile(this.player);
        List<List<Pair>> allProfileInfo = UserController.viewMyPersonalInfo(this.firstUser);
        assertEquals(3, allProfileInfo.size());
    }

}
