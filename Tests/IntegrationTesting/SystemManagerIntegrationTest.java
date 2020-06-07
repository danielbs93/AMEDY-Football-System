package IntegrationTesting;

import BuisinessLayer.*;
import BuisinessLayer.LogicalOperations.*;
import BuisinessLayer.Policies.*;
import CrossCuttingLayer.CRUD;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.ProfileDoesntExistsException;
import Exceptions.TeamStatusException;
import ServiceLayer.SystemManagerController;
import UnitTesting.myFactory;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SystemManagerIntegrationTest {



    private static Player player1;
    private static User userPlayer1;
    private static TeamOwner teamOwner1;
    private static TeamOwner teamOwner2;
    private static TeamOwner teamOwner3;
    private static User userTeamOwner1;
    private static User userTeamOwner2;
    private static User userTeamOwner3;
    private static User UserSystemManager;
    private static SystemManager systemManager;
    private static DataBaseReturnTrueAndAdding dataBase;
    private static ArrayList<TeamOwner> teamOwnersT1;
    private static ArrayList<TeamOwner> teamOwnersT2;
    private static Team team1;
    private static Team team2;
    private static Match match;
    private static Season season;
    private static Budget budget2;
    private static Budget budget1;

    @BeforeAll
    static  void setUp(){
        dataBase = new DataBaseReturnTrueAndAdding();

        try {
            Authentication authentication = new Authentication(dataBase);
            AMEDYSystem amedySystem = new AMEDYSystem(authentication);
            new CRUD(dataBase);

            //Creating Users
            systemManager = new SystemManager("Eran","12345678","sm",amedySystem);
            UserSystemManager = new User("Eran","12345678",systemManager.getAMEDYSystem());



            team1 = (Team)myFactory.Generate("team","t1");
            team2 = (Team)myFactory.Generate("team","t2");

            userPlayer1 = new User("Daniel","12345678",systemManager.getAMEDYSystem());

            player1 = (Player)myFactory.Generate("player", "Daniel");

            userPlayer1.addProfile(player1);

            player1.setUser(userPlayer1);

            userTeamOwner1 = new User("Aviv","12345678",systemManager.getAMEDYSystem());
            userTeamOwner2 = new User("Michal","12345678",systemManager.getAMEDYSystem());
            userTeamOwner3 = new User("Yarden","12345678",systemManager.getAMEDYSystem());

            teamOwner1 = new TeamOwner("Aviv",team1);
            teamOwner2 = new TeamOwner("Michal",team1);
            teamOwner3 = new TeamOwner("Yarden",team2);

            userTeamOwner1.addProfile(teamOwner1);
            userTeamOwner2.addProfile(teamOwner2);
            userTeamOwner3.addProfile(teamOwner3);

            teamOwner1.setUser(userTeamOwner1);
            teamOwner2.setUser(userTeamOwner2);
            teamOwner3.setUser(userTeamOwner3);

            teamOwnersT1 = new ArrayList<>();
            teamOwnersT2 = new ArrayList<>();

            teamOwnersT1.add(teamOwner1);
            teamOwnersT1.add(teamOwner2);
            teamOwnersT2.add(teamOwner3);

            team1.setTeamOwners(teamOwnersT1);
            team2.setTeamOwners(teamOwnersT2);

            match = (Match)myFactory.Generate("match","match");
            season = new Season(2020,systemManager.getAMEDYSystem(),(LeagueRankPolicy)myFactory.Generate("leagueRankPolicy","leagueRankPolicy"));
            budget1 = new Budget(100,season,team1);
            budget2 = new Budget(20,season,team2);

            javafx.util.Pair p1 = new javafx.util.Pair<>(season,budget1);
            javafx.util.Pair p2 = new javafx.util.Pair<>(season,budget2);

            List<Pair<Season,Budget>> l1 = new LinkedList<>();
            List<Pair<Season,Budget>> l2 = new LinkedList<>();

            l1.add(p1);
            l2.add(p2);

            team1.setSeasonBudget(l1);
            team2.setSeasonBudget(l2);

            match.setHomeTeam(team1);
            match.setAwayTeam(team2);

            dataBase.initialDBStruct();
            dataBase.add(systemManager);
            dataBase.add(userPlayer1);
            dataBase.add(player1);
            dataBase.add(userTeamOwner1);
            dataBase.add(userTeamOwner2);
            dataBase.add(userTeamOwner3);
            dataBase.add(teamOwner1);
            dataBase.add(teamOwner2);
            dataBase.add(teamOwner3);
            dataBase.add(team1,team1.getStatus().toString());
            dataBase.add(team2,team2.getStatus().toString());

        } catch (ConnectException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testCloseTeam(){
        try {
            assertTrue(systemManager.closeTeamPermanently(team1.getTeamName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(team1.getStatus().equals(TeamStatus.CLOSE_PERMANENTLY));
    }

    @Test
    void testResponseComplaint(){
        //initial
        Fan fan = new Fan("Daniel");
        Complaint complaint = new Complaint(fan,"123","456");
        fan.addComplaintToFan(complaint);
        dataBase.add(fan);
        dataBase.add(complaint);

        try {
            SystemManagerController.responseComplaint(systemManager,1,"answer");
        } catch (Exception e) {

        }
        assertTrue(complaint.getSystemManagerAnswer().equals("answer"));

    }

}
