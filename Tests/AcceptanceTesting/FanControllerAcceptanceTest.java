package AcceptanceTesting;

import BuisinessLayer.LogicalOperations.*;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.AssertAlreadyExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;
import ServiceLayer.FanController;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FanControllerAcceptanceTest
{
    private static Fan firstFan;
    private static Fan secondFan;
    private static Fan thirdFan;
    private static Player firstPlayer;
    private static Player secondPlayer;
    private static Coach coach;
    private static Referee referee;
    private static Match firstMatch;
    private static Match secondMatch;
    private static Match thirdMatch;


    @BeforeEach
    void initial()
    {
        firstFan = new Fan("Aviv");
        secondFan = new Fan("Yarden");
        thirdFan = new Fan("Daniel");
        firstPlayer = new Player("Michal", new Date(1993,1,1), PlayerType.Defender);
        secondPlayer = new Player("Moshe", new Date(1993,1,1), PlayerType.Defender);
        coach = new Coach("Eran", "ABC", "coach");
        referee = new Referee("Boaz", RefereeType.SideReferee);
        firstMatch = (Match) myFactory.Generate("match","");
        secondMatch = (Match) myFactory.Generate("match","");
        thirdMatch = (Match) myFactory.Generate("match","");
        DataBaseReturnTrueAndAdding DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();

        new CRUD(DB);
    }


    @Test
    void test_followAfterEventMaker_IllegalValueException()
    {
        Exception exception = assertThrows(IllegalValueException.class, () ->
                FanController.followEventMaker(null,null)
        );
        assertEquals("at list one of the entities are null.",exception.getMessage());

        Exception exception2 = assertThrows(IllegalValueException.class, () ->
                FanController.followEventMaker(this.firstFan,null)
        );
        assertEquals("at list one of the entities are null.",exception2.getMessage());

        Exception exception3 = assertThrows(IllegalValueException.class, () ->
                FanController.followEventMaker(null, this.referee)
        );
        assertEquals("at list one of the entities are null.",exception3.getMessage());
    }

    @Test
    void test_followAfterEventMaker() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException {
        FanController.followEventMaker(this.firstFan, this.firstPlayer);
        FanController.followEventMaker(this.firstFan, this.coach);
        FanController.followEventMaker(this.firstFan, this.referee);

        assertTrue(this.firstFan.isFollowAfterPEM(this.firstPlayer)); //PEM = ProfileEventMaker
        assertTrue(this.firstFan.isFollowAfterPEM(this.coach));
        assertTrue(this.firstFan.isFollowAfterPEM(this.referee));

        assertTrue(this.firstPlayer.isFanObserverExist(this.firstFan));
        assertTrue(this.coach.isFanObserverExist(this.firstFan));
        assertTrue(this.referee.isFanObserverExist(this.firstFan));

        assertFalse(this.firstFan.isFollowAfterPEM(this.secondPlayer));
        assertFalse(this.secondPlayer.isFanObserverExist(this.firstFan));
    }

    @Test
    void test_followAfterEventMaker_AlreadyExistException() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException
    {
        FanController.followEventMaker(this.firstFan, this.firstPlayer);

        assertTrue(this.firstFan.isFollowAfterPEM(this.firstPlayer));
        assertTrue(this.firstPlayer.isFanObserverExist(this.firstFan));

        Exception exception = assertThrows(AssertAlreadyExistException.class, () ->
                FanController.followEventMaker(this.firstFan, this.firstPlayer)
        );
        assertEquals("fan/profile event maker already exist at profile event maker/fan.",exception.getMessage());
    }

    @Test
    void test_followMatch_IllegalValueException()
    {
        Exception exception = assertThrows(IllegalValueException.class, () ->
                FanController.followMatch(null,null)
        );
        assertEquals("at list one of the entities are null.",exception.getMessage());

        Exception exception2 = assertThrows(IllegalValueException.class, () ->
                FanController.followMatch(this.firstFan,null)
        );
        assertEquals("at list one of the entities are null.",exception2.getMessage());

        Exception exception3 = assertThrows(IllegalValueException.class, () ->
                FanController.followMatch(null, this.firstMatch)
        );
        assertEquals("at list one of the entities are null.",exception3.getMessage());
    }

    @Test
    void test_followMatch() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException {
        FanController.followMatch(this.firstFan, this.firstMatch);
        FanController.followMatch(this.firstFan, this.secondMatch);

        assertTrue(this.firstFan.isFollowAfterMatch(this.firstMatch));
        assertTrue(this.firstFan.isFollowAfterMatch(this.secondMatch));

        assertTrue(this.firstMatch.isFanObserverExist(this.firstFan));
        assertTrue(this.secondMatch.isFanObserverExist(this.firstFan));

        assertFalse(this.firstFan.isFollowAfterMatch(this.thirdMatch));
    }

    @Test
    void test_fillComplaint_IllegalValueException_nullInsertion()
    {
        Exception exception = assertThrows(IllegalValueException.class, () ->
                FanController.fillComplaint(null,"aaaaaa", "bbbbb")
        );
        assertEquals("fan or one the complaint values are null." ,exception.getMessage());

        Exception exception2 = assertThrows(IllegalValueException.class, () ->
                FanController.fillComplaint(this.firstFan,null, "bbbbb")
        );
        assertEquals("fan or one the complaint values are null." ,exception2.getMessage());

        Exception exception3 = assertThrows(IllegalValueException.class, () ->
                FanController.fillComplaint(this.firstFan, "aaaaaa", null)
        );
        assertEquals("fan or one the complaint values are null." ,exception3.getMessage());
    }

    @Test
    void test_fillComplaint_IllegalValueException_shortComplaint()
    {
        Exception exception = assertThrows(IllegalValueException.class, () ->
                FanController.fillComplaint(this.firstFan,"a", "bbbbbccccdddeeee")
        );
        assertEquals("one of the complaint values are invalid." ,exception.getMessage());

        Exception exception2 = assertThrows(IllegalValueException.class, () ->
                FanController.fillComplaint(this.firstFan,"abbbccdddeee", "a")
        );
        assertEquals("one of the complaint values are invalid." ,exception2.getMessage());
    }

    @Test
    void test_FillComplaint_checkIfSuccess() throws IllegalValueException, PermissionDeniedException {
        assertEquals(true, FanController.fillComplaint(this.firstFan, "aaaaaaaaaa","bbbbbbbbbbbbbbbbbbb"));
    }

}
