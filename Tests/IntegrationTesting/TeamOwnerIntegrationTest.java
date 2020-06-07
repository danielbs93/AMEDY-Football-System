package IntegrationTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.*;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamOwnerIntegrationTest {
    private TeamOwner teamOwner;


    @BeforeEach
    void setUp() {
        teamOwner = (TeamOwner) myFactory.Generate("teamOwner", "testTeamOwner");
    }

    @Test
    void addNewPlayer() {
        //illegal input
        try {
            teamOwner.addNewPlayer(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        try {
            teamOwner.addNewPlayer(newPlayer);
            assertTrue(newPlayer.equals(teamOwner.getTeam().getPlayers().get(11)));

            //Checking exception to insertion of an existing assert
            teamOwner.addNewPlayer(newPlayer);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }

    }

    @Test
    void addNewCoach() {
        //illegal input
        try {
            teamOwner.addNewCoach(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        Coach newCoach = (Coach)myFactory.Generate("coach", "testCoach");
        try {
            teamOwner.addNewCoach(newCoach);
            assertTrue(newCoach.equals(teamOwner.getTeam().getCoach()));

            //Checking exception to insertion of an existing assert
            teamOwner.addNewCoach(newCoach);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }

    }

    @Test
    void addNewStadium() {
        //illegal input
        try {
            teamOwner.addNewStadium(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        Stadium newStadium = (Stadium) myFactory.Generate("stadium", "stadium1");
        try {
            teamOwner.addNewStadium(newStadium);
            assertTrue(newStadium.equals(teamOwner.getTeam().getHomeStadium()));

            //Checking exception to insertion of an existing assert
            teamOwner.addNewStadium(newStadium);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }
    }

    @Test
    void addNewTeamManager() {
        //illegal input
        try {
            teamOwner.addNewTeamManager(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        TeamManager newTeamManager = (TeamManager) myFactory.Generate("teamManager", "teamManager1");
        try {
            teamOwner.addNewTeamManager(newTeamManager);
            assertTrue(newTeamManager.equals(teamOwner.getTeam().getTeamManagers().get(0)));

            //Checking exception to insertion of an existing assert
            teamOwner.addNewTeamManager(newTeamManager);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }
    }

    @Test
    void removePlayer() {

        //illegal input
        try {
            teamOwner.removePlayer(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        try {
            teamOwner.removePlayer(newPlayer);
            assertTrue(!teamOwner.getTeam().getPlayers().contains(newPlayer));

            //Checking exception to remove an not existing assert
            teamOwner.removePlayer(newPlayer);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void removeCoach() {
        //illegal input
        try {
            teamOwner.removeCoach(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        Coach newCoach = (Coach) myFactory.Generate("coach", "testCoach");
        try {
            teamOwner.removeCoach(newCoach);
            assertTrue(!teamOwner.getTeam().getCoach().equals(newCoach));

            //Checking exception to remove an not existing assert
            teamOwner.removeCoach(newCoach);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void removeStadium() {
        //illegal input
        try {
            teamOwner.removeStadium(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        Stadium newStadium = (Stadium) myFactory.Generate("stadium", "testStadium");
        try {
            teamOwner.removeStadium(newStadium);
            assertTrue(!teamOwner.getTeam().getHomeStadium().equals(newStadium));

            //Checking exception to remove an not existing assert
            teamOwner.removeStadium(newStadium);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void removeTeamManager() {
        //illegal input
        try {
            teamOwner.removeTeamManager(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        TeamManager newTeamManager = (TeamManager) myFactory.Generate("teamManager", "testTeamManager");
        try {
            teamOwner.removeTeamManager(newTeamManager);
            assertTrue(!teamOwner.getTeam().getTeamManagers().contains(newTeamManager));

            //Checking exception to remove an not existing assert
            teamOwner.removeTeamManager(newTeamManager);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void editPlayer() {

        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        //illegal input
        try {
            teamOwner.editPlayer(null,"testPlayer",new Date(1,1,1994),PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamOwner.editPlayer(newPlayer,null ,new Date(1,1,1994),PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }try {
            teamOwner.editPlayer(newPlayer,"testPlayer" ,null,PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }try {
            teamOwner.editPlayer(newPlayer,"testPlayer",new Date(1,1,1994),null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamOwner.editPlayer(newPlayer,"testPlayer",new Date(1,1,1994),PlayerType.Defender);
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamOwner.addNewPlayer(newPlayer);
            teamOwner.editPlayer(newPlayer, "nameChanged", new Date(1, 1, 1994), PlayerType.Defender);
            assertEquals("nameChanged", teamOwner.getTeam().getPlayers().get(11).getName());
        }catch (Exception e){

        }

    }

    @Test
    void editCoach() {

        Coach newCoach = (Coach) myFactory.Generate("coach", "testCoach");
        //illegal input
        try {
            teamOwner.editCoach(null,"testCoach","1");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamOwner.editCoach(newCoach,null ,"1");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }try {
            teamOwner.editCoach(newCoach,"testCoach" ,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamOwner.editCoach(newCoach,"testCoach","1");
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamOwner.addNewCoach(newCoach);
            teamOwner.editCoach(newCoach,"nameChanged","1");
            assertEquals("nameChanged", teamOwner.getTeam().getCoach().getName());
        }catch (Exception e){

        }

    }

    @Test
    void editStadium() {

        Stadium newStadium = (Stadium) myFactory.Generate("stadium", "testStadium");
        //illegal input
        try {
            teamOwner.editStadium(null,"testStadium");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamOwner.editStadium(newStadium,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamOwner.editStadium(newStadium,"testStadium");
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamOwner.addNewStadium(newStadium);
            teamOwner.editStadium(newStadium,"nameChanged");
            assertEquals("nameChanged", teamOwner.getTeam().getHomeStadium().getStadiumName());
        }catch (Exception e){

        }

    }

    @Test
    void editTeamManager() {
        TeamManager newTeamManager = (TeamManager) myFactory.Generate("teamManager", "testTeamManager");
        //illegal input
        try {
            teamOwner.editTeamManager(null,"testTeamManager");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamOwner.editTeamManager(newTeamManager,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamOwner.editTeamManager(newTeamManager,"testTeamManager");
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamOwner.addNewTeamManager(newTeamManager);
            teamOwner.editTeamManager(newTeamManager,"nameChanged");
            assertEquals("nameChanged", teamOwner.getTeam().getTeamManagers().get(0).getName());
        }catch (Exception e){

        }
    }

    @Test
    void assignNewTeamOwner() {
        User newUser = new User("michal", "1234", null);
        //illegal input
        try {
            teamOwner.assignNewTeamOwner(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        try {
            teamOwner.assignNewTeamOwner(newUser);
            assertEquals(2,teamOwner.getTeam().getTeamOwners().size());
            assertEquals(1,teamOwner.getAssignedTeamOwners().size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Checking exception to existing profile
            TeamOwner teamOwnerProfile = (TeamOwner)myFactory.Generate("teamOwner", "teamOwnerProfile");
            newUser.addProfile(teamOwnerProfile);
            teamOwner.assignNewTeamOwner(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof ProfileTypeExistException);
        }

    }

    @Test
    void removeAssignTeamOwner() {
        TeamOwner teamOwnerProfile = (TeamOwner)myFactory.Generate("teamOwner", "teamOwnerProfile");
        User newUser = new User("michal", "1234", null);
        User newUser2 = new User("michal2", "1234", null);
        User newUser3 = new User("michal3", "1234", null);

        //illegal input
        try {
            assertFalse(teamOwner.removeAssignTeamOwner(null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //team owner not existing
        try {
            assertFalse(teamOwner.removeAssignTeamOwner(teamOwnerProfile));
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }

        //team owner is not assigned by this team Owner exception
        try {
            teamOwner.assignNewTeamOwner(newUser);
            teamOwner.getAssignedTeamOwners().getFirst().assignNewTeamOwner(newUser2);
            assertFalse(teamOwner.removeAssignTeamOwner(teamOwner.getTeam().getTeamOwners().get(2)));
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

        //remove team owner and all assigned by
        try {
            assertTrue(teamOwner.removeAssignTeamOwner(teamOwner.getAssignedTeamOwners().getFirst()));
            assertEquals(1,teamOwner.getTeam().getTeamOwners().size());
            assertEquals(0,teamOwner.getAssignedTeamOwners().size());

        } catch (Exception e) {
            assertFalse(e instanceof Exception);
        }


    }

    @Test
    void assignNewTeamManager() {
        User newUser = new User("michal", "1234", null);
        //illegal input
        try {
            assertFalse(teamOwner.assignNewTeamManager(null,null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        try {
            teamOwner.assignNewTeamManager(newUser,null);
            assertEquals(1,teamOwner.getTeam().getTeamManagers().size());
            assertEquals(1,teamOwner.getAssignedTeamManagers().size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Checking exception to existing profile
            TeamManager teamManagerProfile = (TeamManager) myFactory.Generate("teamManager", "teamManagerProfile");
            newUser.addProfile(teamManagerProfile);
            assertTrue(teamOwner.assignNewTeamManager(newUser,null));
        } catch (Exception e) {
            assertTrue(e instanceof ProfileTypeExistException);
        }
    }

    @Test
    void removeAssignTeamManager() {
        TeamManager teamManagerProfile = (TeamManager) myFactory.Generate("teamManager", "teamManagerProfile");
        User newUser = new User("michal", "1234", null);

        //illegal input
        try {
            assertFalse(teamOwner.removeAssignTeamManager(null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //team manager not existing
        try {
            assertFalse(teamOwner.removeAssignTeamManager(teamManagerProfile));
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }


        //remove team manager
        try {
            teamOwner.assignNewTeamManager(newUser, null);
            assertTrue(teamOwner.removeAssignTeamManager(teamOwner.getAssignedTeamManagers().getFirst()));
            assertEquals(0,teamOwner.getTeam().getTeamManagers().size());
            assertEquals(0,teamOwner.getAssignedTeamManagers().size());

        } catch (Exception e) {
            assertFalse(e instanceof Exception);
        }


    }

    @Test
    void closeTeam() {
        try {
            teamOwner.closeTeam();
            assertEquals(TeamStatus.CLOSE, teamOwner.getTeam().getStatus());
        } catch (TeamStatusException e) {
            assertFalse(e instanceof Exception);
        }
        try {
            assertTrue(teamOwner.closeTeam());
        } catch (Exception e) {
            assertTrue(e instanceof TeamStatusException);
        }
    }

    @Test
    void reopenTeam() {
        try {
            teamOwner.reopenTeam();
        } catch (Exception e) {
            assertTrue(e instanceof  TeamStatusException);
        }
        try {
            teamOwner.getTeam().setStatus(TeamStatus.CLOSE);
            teamOwner.reopenTeam();
            assertEquals(TeamStatus.ACTIVE, teamOwner.getTeam().getStatus());
        } catch (Exception e) {
            assertFalse(e instanceof Exception);
        }
    }

    @Test
    void newExpenditureFinancialReport() throws IllegalAmountException, IllegalValueException {
        assertTrue(teamOwner.newExpenditureFinancialReport("test",12));
    }

    @Test
    void newIncomeFinancialReport() throws IllegalValueException, IllegalAmountException {
        assertTrue(teamOwner.newIncomeFinancialReport("test",12));

    }
}