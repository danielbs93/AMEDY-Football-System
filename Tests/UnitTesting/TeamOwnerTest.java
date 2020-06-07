package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamOwnerTest {
    //Connections
    private TeamOwner teamOwner;


    @BeforeEach
    void setUp() {
        teamOwner = (TeamOwner)myFactory.Generate("teamOwner", "testTeamOwner");
    }

    @Test
    void addNewPlayer() {
        //illegal input
        try {
            teamOwner.addNewPlayer(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
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


    }

    @Test
    void addNewStadium() {
        //illegal input
        try {
            teamOwner.addNewStadium(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
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

    }

    @Test
    void removePlayer() {

        //illegal input
        try {
            teamOwner.removePlayer(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
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

    }

    @Test
    void removeStadium() {
        //illegal input
        try {
            teamOwner.removeStadium(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
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

    }

    @Test
    void editPlayer() {

        Player newPlayer = (Player)myFactory.Generate("player", "testPlayer");
        //illegal input
        try {
            teamOwner.editPlayer(null,"testPlayer",new Date(1,1,1994), PlayerType.Defender);
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


    }

    @Test
    void assignNewTeamOwner() {
        //illegal input
        try {
            teamOwner.assignNewTeamOwner(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

    }

    @Test
    void removeAssignTeamOwner() {

        //illegal input
        try {
            assertFalse(teamOwner.removeAssignTeamOwner(null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

    }

    @Test
    void assignNewTeamManager() {
        //illegal input
        try {
          assertFalse(teamOwner.assignNewTeamManager(null,null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }

    }

    @Test
    void removeAssignTeamManager() {

        //illegal input
        try {
            assertFalse(teamOwner.removeAssignTeamManager(null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
    }

    /**
     * test for UseCase 3.6.1 - ViewMyPersonalInfo.
     */
    @Test
    void test_getPersonalInfo()
    {
        List<Pair> fanPersonalInfo = this.teamOwner.getPersonalInfo();
        for(Pair currentPair : fanPersonalInfo)
        {
            if(currentPair.getKey().equals("Type"))
            {
                assertEquals("TeamOwner", currentPair.getValue());
            }
            else if(currentPair.getKey().equals("name"))
            {
                assertEquals(currentPair.getValue(), "testteamowner");
            }
            else if(currentPair.getKey().equals("team"))
            {
                assertEquals(currentPair.getValue(), "testteamowner teamownerteam");
            }
            else
            {
                assertEquals(true, false);
            }
        }
    }

}