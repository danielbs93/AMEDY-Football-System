package IntegrationTesting;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.LeagueRankPolicyStub;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicyStub;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RFAIntegrationTests {

    //Fields
    private static RepresentativeFootballAssociation rfa;
    private static FootballAssociation footballAssociation;
    private static LeagueRankPolicy leagueRankPolicy1;
    private static LeagueRankPolicy leagueRankPolicy2;
    private static League league;
    private static MatchAssignmentPolicy matchAssignmentPolicy;
    private static Season season;
    private static Referee referee;


    @BeforeEach
    void setUp() {
        rfa = (RepresentativeFootballAssociation) myFactory.Generate("rfa","Daniel");
        footballAssociation = new FootballAssociation("Israel");
        rfa.setFootballAssociation(footballAssociation);
        leagueRankPolicy1 = new LeagueRankPolicyStub("Policy1");
        leagueRankPolicy2 = new LeagueRankPolicyStub("Policy2");
        league = (League) myFactory.Generate("league","league1");
        matchAssignmentPolicy = new MatchAssignmentPolicyStub("assignment1");
        season = (Season) myFactory.Generate("season","2019");
        referee = (Referee) myFactory.Generate("referee","mainreferee");
    }

    @Test
    void testAddLeagueRankPolicy() {
        //Testing correct insertion
        assertTrue(footballAssociation.addLeagueRankPolicy(leagueRankPolicy1));
        assertTrue(footballAssociation.addLeagueRankPolicy(leagueRankPolicy2));
    }

    @Test
    void testGetAllLeagueRankPolicies() {
        //Insertion
        assertTrue(footballAssociation.addLeagueRankPolicy(leagueRankPolicy1));
        assertTrue(footballAssociation.addLeagueRankPolicy(leagueRankPolicy2));

        //Testing getAllLeagueRankPolicies function
        ArrayList<LeagueRankPolicy> allPolicies = rfa.getAllLeagueRankPolicies();
        assertArrayEquals(allPolicies.toArray(), new LeagueRankPolicy[]{leagueRankPolicy1, leagueRankPolicy2});

    }

    @Test
    void testGetLeagueRankPolicy() {
        //Insertion
        assertTrue(footballAssociation.addLeagueRankPolicy(leagueRankPolicy1));

        //Testing function
        try {
            LeagueRankPolicy result = rfa.getLeagueRankPolicy(leagueRankPolicy1.getName());
            assertTrue(leagueRankPolicy1.equals(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddNewLeagueRankPolicyToSystem() {
        //Insertion
        try {
            rfa.addNewLeagueRankPolicyToSystem(leagueRankPolicy1);
            assertTrue(footballAssociation.getLeagueRankPolicies().get(0).equals(leagueRankPolicy1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddLeagueRankPolicyToLeague() {
        //Creating League
        try {
            assertTrue(rfa.openNewLeague(league.getLeagueRank()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing function
        try {
            assertTrue(rfa.addLeagueRankPolicyToLeague(league,leagueRankPolicy1));
            assertTrue(rfa.getLeague(league.getLeagueRank()).getLeagueRankPolicy().equals(leagueRankPolicy1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateLeagueRankPolicy() {
        //Creating League and assigning policy to it
        try {
            assertTrue(rfa.openNewLeague(league.getLeagueRank()));
            assertTrue(rfa.addNewLeagueRankPolicyToSystem(leagueRankPolicy1));
            assertTrue(rfa.addLeagueRankPolicyToLeague(league,leagueRankPolicy1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing function
        try {
            assertTrue(rfa.updateLeagueRankPolicy(leagueRankPolicy1,leagueRankPolicy2));
            assertTrue(rfa.getAllLeagueRankPolicies().contains(leagueRankPolicy2));
            assertTrue(rfa.getLeague(league.getLeagueRank()).getLeagueRankPolicy().equals(leagueRankPolicy2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateNewMatchAssignmentPolicy() {
        //Testing function
        try {
            assertTrue(rfa.createNewMatchAssignmentPolicy(matchAssignmentPolicy));
            assertTrue(rfa.getMatchAssignmentPolicy(matchAssignmentPolicy.getName()).equals(matchAssignmentPolicy));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddMatchAssignmentPolicyToLeagueAndSeason() {
        //Creation
        try {
            assertTrue(rfa.createNewMatchAssignmentPolicy(matchAssignmentPolicy));
            assertTrue(rfa.openNewLeague(league.getLeagueRank()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing function
        try {
            assertTrue(rfa.addMatchAssignmentPolicyToLeagueAndSeason(rfa.getLeague(league.getLeagueRank()),matchAssignmentPolicy));
            League l = rfa.getLeague(league.getLeagueRank());
            for (SeasonLeagueAssignments sla: l.getAllSeasonLeagueAssignments()) {
                assertTrue(sla.getMyMatchAssignment().equals(matchAssignmentPolicy));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAssignRefereesToSeason() {
        //Creating
        League l = null;
        try {
            assertTrue(rfa.openNewLeague(league.getLeagueRank()));
            l = rfa.getLeague(league.getLeagueRank());
            SeasonLeagueAssignments seasonLeagueAssignments = new SeasonLeagueAssignments(l,season);
            l.putNewSeasonAssignment(season,seasonLeagueAssignments);
            Referee r1 = (Referee) myFactory.Generate("referee","sidereferee");
            ArrayList<Referee> referees = new ArrayList<>();
            referees.add(r1);
            referees.add(referee);
            footballAssociation.setAllReferees(referees);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Testing function
        try {
            assertTrue(rfa.assignRefereesToSeason(referee.getName(),l,season));
            assertTrue(l.getSeasonLeagueAssignment(season).isRefereeAssigned(referee));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
