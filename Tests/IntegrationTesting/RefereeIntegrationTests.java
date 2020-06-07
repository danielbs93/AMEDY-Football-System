package IntegrationTesting;

import BuisinessLayer.LogicalOperations.Event;
import BuisinessLayer.LogicalOperations.Match;
import BuisinessLayer.LogicalOperations.Referee;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RefereeIntegrationTests {

    //Fields
    private static Referee referee1;
    private static Referee referee2;
    private static Match match1;
    private static Match match2;
    private static Event event1;
    private static Event event2;

    @BeforeEach
    void setUp() {
        referee1 = (Referee) myFactory.Generate("referee","mainreferee");
        referee2 = (Referee) myFactory.Generate("referee","sidereferee");
        match1 = (Match) myFactory.Generate("match","m1");
        match2 = (Match) myFactory.Generate("match","m2");
        event1 = (Event) myFactory.Generate("event","foul");
        event2 = (Event) myFactory.Generate("event","redcard");

        match1.setDate(new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth()));
        match1.setTime(LocalTime.now());
        event1.setTime(LocalTime.now());
        event2.setTime(LocalTime.now());
    }

    @Test
    void addMatch() {
        try {
            referee1.addMatch(match1);
            assertTrue(referee1.isExistAndReturn(match1).equals(match1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewMyMatches() {
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);
        try {
            match1.setReferees(new Referee[]{referee1, referee2});
            match2.setReferees(new Referee[]{referee1, referee2});
            assertArrayEquals(referee1.viewMyMatches().toArray(),matches.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void addEventToAMatch() {
        try {
            referee1.addMatch(match1);
            assertTrue(referee1.addEventToAMatch(event1,match1));
            assertTrue(referee1.isExistAndReturn(match1).getEventDiary().isEventExist(event1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateEventMatch() {
        try {
            referee1.addMatch(match1);
            referee1.addEventToAMatch(event1,match1);
            assertTrue(referee1.updateEventMatch(event1,event2,match1));
            assertTrue(referee1.isExistAndReturn(match1).getEventDiary().isEventExist(event2));
            assertFalse(referee1.isExistAndReturn(match1).getEventDiary().isEventExist(event1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
