package AcceptanceTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.AssertAlreadyExistException;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;
import DataLayer.DataBaseReturnTrueAndAdding;
import ServiceLayer.RefereeController;
import UnitTesting.myFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RefereeAcceptanceTest {

    private static RefereeController refereeController;
    private static DataBaseReturnTrueAndAdding database;
    private static Referee mainReferee;
    private static Referee sideReferee;
    private static Match match1;
    private static Match match2;

    @BeforeEach
     void setUp() {
        refereeController = new RefereeController();
        database = new DataBaseReturnTrueAndAdding();
        mainReferee = (Referee) myFactory.Generate("referee","MainReferee");
        sideReferee = (Referee) myFactory.Generate("referee","SideReferee");

        //Creating matches
        match1 = (Match)myFactory.Generate("match","1");
        match2 = (Match)myFactory.Generate("match","2");

        //Connecting matches to Main Referee
        try {
            mainReferee.addMatch(match1);
            mainReferee.addMatch(match2);
            sideReferee.addMatch(match1);
        } catch (Exception e) {
            e.getMessage();
        }

        //Simulate DB
        database.initialDBStruct();
        database.add(mainReferee);
        database.add(sideReferee);
        database.add(match1);
        database.add(match2);
    }

    @Test
    void testViewRefereeGames() {

        //Connecting to the system
        String name = mainReferee.getName();
        Referee r1 = database.selectReferee(name);

        //view my games
        try {
            ArrayList<Match> list = (ArrayList<Match>) refereeController.viewAssignGames(r1);
            Assertions.assertArrayEquals(list.toArray(),mainReferee.getMyMatches().toArray());
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Test
    void testAddEventToAnActiveMatch() {

        //Creating objects
        Event foul = (Event)myFactory.Generate("event","foul");
        Event extension = (Event)myFactory.Generate("event","extension");
        match1.setDate(new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(),LocalDateTime.now().getDayOfMonth()));

        //Connecting to the system
        String refereeName = mainReferee.getName();
        Referee r1 = database.selectReferee(refereeName);

        String sideRefereeName = sideReferee.getName();
        Referee r2 = database.selectReferee(sideRefereeName);

        String match1Name = match1.toString();
        Match m1 = database.selectMatch(match1Name);

        String match2Name = match1.toString();
        Match m2 = database.selectMatch(match2Name);

        //UC 10.3
        //Checking correct insertion
        try {
            assertTrue(refereeController.addEventToMatch(r1,foul,m1));
        } catch (Exception e) {

        }

        //Checking exception to insertion of the same event
        try {
            refereeController.addEventToMatch(r1,foul,m1);
            refereeController.addEventToMatch(r2,foul,m1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("Event already exist"));
        }

        //Checking exception to insertion of non existing match at referee's matches list
        try {
            refereeController.addEventToMatch(r1,extension,m2);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("Match does not exist in your matches list"));
        }

        //Checking null insertion
        try {
            refereeController.addEventToMatch(r1,null,m1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            refereeController.addEventToMatch(r1,foul,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception of event insertion to a match by MainReferee and 5 hours have passed
        m1.setDate(new Date(1900,12,12));
        try {
            refereeController.addEventToMatch(r1,foul,m1);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

        //Testing exception of event insertion to an ended match by SideReferee
        try {
            refereeController.addEventToMatch(r2,extension,m1);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }


    }

    @Test
    void testUpdateEventToMatch() {

        //Creating Events & connections
        EventDiary eventDiary = new EventDiary(match1.getDate(),match1);
        Event redCard = (Event)myFactory.Generate("event","redcard");
        Event extension = (Event)myFactory.Generate("event","extension");

        database.add(eventDiary);
        database.add(redCard);
        database.add(extension);

        //Generate connections
        match1.setEventDiary(eventDiary);
        match1.setDate(new Date(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth()));
        match1.setTime(LocalTime.now());
        try {
            eventDiary.addEvent(redCard);
            eventDiary.addEvent(extension);
            mainReferee.addEventToAMatch(redCard,match1);
            mainReferee.addEventToAMatch(extension,match1);
        } catch (Exception e) {
            e.getMessage();
        }

        //Connecting to the system
        String refereeName = mainReferee.getName();
        Referee r1 = database.selectReferee(refereeName);

        String sideRefereeName = sideReferee.getName();
        Referee r2 = database.selectReferee(sideRefereeName);

        String match1Name = match1.toString();
        Match m1 = database.selectMatch(match1Name);

        String match2Name = match1.toString();
        Match m2 = database.selectMatch(match2Name);


        //Testing validate update insertion

            //Returning to user event list to choose which one to update
            ArrayList<Event> eventList = m1.getEventDiary().getEvents();
            Event eventToUpdate = eventList.get(0); //Red Card has chosen

            //Creating the update
            Event updated = new Event(
                    eventToUpdate.getDate(),
                    eventToUpdate.getTime(),
                    30,
                    EventType.Extension,
                    null,
                    "20"
            );

            //Inserting the update and removing the old one
            try {
                assertTrue(refereeController.updateEventToMatch(r1,eventToUpdate,updated,m1));
            } catch (Exception e) {
                e.getMessage();
            }

        //Null insertion
        try {
            refereeController.updateEventToMatch(r1,null,updated,m1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        try {
            refereeController.updateEventToMatch(r1,eventToUpdate,null,m1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        try {
            refereeController.updateEventToMatch(r1,eventToUpdate,updated,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Testing insertion of an updated event
        try {
            refereeController.updateEventToMatch(r1,updated,updated,m1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("The updated event is already exist"));
        }

        //Testing main referee updating after 5 hours
        m1.setTime(LocalTime.now().minusHours(6));
        try {
            refereeController.updateEventToMatch(r1,eventToUpdate,updated,m1);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
            assertTrue(e.getMessage().equals(r1.getName() + ", you can't insert new event since 5 hours have passed from the end of the match"));
        }


    }

}
