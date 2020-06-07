package UnitTesting;

import BuisinessLayer.LogicalOperations.*;
import Exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    Team team;

    @BeforeEach
    void setUp() {
         team = (Team)myFactory.Generate("team", "newTeam");
    }

    @Test
    void addExpenditureFinancialReport() {
        try {
            team.addExpenditureFinancialReport(null, 10);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            team.addExpenditureFinancialReport("", 10);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            team.addExpenditureFinancialReport("test", 0);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalAmountException);
        }
        try {
            assertTrue(team.addExpenditureFinancialReport("test", 10));
        } catch (Exception e) {
            assertFalse(e instanceof Exception);
        }
    }

    @Test
    void addIncomeFinancialReport() {
        try {
            team.addIncomeFinancialReport(null, 10);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            team.addIncomeFinancialReport("", 10);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalValueException);
        }
        try {
            team.addIncomeFinancialReport("test", 0);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalAmountException);
        }
        try {
            assertTrue(team.addIncomeFinancialReport("test", 10));
        } catch (Exception e) {
            assertFalse(e instanceof Exception);
        }
    }

    @Test
    void addTeamOwner() throws AssertAlreadyExistException, IllegalValueException {
        TeamOwner teamOwner = new TeamOwner("newTeamOwner", null);
        team.addTeamOwner(teamOwner);
        assertEquals(1, team.getTeamOwners().size());
    }

    @Test
    void addTeamManager() throws IllegalValueException, AssertAlreadyExistException {
        TeamManager teamManager = new TeamManager("newTeamManager", null);
        team.addTeamManager(teamManager);
        assertEquals(1, team.getTeamManagers().size());
    }

    @Test
    void removeTeamOwner() throws AssertIsNotExistException, PermissionDeniedException, IllegalValueException, ProfileTypeExistException {
        TeamOwner teamOwner = new TeamOwner("newTeamOwner", team);
        team.removeTeamOwner(teamOwner);
        assertEquals(0, team.getTeamOwners().size());

    }

    @Test
    void removeTeamManager() throws AssertIsNotExistException, IllegalValueException {
        TeamManager teamManager = new TeamManager("newTeamManager", team);
        team.removeTeamManager(teamManager);
        assertEquals(0, team.getTeamManagers().size());
    }

    @Test
    void addNewPlayer() throws AssertAlreadyExistException, IllegalValueException, PermissionDeniedException {
        Player player = (Player)myFactory.Generate("player", "newPlayer");
        team.addNewPlayer(player);
        assertEquals(12, team.getPlayers().size());

    }

    @Test
    void removePlayer() throws IllegalValueException, PermissionDeniedException, AssertIsNotExistException {
        Player player = (Player)myFactory.Generate("player", "newPlayer");
        team.removePlayer(team.getPlayers().get(0));
        assertEquals(10, team.getPlayers().size());
    }
    @Test
    void getStatus() {
        assertEquals(TeamStatus.ACTIVE, team.getStatus());
    }

    @Test
    void setStatus() {
        team.setStatus(TeamStatus.CLOSE);
        assertEquals(TeamStatus.CLOSE, team.getStatus());
    }


}