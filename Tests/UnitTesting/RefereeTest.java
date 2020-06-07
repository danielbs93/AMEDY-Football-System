package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.*;
import org.junit.jupiter.api.*;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RefereeTest {

    private static Referee referee1Main;
    private static Referee referee2Side;
    private static Referee referee3Side;
    private static Team team1;
    private static Team team2;
    private static Stadium stadium;


    @BeforeAll
    public static void setUp() {
        referee1Main = (Referee)myFactory.Generate("referee","MainReferee");
        referee2Side = (Referee)myFactory.Generate("referee","SideReferee");
        referee3Side = (Referee)myFactory.Generate("referee","SideReferee");
        team1 = (Team) myFactory.Generate("team","1");
        team2 = (Team) myFactory.Generate("team","2");
        stadium = team1.getHomeStadium();

    }



    @Test
    void testRefereeTypeAssignment() {
        assertTrue(referee1Main.getRefereeType().equals(RefereeType.MainReferee));
        assertTrue(referee2Side.getRefereeType().equals(RefereeType.SideReferee));
        assertFalse(referee1Main.getRefereeType().equals(referee2Side.getRefereeType()));
    }

    @Test
    void testAddingMatchToReferee() {
        //Illegal input
        try {
            referee1Main.addMatch(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking insertion of a new match
        Match m1 = (Match)myFactory.Generate("match","one");
        referee1Main = m1.getReferees()[0];
        try {
            referee1Main.addMatch(m1);
            assertTrue(m1.equals(referee1Main.getMyMatches().get(0)));

            //Checking exception to insertion of an existing match
            referee1Main.addMatch(m1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("Match is already exist in MainReferee matches list"));
        }
    }

    @Test
    void testViewRefereeMatches(){
        Referee referee = new Referee("Aviv", RefereeType.SideReferee);
        Match m1 = new Match();
        Match m2 = new Match();
        Match m3 = new Match();
        Match[] two = {m1,m2};

        //Checking exception of empty matches list
        try {
            referee.viewMyMatches();
        }catch (Exception e) {
            assertTrue(e instanceof ListIsEmptyException);
            assertTrue(e.getMessage().equals("You don't have any match assignments yet"));
        }

        //Checking correct adding to referee's matches list
        try {
            referee.addMatch(m1);
            m2.setDate(new Date((int)Math.random()*2000,(int)Math.random()*12,(int)Math.random()*31));
            referee.addMatch(m2);
        } catch (Exception e) {}
        try {
            assertArrayEquals(referee.viewMyMatches().toArray(),two);
            Match[] three = {m1,m2,m3};
            //Incorrect referee's matches list
            assertNotEquals(referee.viewMyMatches().toArray(),three);
        }catch (Exception e) {

        }


    }


    @Test
    void testAddEventToAnActiveMatch() {
        Match m1 = (Match)myFactory.Generate("match","one");
        Referee r1 = m1.getReferees()[0];
        try {
            m1.setDate(new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(),LocalDateTime.now().getDayOfMonth()));
            r1.addMatch(m1);
        } catch (Exception e) {}
        Event foul = (Event)myFactory.Generate("event","foul");

        //Checking correct insertion
        try {
            assertTrue(r1.addEventToAMatch(foul,m1));
        } catch (Exception e) {

        }

        //Checking exception to insertion of the same event
        try {
            r1.addEventToAMatch(foul,m1);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
            assertTrue(e.getMessage().equals("Event already exist"));
        }

        //Checking exception to insertion of non existing match at referee's matches list
        Match m2 = (Match)myFactory.Generate("match","two");
        try {
            r1.addEventToAMatch(foul,m2);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertTrue(e.getMessage().equals("Match does not exist in your matches list"));
        }

        //Checking null insertion
        try {
            r1.addEventToAMatch(null,m1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            r1.addEventToAMatch(foul,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception of event insertion to a match by MainReferee and 5 hours have passed
        m1.setDate(new Date(1900,12,12));
        try {
            r1.addEventToAMatch(foul,m1);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

        //Insertion exception of SideReferee after match ended
        try {
            r1 = m1.getReferees()[1];//SideReferee
            r1.addEventToAMatch(foul,m1);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> fanPersonalInfo = this.referee1Main.getPersonalInfo();
        for(Pair currentPair : fanPersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("Referee", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals(currentPair.getValue(), "mainreferee");
            }
            else if(currentPair.getKey().equals("refereeType"))
            {
                assertEquals(currentPair.getValue(), "MainReferee");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }
}
