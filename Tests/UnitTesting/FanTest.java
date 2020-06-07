package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.AssertAlreadyExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;
import ServiceLayer.FanController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FanTest
{

    private static Fan fan;
    private static Fan anotherFan;
    private static Player player;
    private static Coach coach;
    private static Match match;
    private static Match secondMatch;
    private static Complaint firstComplaint;
    private static Complaint secondComplaint;

    @BeforeEach
    void initial()
    {
        fan = new Fan("Aviv");
        anotherFan = (Fan) myFactory.Generate("fan", "Michal");
        player = new Player("Yarden", new Date(1993,1,1), PlayerType.Defender);
        coach = new Coach("Daniel", "ABC", "coach");
        match = (Match) myFactory.Generate("match", "");
        secondMatch = (Match) myFactory.Generate("match", "");
        DataBaseReturnTrueAndAdding DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();

        new CRUD(DB);
    }


    @Test
    void test_getFollowingList() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException
    {
        assertEquals(0, this.fan.getFollowingList().size());

        FanController.followEventMaker(this.fan, this.player);
        FanController.followEventMaker(this.fan, this.coach);

        assertEquals(2, this.fan.getFollowingList().size());
    }

    @Test
    void test_addNewEventMaker()
    {
        assertEquals(true, this.fan.addNewEventMaker(this.player));
        assertEquals(false, this.fan.addNewEventMaker(this.player));
    }

    @Test
    void test_removeEventMaker() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException
    {
        FanController.followEventMaker(this.fan, this.player);
        FanController.followEventMaker(this.fan, this.coach);

        assertEquals(2, this.fan.getFollowingList().size());

        assertEquals(true, this.fan.removeFollower(this.player));
        assertEquals(1, this.fan.getFollowingList().size());

        assertEquals(true, this.fan.removeFollower(this.coach));
        assertEquals(0, this.fan.getFollowingList().size());
    }

    @Test
    void test_isFollowAfterPEM() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException {
        FanController.followEventMaker(this.fan, this.player);

        assertEquals(true, this.fan.isFollowAfterPEM(this.player));

        assertEquals(false, this.fan.isFollowAfterPEM(this.coach));
    }

    @Test
    void test_addNewMatchToFollow()
    {
        assertEquals(true, this.fan.addNewMatchToFollow(this.match));
        assertEquals(false, this.fan.addNewMatchToFollow(this.match));
    }

    @Test
    void test_isFollowAfterMatch() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException {
        FanController.followMatch(this.fan, this.match);

        assertEquals(true, this.fan.isFollowAfterMatch(this.match));

        assertEquals(false, this.fan.isFollowAfterMatch(this.secondMatch));
    }

    @Test
    void test_addComplaintToFan()
    {
        this.firstComplaint = new Complaint(this.fan,"aaaaaaaaa", "bbbbbbbbbbbbbbbbbb");
        assertEquals(true, this.fan.addComplaintToFan(this.firstComplaint));
        assertEquals(false, this.fan.addComplaintToFan(this.firstComplaint));
    }

    @Test
    void test_isContainComplaint()
    {
        this.firstComplaint = new Complaint(this.fan,"aaaaaaaaa", "bbbbbbbbbbbbbbbbbb");
        this.secondComplaint = new Complaint(this.anotherFan,"cccccccccc", "ddddddddddddddd");

        this.fan.addComplaintToFan(this.firstComplaint);
        assertEquals(true, this.fan.isContainComplaint(this.firstComplaint));
        assertEquals(false,this.fan.isContainComplaint(this.secondComplaint));
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> fanPersonalInfo = this.fan.getPersonalInfo();
        for(Pair currentPair : fanPersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("Fan", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals(currentPair.getValue(), "Aviv");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }
}
