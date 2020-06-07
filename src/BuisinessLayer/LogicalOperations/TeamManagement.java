package BuisinessLayer.LogicalOperations;

import Exceptions.AssertAlreadyExistException;
import Exceptions.AssertIsNotExistException;
import Exceptions.IllegalValueException;
import Exceptions.PermissionDeniedException;

import java.util.ArrayList;
import java.util.Date;

public abstract class TeamManagement extends ProfileEventMaker {

    //Connections
    protected Team team;
    private ArrayList<TeamOwner> assignedTeamOwners;
    private ArrayList<TeamManager> assignedTeamManagers;

    /**
     * Construct an Observable with zero Observers.
     *
     * @param name
     * @param classtype
     */
    public TeamManagement(String name, String classtype) {
        super(name, classtype);
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }



    /**
     *
     * sub function for UC 6.1 - addAssert
     */
    public boolean addNewPlayer (Player newPlayer) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (newPlayer==null){
            throw new IllegalValueException();
        }
        return team.addNewPlayer(newPlayer);
    }
    public boolean addNewCoach (Coach newCoach) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (newCoach==null){
            throw new IllegalValueException();
        }
        if (getTeam().getCoach().equals(newCoach)) {
            throw new AssertAlreadyExistException();
        }
        team.setCoach(newCoach);
        return true;
    }
    public boolean addNewStadium (Stadium newStadium) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (newStadium==null){
            throw new IllegalValueException();
        }
        if (team.getHomeStadium().equals(newStadium)) {
            throw new AssertAlreadyExistException();
        }
        team.setHomeStadium(newStadium);
        return true;
    }
    public boolean addNewTeamManager (TeamManager newTeamManager) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (newTeamManager==null){
            throw new IllegalValueException();
        }
        return team.addTeamManager(newTeamManager);
    }

    /**
     *
     * sub functions for UC 6.1 - removeAssert
     */
    public boolean removePlayer (Player player) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (player==null){
            throw new IllegalValueException();
        }
        return team.removePlayer(player);
    }
    public boolean removeCoach (Coach coach) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (coach==null){
            throw new IllegalValueException();
        }
        if (!team.getCoach().equals(coach)) {
            throw new AssertIsNotExistException("coach is not exist");
        }
        team.setCoach(null);
        return true;
    }
    public boolean removeStadium (Stadium stadium) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (stadium==null){
            throw new IllegalValueException();
        }
        if (!team.getHomeStadium().equals(stadium)) {
            throw new AssertIsNotExistException("stadium is not exist");
        }
        team.setHomeStadium(null);
        return true;
    }
    public boolean removeTeamManager (TeamManager teamManager) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (teamManager==null){
            throw new IllegalValueException("team manger is not exist");
        }
        return team.removeTeamManager(teamManager);
    }
    /**
     *
     * sub function for UC 6.1 - editAssert
     */
    public boolean editPlayer(Player player, String name, Date birthday, PlayerType playerType) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException, AssertAlreadyExistException {
        if (player==null || name==null || birthday==null || playerType==null){
            throw new IllegalValueException();
        }
        if (!team.getPlayers().contains(player)){
            throw new AssertIsNotExistException("player is not exist");
        }

        team.removePlayer(player);
        player.setName(name);
        player.setBirthday(birthday);
        player.setPlayerType(playerType);
        return team.addNewPlayer(player);


    }
    public boolean editCoach(Coach coach, String name, String qualification) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (coach==null || name==null || qualification==null){
            throw new IllegalValueException();
        }
        if (!team.getCoach().equals(coach)){
            throw new AssertIsNotExistException("coach is not exist");
        }
        coach.setName(name);
        coach.setQualification(qualification);
        team.setCoach(coach);

        return true;
    }
    public boolean editStadium(Stadium stadium, String stadiumName) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (stadium==null || stadiumName==null ){
            throw new IllegalValueException();
        }
        if (!team.getHomeStadium().equals(stadium)){
            throw new AssertIsNotExistException("stadium is not exist");
        }
        stadium.setStadiumName(stadiumName);

        return true;
    }
    public boolean editTeamManager(TeamManager teamManager, String name) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException, AssertAlreadyExistException {
        if (teamManager==null || name==null ){
            throw new IllegalValueException();
        }
        if (!team.getTeamManagers().contains(teamManager)){
            throw new AssertIsNotExistException("team manager is not exist");
        }

        team.removeTeamManager(teamManager);
        teamManager.setName(name);

        return team.addTeamManager(teamManager);
    }

}
