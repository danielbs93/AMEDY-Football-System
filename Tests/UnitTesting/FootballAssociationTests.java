package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.LeagueRankPolicyStub;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicyStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FootballAssociationTests {

    //Fields
    private static FootballAssociation footballAssociation;
    private static League league;
    private static Referee referee;
    private static RepresentativeFootballAssociation rfa;
    private static LeagueRankPolicy leagueRankPolicy1;
    private static LeagueRankPolicy leagueRankPolicy2;
    private static MatchAssignmentPolicy matchAssignmentPolicy;
    private static Season season;
    private static SeasonLeagueAssignments seasonLeagueAssignments;

    @BeforeEach
    void setUp() {
        footballAssociation = new FootballAssociation("Israel");
        rfa = (RepresentativeFootballAssociation) myFactory.Generate("rfa","Daniel");
        league = (League) myFactory.Generate("league","league1");
        season = (Season) myFactory.Generate("season","2020");
        referee = (Referee) myFactory.Generate("referee","mainreferee");
        leagueRankPolicy1 = new LeagueRankPolicyStub("LRP_Policy1");
        leagueRankPolicy2 = new LeagueRankPolicyStub("LRP_Policy2");
        matchAssignmentPolicy = new MatchAssignmentPolicyStub("MAP_Policy");
        seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);

    }

    @Test
    void testGetLeague() {
        footballAssociation.addToLeagues(league);
        assertTrue(footballAssociation.getLeague(LeagueRank.league1).equals(league));
    }

    @Test
    void testGetRefereeByName() {
        footballAssociation.getAllReferees().add(referee);
        assertTrue(footballAssociation.getRefereeByName(referee.getName()).equals(referee));
    }


    @Test
    void testAddToRFA() {
        rfa.setFootballAssociation(footballAssociation);
        assertTrue(footballAssociation.getAllRFA().contains(rfa));
    }

    @Test
    void testAddMatchAssignmentPolicy() {
        footballAssociation.addMatchAssignmentPolicy(matchAssignmentPolicy);
        assertTrue(footballAssociation.getMatchAssignmentPolicies().contains(matchAssignmentPolicy));
    }

    @Test
    void testAddLeagueRankPolicy() {
        footballAssociation.addLeagueRankPolicy(leagueRankPolicy1);
        assertTrue(footballAssociation.getLeagueRankPolicies().contains(leagueRankPolicy1));
    }

    @Test
    void testUpdateLeagueRankPolicy() {
        footballAssociation.updateLeagueRankPolicy(leagueRankPolicy1,leagueRankPolicy2);
        assertTrue(footballAssociation.getLeagueRankPolicies().contains(leagueRankPolicy2));
        assertFalse(footballAssociation.getLeagueRankPolicies().contains(leagueRankPolicy1));
    }

    @Test
    void testGetMatchAssignmentPolicy() {
        footballAssociation.addMatchAssignmentPolicy(matchAssignmentPolicy);
        assertTrue(footballAssociation.getMatchAssignmentPolicy(matchAssignmentPolicy.getName()).equals(matchAssignmentPolicy));
    }
}
