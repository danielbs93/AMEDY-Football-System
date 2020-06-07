package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.ProfileTypeExistException;
import org.junit.jupiter.api.*;

import java.net.ConnectException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RFA_Tests {

    private static FootballAssociation footballAssociation;
    private static RepresentativeFootballAssociation RFA;
    private static User rfaUser;
    private static User refereeUser;
    private static League league;
    private static Season season;
    private static SeasonLeagueAssignments seasonLeagueAssignments;

    @BeforeAll
    static void setUp() throws ConnectException {
        DataBaseReturnTrueAndAdding dataBaseReturnTrueAndAdding = new DataBaseReturnTrueAndAdding();
        dataBaseReturnTrueAndAdding.initialDBStruct();
        Authentication authentication = new Authentication(dataBaseReturnTrueAndAdding);
        new CRUD(dataBaseReturnTrueAndAdding);

        AMEDYSystem amedySystem = new AMEDYSystem(authentication);
        footballAssociation = new FootballAssociation("Israel");
        rfaUser = new User("Daniel","12345678",amedySystem);
        RFA = (RepresentativeFootballAssociation)myFactory.Generate("rfa","Daniel");
        RFA.setUser(rfaUser);
        rfaUser.addProfile(RFA);
        RFA.setFootballAssociation(footballAssociation);
        refereeUser = new User("Eran","12345678",amedySystem);
        season = new Season(2020,amedySystem,(LeagueRankPolicy)myFactory.Generate("leaguerankpolicy","leaguerankpolicy"));
        league = (League)myFactory.Generate("League",LeagueRank.league1.toString());
        league.setLeagueRankPolicy(season.getLeagueRankPolicy(league));
        seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);
    }

    @Test
    void testOpenNewLeague() {
        assertTrue(RFA.addNewLeagueUnitTesting(LeagueRank.league1));
        assertTrue(RFA.getLeague(LeagueRank.league1).getLeagueRank().equals(LeagueRank.league1));
        assertFalse(RFA.addNewLeagueUnitTesting(LeagueRank.league1));
    }

    @Test
    void testAddSeasonToLeague(){

        assertTrue(league.getSeasonByYear(2020)!=null);
        try {
            assertTrue(RFA.addSeasonToLeague(league,2021));
        } catch (IllegalValueException e) {

        }
        assertTrue(league.getSeasonByYear(2021).getYear() == 2021);

    }

    @Test
    void testRegistrationReferee() throws ProfileTypeExistException {

        try {
            assertTrue(RFA.registrationReferee(refereeUser,"Bigzi", RefereeType.MainReferee));
        } catch (IllegalValueException e) {

        }
        Profile profile = refereeUser.getProfiles().get(0);
        assertTrue(profile.getType().equals("Referee"));
    }

    @Test
    void testRemoveReferee() throws AssertIsNotExistException, IllegalValueException {

        assertTrue(RFA.removeReferee("Bigzi"));

        assertTrue(refereeUser.getProfiles().size() == 0);
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> fanPersonalInfo = this.RFA.getPersonalInfo();
        for(Pair currentPair : fanPersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("RFA", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals(currentPair.getValue(), "daniel");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }
}
