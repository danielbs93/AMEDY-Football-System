package AcceptanceTesting;
import BuisinessLayer.LogicalOperations.*;
import DataLayer.DataBaseReturnTrueAndAdding;
import Exceptions.*;
import ServiceLayer.TeamOwnerController;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class TeamOwnerAcceptanceTest {
    private static DataBaseReturnTrueAndAdding dataBase;
    private static Team team1;
    private static TeamOwner teamOwner;
    private static Player player1;
    private static Coach coach1;
    private static Stadium stadium1;
    private static TeamManager teamManager1;
    private static TeamOwner teamOwner1;
    private static Player player2;
    private static Coach coach2;
    private static Stadium stadium2;
    private static TeamManager teamManager2;
    private static Authentication authentication;
    private static AMEDYSystem amedySystem;
    private static User NewTeamOwnerUser1;
    private static User NewTeamManagerUser1;
    private static TeamOwner teamOwner2;
    private static TeamManager teamManager3;

    @BeforeEach
     void setUp() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException, ConnectException {

        dataBase = new DataBaseReturnTrueAndAdding();
        authentication = new Authentication(dataBase);
        amedySystem = new AMEDYSystem(authentication);

        team1 = (Team)myFactory.Generate("team","team1");
        teamOwner = new TeamOwner("teamOwner", team1);
        player1 = (Player)myFactory.Generate("player","player1");
        coach1 = (Coach)myFactory.Generate("coach", "coach1");
        stadium1 = (Stadium) myFactory.Generate("stadium", "stadium1");
        teamManager1 = (TeamManager) myFactory.Generate("teamManager", "teamManager1");

        player2 = (Player)myFactory.Generate("player","player2");
        coach2 = (Coach)myFactory.Generate("coach", "coach2");
        stadium2 = (Stadium) myFactory.Generate("stadium", "stadium2");
        teamManager2 = (TeamManager) myFactory.Generate("teamManager", "teamManager2");
        team1.addNewPlayer(player2);
        team1.setCoach(coach2);
        team1.setHomeStadium(stadium2);
        team1.addTeamManager(teamManager2);

        teamOwner1 = new TeamOwner("teamOwner1", null);
        NewTeamOwnerUser1 = new User("user1","1234", amedySystem);
        NewTeamManagerUser1 = new User("user2","1234", amedySystem);

        teamOwner2 = new TeamOwner("teamOwner2", team1);
        teamManager3 = new TeamManager("teamManager",team1);
    }

    /**
     * UC 6.1 tests addAssert
     */
    @Test
    void addNewAssetToTeam() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException {
        assertTrue(TeamOwnerController.addNewAssetToTeam(teamOwner,"player",player1));
        assertTrue(TeamOwnerController.addNewAssetToTeam(teamOwner,"coach",coach1));
        assertTrue(TeamOwnerController.addNewAssetToTeam(teamOwner,"stadium",stadium1));
        assertTrue(TeamOwnerController.addNewAssetToTeam(teamOwner,"teamManager",teamManager1));

        Exception exception = assertThrows(IllegalValueException.class, () ->
                TeamOwnerController.addNewAssetToTeam(teamOwner,null,player1)
        );
        assertEquals("Illegal assert type",exception.getMessage());

        Exception exception1 = assertThrows(IllegalValueException.class, () ->
                TeamOwnerController.addNewAssetToTeam(teamOwner,"player",null)
        );
        assertTrue(exception1 instanceof IllegalValueException);

        Exception exception2 = assertThrows(AssertAlreadyExistException.class, () ->
                TeamOwnerController.addNewAssetToTeam(teamOwner,"player",player1)
        );
        assertEquals("player already exist",exception2.getMessage());


    }
    /**
     * UC 6.1 tests removeAssert
     */
    @Test
    void removeAssetFromTeam() throws IllegalValueException, PermissionDeniedException, AssertIsNotExistException, AssertAlreadyExistException {

        assertTrue(TeamOwnerController.removeAssetFromTeam(teamOwner,"player",player2));
        assertTrue(TeamOwnerController.removeAssetFromTeam(teamOwner,"coach",coach2));
        assertTrue(TeamOwnerController.removeAssetFromTeam(teamOwner,"stadium",stadium2));
        assertTrue(TeamOwnerController.removeAssetFromTeam(teamOwner,"teamManager",teamManager2));

        Exception exception = assertThrows(AssertIsNotExistException.class, () ->
                TeamOwnerController.removeAssetFromTeam(teamOwner,"player",player1)
        );
        assertEquals("player is not exist",exception.getMessage());
    }
    /**
     * UC 6.1 tests editAssert
     */
    @Test
    void editAssetInfo() throws AssertIsNotExistException, PermissionDeniedException, IllegalValueException, AssertAlreadyExistException {
        String [] playerInfo = {"newName", "1","1","1994","striker"};
        Exception exception = assertThrows(AssertIsNotExistException.class, () ->
                TeamOwnerController.editAssetInfo(teamOwner,"player",player1,playerInfo)
        );
        assertEquals("player is not exist",exception.getMessage());

        String [] playerInfo1 = {"newName", "1","1","1994","badName"};
        Exception exception1 = assertThrows(IllegalValueException.class, () ->
                TeamOwnerController.editAssetInfo(teamOwner,"player",player2,playerInfo1)
        );
        assertEquals("player type is not exist", exception1.getMessage());

        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"player",player2,playerInfo));
        playerInfo[4] = "goalKeeper";
        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"player",player2,playerInfo));
        playerInfo[4] = "defender";
        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"player",player2,playerInfo));
        playerInfo[4] = "midfielders";
        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"player",player2,playerInfo));

        String [] coachInfo = {"newName","qualification1"};
        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"coach",coach2,coachInfo));

        String [] stadiumInfo = {"newName"};
        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"stadium",stadium2,stadiumInfo));

        String [] teamMangerInfo= {"newName"};
        assertTrue(TeamOwnerController.editAssetInfo(teamOwner,"teamManager",teamManager2,teamMangerInfo));


    }
    /**
     * UC 6.2 test
     */
    @Test
    void assignNewTeamOwner() throws ProfileTypeExistException, IllegalValueException, AssertAlreadyExistException, PermissionDeniedException, AssertIsNotExistException {

        Exception exception1 = assertThrows(IllegalValueException.class, () ->
                TeamOwnerController.assignNewTeamOwner(teamOwner,null)
        );
        assertEquals("Illegal user", exception1.getMessage());


        assertTrue(TeamOwnerController.assignNewTeamOwner(teamOwner,NewTeamOwnerUser1));

        Exception exception2 = assertThrows(ProfileTypeExistException.class, () ->
                TeamOwnerController.assignNewTeamOwner(teamOwner,NewTeamOwnerUser1)
        );
        assertEquals( "the user has a team owner profile", exception2.getMessage());

    }
    /**
     * UC 6.3 test
     */
    @Test
    void removeAssignTeamOwner() throws AssertIsNotExistException, PermissionDeniedException, IllegalValueException, ProfileTypeExistException, AssertAlreadyExistException {
        teamOwner.assignNewTeamOwner(new User("sdf","sdf",amedySystem));
        assertTrue(TeamOwnerController.removeAssignTeamOwner(teamOwner,teamOwner.getAssignedTeamOwners().getFirst()));

        Exception exception1 = assertThrows(PermissionDeniedException.class, () ->
                TeamOwnerController.removeAssignTeamOwner(teamOwner,teamOwner2)
        );
        assertEquals( "team owner is not assigned by this team owner", exception1.getMessage());

        TeamOwner teamOwner3 = new TeamOwner("teamOwner3",null);
        Exception exception2 = assertThrows(AssertIsNotExistException.class, () ->
                TeamOwnerController.removeAssignTeamOwner(teamOwner,teamOwner3)
        );
        assertEquals( "team owner is not exist", exception2.getMessage());
    }

    /**
     * UC 6.4 test
     */
    @Test
    void assignNewTeamManager() throws ProfileTypeExistException, IllegalValueException {
        Exception exception1 = assertThrows(IllegalValueException.class, () ->
                TeamOwnerController.assignNewTeamManager(teamOwner,null,null)
        );
        assertEquals("Illegal user", exception1.getMessage());


        assertTrue(TeamOwnerController.assignNewTeamManager(teamOwner,NewTeamManagerUser1,null));

        Exception exception2 = assertThrows(ProfileTypeExistException.class, () ->
                TeamOwnerController.assignNewTeamManager(teamOwner,NewTeamManagerUser1,null)
        );
        assertEquals( "the user has a team owner or team manager profile", exception2.getMessage());


    }
    /**
     * UC 6.5 test
     */
    @Test
    void removeAssignTeamManager() throws ProfileTypeExistException, IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        teamOwner.assignNewTeamManager(new User("sdf","sdf",amedySystem),null);
        assertTrue(TeamOwnerController.removeAssignTeamManager(teamOwner,teamOwner.getAssignedTeamManagers().getFirst()));

        Exception exception1 = assertThrows(PermissionDeniedException.class, () ->
                TeamOwnerController.removeAssignTeamManager(teamOwner,teamManager3)
        );
        assertEquals( "team manager is not assigned by this team owner", exception1.getMessage());

        TeamManager teamManager4 = new TeamManager("teamManager4",null);
        Exception exception2 = assertThrows(AssertIsNotExistException.class, () ->
                TeamOwnerController.removeAssignTeamManager(teamOwner,teamManager4)
        );
        assertEquals( "team manager is not exist", exception2.getMessage());
    }
    /**
     * UC 6.6 test
     */
    @Test
    void closeTeam() throws TeamStatusException {
        assertTrue(TeamOwnerController.closeTeam(teamOwner));
        Exception exception = assertThrows(TeamStatusException.class, () ->
                TeamOwnerController.closeTeam(teamOwner)
        );
        assertEquals( "the team is already close", exception.getMessage());
    }
    /**
     * UC 6.7 test
     */
    @Test
    void reopenTeam() throws TeamStatusException {

        Exception exception = assertThrows(TeamStatusException.class, () ->
                TeamOwnerController.reopenTeam(teamOwner)
        );
        assertEquals( "the team is already open", exception.getMessage());

        team1.setStatus(TeamStatus.CLOSE_PERMANENTLY);
        Exception exception1 = assertThrows(TeamStatusException.class, () ->
                TeamOwnerController.reopenTeam(teamOwner)
        );
        assertEquals( "the team is already close permanently", exception1.getMessage());

        team1.setStatus(TeamStatus.CLOSE);
        assertTrue(TeamOwnerController.reopenTeam(teamOwner));
    }
    /**
     * UC 6.8 test
     */
    @Test
    void financeReport() throws IllegalAmountException, IllegalValueException {
        assertTrue(TeamOwnerController.financeReport(teamOwner,"expenditure","new player",100));
        assertTrue(TeamOwnerController.financeReport(teamOwner,"income","new win",100));

        Exception exception1 = assertThrows(IllegalAmountException.class, () ->
                TeamOwnerController.financeReport(teamOwner, "expenditure","new player",0)
        );
        assertEquals( "amount should be a positive value", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalAmountException.class, () ->
                TeamOwnerController.financeReport(teamOwner, "income","new player",0)
        );
        assertEquals( "amount should be a positive value", exception2.getMessage());
    }
}