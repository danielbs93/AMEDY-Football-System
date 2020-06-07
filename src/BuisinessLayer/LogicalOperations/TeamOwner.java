package BuisinessLayer.LogicalOperations;

import Exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Michal Talmor
 * @version 1.0
 *
 */
public class TeamOwner extends TeamManagement {
    //Fields
    protected List<Pair> allPersonalInfo;

    //Connections

    private ConcurrentLinkedDeque<TeamOwner> assignedTeamOwners;
    private ConcurrentLinkedDeque<TeamManager> assignedTeamManagers;

    public TeamOwner(String name, Team team) {
        super(name,"TeamOwner");

        this.team = team;
        assignedTeamOwners = new ConcurrentLinkedDeque<>();
        assignedTeamManagers = new ConcurrentLinkedDeque<>();
        if (team!=null) {
            try {
                this.team.addTeamOwner(this);
            } catch (AssertAlreadyExistException e) {
                e.printStackTrace();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    @Override
    public synchronized List<Pair> getPersonalInfo()
    {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "TeamOwner"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        this.allPersonalInfo.add(new Pair("team", this.team.getTeamName()));
        return this.allPersonalInfo;
    }

    public ConcurrentLinkedDeque<TeamManager> getAssignedTeamManagers() {
        return assignedTeamManagers;
    }

    public void setAssignedTeamManagers(ConcurrentLinkedDeque<TeamManager> assignedTeamManagers) {
        this.assignedTeamManagers = assignedTeamManagers;
    }

    public ConcurrentLinkedDeque<TeamOwner> getAssignedTeamOwners() {
        return assignedTeamOwners;
    }
    public void setAssignedTeamOwners(ConcurrentLinkedDeque<TeamOwner> assignedTeamOwners) {
        this.assignedTeamOwners = assignedTeamOwners;
    }
    /**
     * create a new TeamOwner profile and connect it into this user
     * add the new TeamOwner profile into the team
     * @param user to assign as team owner
     * @return true if success
     * @throws IllegalValueException
     */
    public boolean assignNewTeamOwner(User user) throws IllegalValueException, ProfileTypeExistException, AssertAlreadyExistException {
       if (user==null){
           throw new IllegalValueException("Illegal user");
       }
       //check if there is no team owner profile for this user
        /*
        if (user.getProfile("TeamOwner")!=null){
            throw new ProfileTypeExistException();
        }
        */
        for (Profile p : user.getProfiles()){
            if (p.ClassType.equals("TeamOwner")){
                throw new ProfileTypeExistException("the user has a team owner profile");
            }
        }


       TeamOwner newTeamOwner = new TeamOwner(user.getUserName(),team);
       user.addProfile(newTeamOwner);
       assignedTeamOwners.add(newTeamOwner);
       return true;
    }

    public boolean removeAssignTeamOwner(TeamOwner teamOwner) throws IllegalValueException, ProfileTypeExistException, PermissionDeniedException, AssertIsNotExistException {
        if (teamOwner==null){
            throw new IllegalValueException("team owner is not exist");
        }
        //team owner is not exist
        if (!team.getTeamOwners().contains(teamOwner)){
            throw new AssertIsNotExistException("team owner is not exist");
        }
        //The team owner is not assigned by this team owner
        if (!assignedTeamOwners.contains(teamOwner)){
            throw new PermissionDeniedException("team owner is not assigned by this team owner");
        }

        //remove all teamOwners assigned by

        for (TeamOwner t: teamOwner.getAssignedTeamOwners()){
            teamOwner.removeAssignTeamOwner(t);
        }
        //remove all teamManager assigned by
        for (TeamManager t: teamOwner.getAssignedTeamManagers()){
            teamOwner.removeAssignTeamManager(t);
        }
        getAssignedTeamOwners().remove(teamOwner); //remove from TeamOwnersAssigned list
        team.removeTeamOwner(teamOwner); //remove from team arrayList
        teamOwner.getMyUser().removeProfile(teamOwner); //disconnecting profile from user
        return true;
    }


    public boolean assignNewTeamManager(User user, ConcurrentHashMap<String ,Boolean> permissions) throws IllegalValueException, ProfileTypeExistException {
        if (user==null){
            throw new IllegalValueException("Illegal user");
        }

        //check if there is no team manager profile for this user
        for (Profile p : user.getProfiles()){
            if (p.ClassType.equals("TeamManager") || p.ClassType.equals("TeamOwner")){
                throw new ProfileTypeExistException("the user has a team owner or team manager profile");
            }
        }

        TeamManager teamManager = new TeamManager(user.getUserName(),team);
        teamManager.setPermissions(permissions); //set permissions
        user.addProfile(teamManager);
        assignedTeamManagers.add(teamManager);
        return true;
    }

    public boolean removeAssignTeamManager(TeamManager teamManager) throws IllegalValueException, PermissionDeniedException, AssertIsNotExistException {
        if (teamManager==null){
            throw new IllegalValueException("team manager is not exist");
        }
        //team manager is not exist
        if (!team.getTeamManagers().contains(teamManager)){
            throw new AssertIsNotExistException("team manager is not exist");
        }
        //The team owner is not assigned by this team owner
        if (!assignedTeamManagers.contains(teamManager)){
            throw new PermissionDeniedException("team manager is not assigned by this team owner");
        }
        getAssignedTeamManagers().remove(teamManager); //remove from TeamOwnersAssigned list
        team.removeTeamManager(teamManager); //remove from team arrayList
        teamManager.getMyUser().removeProfile(teamManager); //disconnecting profile from user
        return true;
    }

    public boolean closeTeam() throws TeamStatusException {
        //team is already close
        if (team.getStatus().equals(TeamStatus.CLOSE)||
                team.getStatus().equals(TeamStatus.CLOSE_PERMANENTLY)){
            throw new TeamStatusException("the team is already close");
        }
        team.setStatus(TeamStatus.CLOSE);

         // TODO: send alert to team managers, team owners and system manager
         // TODO: save team activity

        return true;
    }

    public boolean reopenTeam() throws TeamStatusException {
        //team is CLOSE_PERMANENTLY already ACTIVE
        if (team.getStatus().equals(TeamStatus.ACTIVE)){
            throw new TeamStatusException("the team is already open");
        }

        if ( team.getStatus().equals(TeamStatus.CLOSE_PERMANENTLY)){
            throw new TeamStatusException("the team is already close permanently");
        }
        team.setStatus(TeamStatus.ACTIVE);

         //TODO: send alert to team managers, team owners and system manager
        //TODO:  give permissions again
         return true;
    }
    /**
     * sub functions for UC 6.7 - financeReport
     */
    public boolean newExpenditureFinancialReport(String report, double amount) throws IllegalValueException, IllegalAmountException {

        return  team.addExpenditureFinancialReport(report,amount);

    }
    public boolean newIncomeFinancialReport(String report, double amount) throws IllegalValueException, IllegalAmountException {

        return team.addIncomeFinancialReport(report,amount);
    }

}
