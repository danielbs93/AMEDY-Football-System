package IntegrationTesting;

import BuisinessLayer.LogicalOperations.League;
import BuisinessLayer.LogicalOperations.Season;
import BuisinessLayer.LogicalOperations.SeasonLeagueAssignments;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.LeagueRankPolicyStub;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicyStub;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeagueIntegrationTest {

    //Fields
    private static League league;
    private static Season season;
    private static SeasonLeagueAssignments seasonLeagueAssignments;
    private static LeagueRankPolicy leagueRankPolicy;
    private static MatchAssignmentPolicy matchAssignmentPolicy;

    @BeforeEach
    void setUp() {
        league = (League) myFactory.Generate("league","league1");
        season = (Season) myFactory.Generate("season","2020");
        seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);

        leagueRankPolicy = new LeagueRankPolicyStub("lrp_Policy");
        matchAssignmentPolicy = new MatchAssignmentPolicyStub("map_Policy");
    }


    @Test
    void testPutNewSeasonAssignment() {
        league.putNewSeasonAssignment(season,seasonLeagueAssignments);
        assertTrue(league.getSeason(season).getSeasonLeagueAssignments(league).equals(seasonLeagueAssignments));
        assertTrue(league.getSeasonLeagueAssignment(season).equals(seasonLeagueAssignments));
    }

    @Test
    void testGetSeasonByYear() {
        league.putNewSeasonAssignment(season,seasonLeagueAssignments);
        assertTrue(league.getSeasonByYear(2020).equals(season));
    }

    @Test
    void testSetLeagueRankPolicy() {
        league.setLeagueRankPolicy(leagueRankPolicy);
        assertTrue(league.getLeagueRankPolicy().equals(leagueRankPolicy));
        assertTrue(league.getLeagueRankPolicy().getName().equals("lrp_Policy"));
    }

    @Test
    void testSetMatchAssignmentPolicyToAllSeasonLeagues() {
        league.putNewSeasonAssignment(season,seasonLeagueAssignments);
        league.setMatchAssignmentPolicyToAllSeasonLeagues(matchAssignmentPolicy);
        assertTrue(league.getSeasonByYear(2020).getSeasonLeagueAssignments(league).getMyMatchAssignment().equals(matchAssignmentPolicy));
        assertTrue(league.getSeasonByYear(2020).getSeasonLeagueAssignments(league).getMyMatchAssignment().getName().equals("map_Policy"));
    }

}