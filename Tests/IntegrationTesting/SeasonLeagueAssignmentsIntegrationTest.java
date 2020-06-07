package IntegrationTesting;

import BuisinessLayer.LogicalOperations.League;
import BuisinessLayer.LogicalOperations.Referee;
import BuisinessLayer.LogicalOperations.Season;
import BuisinessLayer.LogicalOperations.SeasonLeagueAssignments;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicyStub;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeasonLeagueAssignmentsIntegrationTest {

    //Fields
    private static League league;
    private static Season season;
    private static SeasonLeagueAssignments seasonLeagueAssignments;
    private static Referee referee;
    private static MatchAssignmentPolicy matchAssignmentPolicy;

    @BeforeEach
    void setUp() {
        league = (League) myFactory.Generate("league","league1");
        season = (Season) myFactory.Generate("season","2030");
        seasonLeagueAssignments = new SeasonLeagueAssignments(league,season);

        referee = (Referee) myFactory.Generate("referee","mainreferee");
        matchAssignmentPolicy = new MatchAssignmentPolicyStub("map_Policy");

    }

    @Test
    void getMyMatchAssignment() {
        seasonLeagueAssignments.setMyMatchAssignment(matchAssignmentPolicy);
        assertTrue(seasonLeagueAssignments.getMyMatchAssignment().equals(matchAssignmentPolicy));
        assertTrue(seasonLeagueAssignments.getMyMatchAssignment().getName().equals("map_Policy"));
    }

    @Test
    void setMyMatchAssignment() {
        seasonLeagueAssignments.setMyMatchAssignment(matchAssignmentPolicy);
        assertTrue(seasonLeagueAssignments.getMyMatchAssignment().equals(matchAssignmentPolicy));
        assertTrue(seasonLeagueAssignments.getMyMatchAssignment().getName().equals("map_Policy"));
    }

    @Test
    void assignRefereeToSeason() {
        seasonLeagueAssignments.assignRefereeToSeason(referee);
        assertTrue(seasonLeagueAssignments.getReferee(referee).equals(referee));
        assertTrue(seasonLeagueAssignments.getReferee(referee).getName().equals("mainreferee"));
    }


    @Test
    void isRefereeAssigned() {
        seasonLeagueAssignments.assignRefereeToSeason(referee);
        assertTrue(seasonLeagueAssignments.isRefereeAssigned(referee));
    }

}