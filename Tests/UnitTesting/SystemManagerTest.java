package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.RecommendationSystem.RecommendSystem;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.*;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.net.ConnectException;
import java.util.LinkedList;
import java.util.List;

public class SystemManagerTest {

    private static SystemManager systemManager;
    private static User playerUser;
    private static Player player;
    private static Team team;
    private static Match match;
    private static Season season;
    private static Budget budget;
    private static Complaint complaint;
    private static Fan fan;


    @BeforeAll
    static void setUp() throws ConnectException {
        DataBaseReturnTrueAndAdding dataBaseReturnTrueAndAdding = new DataBaseReturnTrueAndAdding();
        dataBaseReturnTrueAndAdding.initialDBStruct();
        Authentication authentication = new Authentication(dataBaseReturnTrueAndAdding);
        new CRUD(dataBaseReturnTrueAndAdding);
        AMEDYSystem amedySystem = new AMEDYSystem(authentication);

        systemManager = new SystemManager("Eran","12345678","Eran",amedySystem);
        playerUser = new User("Daniel","12345678",amedySystem);
        player = (Player)myFactory.Generate("player","Daniel");
        team = (Team)myFactory.Generate("team","t1");
        season = new Season(2020,systemManager.getAMEDYSystem(),(LeagueRankPolicy)myFactory.Generate("leagueRankPolicy","leagueRankPolicy"));
        budget = new Budget(100,season,team);
        match = (Match)myFactory.Generate("match","match");
        javafx.util.Pair p1 = new javafx.util.Pair<>(season,budget);
        List<Pair<Season,Budget>> l1 = new LinkedList<>();
        fan = new Fan("Aviv");
        complaint = new Complaint(fan,"project","too longgg");

        l1.add(p1);
        team.setSeasonBudget(l1);
        match.setAwayTeam(team);
        match.setHomeTeam(team);

        playerUser.addProfile(player);
        player.setUser(playerUser);

        dataBaseReturnTrueAndAdding.add(player);
        dataBaseReturnTrueAndAdding.add(playerUser);
        dataBaseReturnTrueAndAdding.add(fan);
        dataBaseReturnTrueAndAdding.add(complaint);
        dataBaseReturnTrueAndAdding.add(team,team.getStatus().toString());
    }

    @Test
    void closeTeamPermanently() throws ProfileDoesntExistsException, TeamStatusException {

        assertTrue(systemManager.closeTeamPermanently(team.getTeamName()));

        assertTrue(team.getStatus().toString().equals(TeamStatus.CLOSE_PERMANENTLY.toString()));

    }

    @Test
    void removeUser() {

        try {
            assertTrue(systemManager.removeUser(playerUser.getUserName()));
        }catch (Exception e){
        }
        try {
            assertEquals(false, systemManager.removeUser(playerUser.getUserName()));
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
            assertEquals("Username does not exist",e.getMessage());
        }

    }

    @Test
    void showComplaint(){
        //According to this test, we need to get a list of complaints in size 1
        List<Complaint> actual = null;
        try {
            actual = systemManager.showComplaints();
        } catch (Exception e) {

        }

        assertTrue(actual.size() == 1);
    }

    @Test
    void responseComplaint() throws IllegalValueException {

        systemManager.responseComplaint(1,"good answer");

        assertTrue(complaint.getSystemManagerAnswer().equals("good answer"));
    }

    @Test
    void showActivities(){
        //According to this test, we need to get a list of activities in size 2
        List<String> actual = systemManager.showActivities();

        assertTrue(actual.size() == 2);

    }

    @Test
    void buildRecommendSystem(){
        //Should get back recommend system that return array size 3 of
        RecommendSystem recommendSystem = systemManager.buildRecommendSystem();
        assertTrue(recommendSystem != null);

        double [] actual = recommendSystem.recommend(match);
        assertTrue(actual.length == 3);

    }

}
