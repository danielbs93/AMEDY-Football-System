package IntegrationTesting;

import BuisinessLayer.LogicalOperations.League;
import BuisinessLayer.LogicalOperations.Referee;
import BuisinessLayer.LogicalOperations.Season;
import BuisinessLayer.LogicalOperations.SeasonLeagueAssignments;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.LeagueRankPolicyStub;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeasonIntegrationTests {

    //Fields
    private static Season season;
    private static Referee referee;
    private static League league;
    private static SeasonLeagueAssignments seasonLeagueAssignments;
    private static LeagueRankPolicy leagueRankPolicy;

    @BeforeEach
    void setUp() {
        season = (Season) myFactory.Generate("season","2020");
        league = (League) myFactory.Generate("league","league1");
        referee = (Referee) myFactory.Generate("referee","mainreferee");
        seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);
        leagueRankPolicy = new LeagueRankPolicyStub("Policy1");
    }


    @Test
    void testPutNewLeagueAssignment() {
        season.putNewLeagueAssignment(league,seasonLeagueAssignments);
        assertTrue(season.getSeasonLeagueAssignments(league).equals(seasonLeagueAssignments));
    }

    @Test
    void testGetLeagueRankPolicy() {
        league.setLeagueRankPolicy(leagueRankPolicy);
        assertTrue(season.getLeagueRankPolicy(league).equals(leagueRankPolicy));
    }

    @Test
    void testAddRefereeToSeason() {
        season.addRefereeToSeason(league,referee);
        assertTrue(seasonLeagueAssignments.getReferee(referee).equals(season.getReferee(league,referee)));
    }

    @Test
    void testIsRefereeAssigned() {
        season.addRefereeToSeason(league,referee);
        assertTrue(season.isRefereeAssigned(league,referee));
    }

}
