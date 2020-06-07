package IntegrationTesting;

import BuisinessLayer.*;
import BuisinessLayer.LogicalOperations.*;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FanIntegrationTest
{

    private static Fan firstfan;
    private static Fan secondfan;
    private static Coach coach;
    private static Match match;

    @BeforeEach
    void initial()
    {
        this.firstfan = (Fan) myFactory.Generate("fan", "Aviv");
        this.secondfan = (Fan) myFactory.Generate("fan", "Yarden");
        this.coach = (Coach) myFactory.Generate("coach", "Michal");
        this.match = (Match) myFactory.Generate("match", "team");
        DataBaseReturnTrueAndAdding DB = new DataBaseReturnTrueAndAdding();
        DB.initialDBStruct();

        new CRUD(DB);
    }

    @Test
    void test_CRUD_addNewFollower()
    {
        assertEquals(true,CRUD.addNewFollowers(this.firstfan, this.coach));
    }

    @Test
    void test_CRUD_deleteFromFollowersTable()
    {
        CRUD.addNewFollowers(this.firstfan, this.coach);
        assertEquals(true, CRUD.deleteFromFollowersTable(this.firstfan, this.coach));
        assertEquals(false, CRUD.deleteFromFollowersTable(this.firstfan, this.coach));
    }

    @Test
    void test_CRUD_select_by_fan_and_follower()
    {
        CRUD.addNewFollowers(this.firstfan, this.coach);
        List<Pair> results = (ArrayList<Pair>) CRUD.select("followers", this.firstfan,this.coach);
        assertEquals(1, results.size());
        assertEquals("aviv", results.get(0).getKey());
        assertEquals("michal", results.get(0).getValue());
    }

    @Test
    void test_CRUD_select_by_fan_and_match()
    {
        CRUD.addNewFollowers(this.firstfan, this.match);
        List<Pair> results = (ArrayList<Pair>) CRUD.select("followers", this.firstfan,this.match);
        assertEquals(1, results.size());
        assertEquals("aviv" , results.get(0).getKey());
        assertEquals(this.match.getMatchID() + "", results.get(0).getValue());
    }

    @Test
    void test_CRUD_addNewComplaint()
    {
        Complaint complaint = new Complaint(this.firstfan, "aaaaaaaaa", "bbbbbbbbbbbbbbbb");
        assertEquals(true, CRUD.addNewComplaint(complaint));
    }

    @Test
    void test_CRUD_checkWhoFilledComplaint()
    {
        Complaint complaint = new Complaint(this.firstfan, "aaaaaaaaa", "bbbbbbbbbbbbbbbb");
        CRUD.addNewComplaint(complaint);
        assertEquals(this.firstfan.getId(), CRUD.checkWhoFilledComplaint(complaint));
    }

    @Test
    void test_Comaplint_getFanComplainerID()
    {
        Complaint complaint = new Complaint(this.firstfan, "aaaaaaaaa", "bbbbbbbbbbbbbbbb");
        assertEquals(this.firstfan.getId(), complaint.getFanComplainerID());
    }
}
