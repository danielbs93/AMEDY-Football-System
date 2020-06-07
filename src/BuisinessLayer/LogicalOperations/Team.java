package BuisinessLayer.LogicalOperations;

import Exceptions.*;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Team {

    //Fields
    private String teamName;
    private TeamStatus status;

    //Connections
    private List<Player> players;
    private Coach coach;
    private List<TeamOwner> teamOwners;
    private List<TeamManager> teamManagers;
    private List<Pair<Season,Budget>> seasonBudget;
    private League league;
    private Stadium homeStadium;
    private List<Match> homeMatches;
    private List<Match> awayMatches;
    private List<Pair<String,Double>> expenditureFinancialReports;
    private List<Pair<String,Double>> incomeFinancialReports;

    public Team(String teamName, List<Player> players, Coach coach, League league, Stadium homeStadium) {
        this.teamName = teamName;
        status = TeamStatus.ACTIVE;

        this.players = players;
        this.coach = coach;
        this.teamOwners = new LinkedList<>();
        this.teamManagers = new LinkedList<TeamManager>();
        this.seasonBudget = new LinkedList<>();
        this.league = league;
        this.homeStadium = homeStadium;
        this.homeMatches = new LinkedList<>();
        this.awayMatches = new LinkedList<>();
        this.expenditureFinancialReports = new LinkedList<>();
        this.incomeFinancialReports = new LinkedList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<TeamOwner> getTeamOwners() {
        return teamOwners;
    }

    public void setTeamOwners(List<TeamOwner> teamOwners) {
        this.teamOwners = teamOwners;
    }

    public List<TeamManager> getTeamManagers() {
        return teamManagers;
    }

    public void setTeamManagers(List<TeamManager> teamManagers) {
        this.teamManagers = teamManagers;
    }

    public List<Pair<Season, Budget>> getSeasonBudget() {
        return seasonBudget;
    }

    public void setSeasonBudget(List<Pair<Season, Budget>> seasonBudget) {
        this.seasonBudget = seasonBudget;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Stadium getHomeStadium() {
        return homeStadium;
    }

    public void setHomeStadium(Stadium homeStadium) {
        this.homeStadium = homeStadium;
    }

    public List<Match> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<Match> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public List<Match> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(List<Match> awayMatches) {
        this.awayMatches = awayMatches;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public boolean addExpenditureFinancialReport(String report, double amount) throws IllegalValueException, IllegalAmountException {
        if (report == null || report == "") {
            throw new IllegalValueException();
        }
        if (amount <= 0) {
            throw new IllegalAmountException("amount should be a positive value");
        }
        //TODO: calculate budget ???
        //TODO: check exceeded the budget ???

        return expenditureFinancialReports.add(new Pair<>(report,amount));
    }

    public boolean addIncomeFinancialReport(String report, double amount) throws IllegalValueException, IllegalAmountException {
        if(report==null) {
            throw new IllegalValueException();
        }
        if (amount <= 0) {
            throw new IllegalAmountException("amount should be a positive value");
        }
        //TODO: calculate budget ???
         return incomeFinancialReports.add(new Pair<>(report,amount));
    }
    public boolean addTeamOwner(TeamOwner teamOwner) throws AssertAlreadyExistException, IllegalValueException {
        if (teamOwner==null){
            throw new IllegalValueException();
        }
        if (getTeamOwners().contains(teamOwner)){
            throw new AssertAlreadyExistException();
        }
       return teamOwners.add(teamOwner);

    }
    public boolean addTeamManager(TeamManager teamManager) throws IllegalValueException, AssertAlreadyExistException {
        if (teamManager==null ){
            throw new IllegalValueException();
        }
        if (getTeamManagers().contains(teamManager)){
            throw new AssertAlreadyExistException();
        }
       return teamManagers.add(teamManager);
    }

    public boolean removeTeamOwner(TeamOwner teamOwner) throws IllegalValueException, AssertIsNotExistException {
        if (teamOwner==null){
            throw new IllegalValueException();
        }
        //team owner is not exist
        if (!getTeamOwners().contains(teamOwner)){
            throw new AssertIsNotExistException();
        }
       return teamOwners.remove(teamOwner);
    }
    public boolean removeTeamManager(TeamManager teamManager) throws IllegalValueException, AssertIsNotExistException {
        if (teamManager==null) {
            throw new IllegalValueException();
        }
         if (!getTeamManagers().contains(teamManager)){
             throw new AssertIsNotExistException();
        }
        return teamManagers.remove(teamManager);
    }
    public boolean addNewPlayer (Player newPlayer) throws IllegalValueException, AssertAlreadyExistException, PermissionDeniedException {
        if (newPlayer == null) {
            throw new IllegalValueException();
        }
        if (players.contains(newPlayer)) {
            throw new AssertAlreadyExistException("player already exist");
        }
        return players.add(newPlayer);
    }

    public boolean removePlayer (Player player) throws IllegalValueException, AssertIsNotExistException, PermissionDeniedException {
        if (player==null){
            throw new IllegalValueException();
        }
        if (!players.contains(player)) {
            throw new AssertIsNotExistException("player is not exist");
        }
        return players.remove(player);
    }

    public boolean equals(Team t) {
        if (t != null && t.teamName.equals(this.teamName))
            return true;
        return false;
    }
}
