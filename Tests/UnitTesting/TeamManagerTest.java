package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamManagerTest {

    TeamManager teamManager;
    Team team;
    @BeforeEach
    void setUp() {
        team = (Team)myFactory.Generate("team","team1");
        teamManager = new TeamManager("teamManager",team);
    }

    @Test
    void setAccessPermission() {

        try {
            assertFalse(teamManager.setAccessPermission("addTeamOwner",true));
        } catch (Exception e) {
           assertTrue(e instanceof IllegalValueException);
        }
        try {
            teamManager.setAccessPermission("addNewPlayer",true);
            assertEquals(true, teamManager.getPermissions().get("addNewPlayer"));
        } catch (Exception e) {
            assertFalse(e instanceof Exception);
        }
    }
    @Test
    void addNewPlayer() {
        //check permission
        Player player = (Player)myFactory.Generate("player","player1");
        try {
            teamManager.addNewPlayer(player);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void addNewCoach() {
        //check permission
        Coach coach = (Coach) myFactory.Generate("coach","coach1");
        try {
            teamManager.addNewCoach(coach);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }
    }

    @Test
    void addNewStadium() {
        //check permission
        Stadium stadium = (Stadium) myFactory.Generate("stadium","stadium");
        try {
            teamManager.addNewStadium(stadium);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void addNewTeamManager() {
        //check permission
        TeamManager teamManager = (TeamManager) myFactory.Generate("teamManager","teamManager");
        try {
            teamManager.addNewTeamManager(teamManager);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void removePlayer() {

        //check permission
        Player player = (Player)myFactory.Generate("player","player1");
        try {
            teamManager.removePlayer(player);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void removeCoach() {
        //check permission
        Coach coach = (Coach) myFactory.Generate("coach","coach1");
        try {
            teamManager.removeCoach(coach);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void removeStadium() {
        //check permission
        Stadium stadium = (Stadium) myFactory.Generate("stadium","stadium");
        try {
            teamManager.removeStadium(stadium);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void removeTeamManager() {
        //check permission
        TeamManager teamManager = (TeamManager) myFactory.Generate("teamManager","teamManager");
        try {
            teamManager.removeTeamManager(teamManager);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }
    }

    @Test
    void editPlayer() {


        //check permission
        Player player = (Player)myFactory.Generate("player","player1");
        try {
            teamManager.editPlayer(player,"testPlayer",new Date(1,1,1994), PlayerType.Defender);
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }


    }

    @Test
    void editCoach() {

        //check permission
        Coach coach = (Coach) myFactory.Generate("coach","coach1");
        try {
            teamManager.editCoach(coach,"testCoach","1");
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }

    }

    @Test
    void editStadium() {

        //check permission
        Stadium stadium = (Stadium) myFactory.Generate("stadium","stadium");
        try {
            teamManager.editStadium(stadium,"testStadium");
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }
    }

    @Test
    void editTeamManager() {
        //check permission
        TeamManager teamManager = (TeamManager) myFactory.Generate("teamManager","teamManager");
        try {
            teamManager.editTeamManager(teamManager,"testTeamManager");
        } catch (Exception e) {
            assertTrue(e instanceof PermissionDeniedException);
        }
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> fanPersonalInfo = this.teamManager.getPersonalInfo();
        for(Pair currentPair : fanPersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("TeamManager", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals(currentPair.getValue(), "teamManager");
            }
            else if(currentPair.getKey().equals("team"))
            {
                assertEquals(currentPair.getValue(), "team1");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }
}