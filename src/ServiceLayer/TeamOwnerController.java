package ServiceLayer;

import BuisinessLayer.LogicalOperations.TeamManager;
import BuisinessLayer.LogicalOperations.TeamOwner;
import BuisinessLayer.LogicalOperations.User;
import Exceptions.*;

import java.util.concurrent.ConcurrentHashMap;

public class TeamOwnerController extends TeamManagementController {


    public TeamOwnerController(TeamOwner teamOwner) {

    }



    public static boolean assignNewTeamOwner(TeamOwner teamOwner, User user) throws ProfileTypeExistException, IllegalValueException, AssertAlreadyExistException {
        return teamOwner.assignNewTeamOwner(user);
    }

    public static boolean removeAssignTeamOwner(TeamOwner teamOwner, TeamOwner teamOwnerToBeRemove) throws AssertIsNotExistException, PermissionDeniedException, IllegalValueException, ProfileTypeExistException {
        return teamOwner.removeAssignTeamOwner(teamOwnerToBeRemove);
    }

    public static boolean assignNewTeamManager(TeamOwner teamOwner, User user, ConcurrentHashMap<String ,Boolean> permissions) throws ProfileTypeExistException, IllegalValueException {

        return teamOwner.assignNewTeamManager(user, permissions);
    }

    public static boolean removeAssignTeamManager(TeamOwner teamOwner, TeamManager TeamMangerToBeRemove) throws IllegalValueException, PermissionDeniedException, AssertIsNotExistException {
        return teamOwner.removeAssignTeamManager(TeamMangerToBeRemove);
    }

    public static boolean closeTeam(TeamOwner teamOwner) throws TeamStatusException {
        return teamOwner.closeTeam();
    }

    public static boolean reopenTeam(TeamOwner teamOwner) throws TeamStatusException {

        return teamOwner.reopenTeam();
    }

    public static boolean financeReport(TeamOwner teamOwner, String financeReportType, String report, double amount) throws IllegalAmountException, IllegalValueException {
        if (financeReportType == "expenditure"){
           return teamOwner.newExpenditureFinancialReport(report, amount);
        }
        if (financeReportType == "income"){
            return teamOwner.newIncomeFinancialReport(report, amount);
        }
        return false;
    }

}
