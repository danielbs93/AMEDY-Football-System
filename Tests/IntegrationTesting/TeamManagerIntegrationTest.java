package IntegrationTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.*;
import UnitTesting.myFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamManagerIntegrationTest {
    private static TeamManager teamManager;

    @BeforeEach
    void setUp() throws IllegalValueException {
        teamManager = (TeamManager) myFactory.Generate("teamManager", "testTeamManager");
        teamManager.setAccessPermission("addNewPlayer",true);
        teamManager.setAccessPermission("addNewCoach",true);
        teamManager.setAccessPermission("addNewStadium",true);
        teamManager.setAccessPermission("addNewTeamManager",true);
        teamManager.setAccessPermission("removePlayer",true);
        teamManager.setAccessPermission("removeCoach",true);
        teamManager.setAccessPermission("removeStadium",true);
        teamManager.setAccessPermission("removeTeamManager",true);
        teamManager.setAccessPermission("editPlayer",true);
        teamManager.setAccessPermission("editCoach",true);
        teamManager.setAccessPermission("editStadium",true);
        teamManager.setAccessPermission("editTeamManager",true);
    }

    @Test
    void addNewPlayer()  {
        //illegal input
        try {
            teamManager.addNewPlayer(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        try {
            teamManager.addNewPlayer(newPlayer);
            assertTrue(newPlayer.equals(teamManager.getTeam().getPlayers().get(11)));

            //Checking exception to insertion of an existing assert
            teamManager.addNewPlayer(newPlayer);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }

    }

    @Test
    void addNewCoach() {
        //illegal input
        try {
            teamManager.addNewCoach(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        Coach newCoach = (Coach)myFactory.Generate("coach", "testCoach");
        try {
            teamManager.addNewCoach(newCoach);
            assertTrue(newCoach.equals(teamManager.getTeam().getCoach()));

            //Checking exception to insertion of an existing assert
            teamManager.addNewCoach(newCoach);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }

    }

    @Test
    void addNewStadium() throws IllegalValueException {

        //illegal input
        try {
            teamManager.addNewStadium(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        Stadium newStadium = (Stadium) myFactory.Generate("stadium", "stadium1");
        try {
            teamManager.addNewStadium(newStadium);
            assertTrue(newStadium.equals(teamManager.getTeam().getHomeStadium()));

            //Checking exception to insertion of an existing assert
            teamManager.addNewStadium(newStadium);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }
    }

    @Test
    void addNewTeamManager() throws IllegalValueException {
        teamManager.setAccessPermission("addNewTeamManager",true);

        //illegal input
        try {
            teamManager.addNewTeamManager(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking insertion of assert
        TeamManager newTeamManager = (TeamManager) myFactory.Generate("teamManager", "teamManager1");
        try {
            teamManager.addNewTeamManager(newTeamManager);
            assertTrue(newTeamManager.equals(teamManager.getTeam().getTeamManagers().get(1)));

            //Checking exception to insertion of an existing assert
            teamManager.addNewTeamManager(newTeamManager);
        } catch (Exception e) {
            assertTrue(e instanceof AssertAlreadyExistException);
        }
    }

    @Test
    void removePlayer() {

        //illegal input
        try {
            teamManager.removePlayer(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        try {
            teamManager.removePlayer(newPlayer);
            assertTrue(!teamManager.getTeam().getPlayers().contains(newPlayer));

            //Checking exception to remove an not existing assert
            teamManager.removePlayer(newPlayer);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void removeCoach() {
        //illegal input
        try {
            teamManager.removeCoach(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        Coach newCoach = (Coach) myFactory.Generate("coach", "testCoach");
        try {
            teamManager.removeCoach(newCoach);
            assertTrue(!teamManager.getTeam().getCoach().equals(newCoach));

            //Checking exception to remove an not existing assert
            teamManager.removeCoach(newCoach);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void removeStadium() {
        //illegal input
        try {
            teamManager.removeStadium(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        Stadium newStadium = (Stadium) myFactory.Generate("stadium", "testStadium");
        try {
            teamManager.removeStadium(newStadium);
            assertTrue(!teamManager.getTeam().getHomeStadium().equals(newStadium));

            //Checking exception to remove an not existing assert
            teamManager.removeStadium(newStadium);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void removeTeamManager() {
        //illegal input
        try {
            teamManager.removeTeamManager(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        //checking remove of assert
        TeamManager newTeamManager = (TeamManager) myFactory.Generate("teamManager", "testTeamManager");
        try {
            teamManager.removeTeamManager(newTeamManager);
            assertTrue(!teamManager.getTeam().getTeamManagers().contains(newTeamManager));

            //Checking exception to remove an not existing assert
            teamManager.removeTeamManager(newTeamManager);
        } catch (Exception e) {
            assertTrue(e instanceof AssertIsNotExistException);
        }
    }

    @Test
    void editPlayer() {

        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        //illegal input
        try {
            teamManager.editPlayer(null,"testPlayer",new Date(1,1,1994),PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamManager.editPlayer(newPlayer,null ,new Date(1,1,1994),PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }try {
            teamManager.editPlayer(newPlayer,"testPlayer" ,null, PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }try {
            teamManager.editPlayer(newPlayer,"testPlayer",new Date(1,1,1994),null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamManager.editPlayer(newPlayer,"testPlayer",new Date(1,1,1994),PlayerType.Defender);
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamManager.addNewPlayer(newPlayer);
            teamManager.editPlayer(newPlayer, "nameChanged", new Date(1, 1, 1994), PlayerType.Defender);
            assertEquals("nameChanged", teamManager.getTeam().getPlayers().get(11).getName());
        }catch (Exception e){

        }

    }

    @Test
    void editCoach() {

        Coach newCoach = (Coach) myFactory.Generate("coach", "testCoach");
        //illegal input
        try {
            teamManager.editCoach(null,"testCoach","1");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamManager.editCoach(newCoach,null ,"1");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }try {
            teamManager.editCoach(newCoach,"testCoach" ,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamManager.editCoach(newCoach,"testCoach","1");
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamManager.addNewCoach(newCoach);
            teamManager.editCoach(newCoach,"nameChanged","1");
            assertEquals("nameChanged", teamManager.getTeam().getCoach().getName());
        }catch (Exception e){

        }

    }

    @Test
    void editStadium() {

        Stadium newStadium = (Stadium) myFactory.Generate("stadium", "testStadium");
        //illegal input
        try {
            teamManager.editStadium(null,"testStadium");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamManager.editStadium(newStadium,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit an not existing assert
        try{
            teamManager.editStadium(newStadium,"testStadium");
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamManager.addNewStadium(newStadium);
            teamManager.editStadium(newStadium,"nameChanged");
            assertEquals("nameChanged", teamManager.getTeam().getHomeStadium().getStadiumName());
        }catch (Exception e){

        }

    }

    @Test
    void editTeamManager() {
        TeamManager newTeamManager = (TeamManager) myFactory.Generate("teamManager", "testTeamManager1");
        //illegal input
        try {
            teamManager.editTeamManager(null,"testTeamManager");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamManager.editTeamManager(newTeamManager,null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

        //Checking exception to edit a not existing assert
        try{
            teamManager.editTeamManager(newTeamManager,"testTeamManager");
        } catch (Exception e){
            assertTrue(e instanceof  AssertIsNotExistException);
        }
        //Checking edit assert
        try {
            teamManager.addNewTeamManager(newTeamManager);
            teamManager.editTeamManager(newTeamManager,"nameChanged");
            assertEquals("nameChanged", teamManager.getTeam().getTeamManagers().get(1).getName());
        }catch (Exception e){

        }
    }
}