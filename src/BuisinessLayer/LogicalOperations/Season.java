package BuisinessLayer.LogicalOperations;

import BuisinessLayer.Policies.LeagueRankPolicy;

import java.util.HashMap;

public class Season {

    //Fields
    private int year;

    //Connections
    private AMEDYSystem AMEDYSystem;
    private LeagueRankPolicy leagueRankPolicy;
    private HashMap<Team, Budget> teamBudget;
    private HashMap<League, SeasonLeagueAssignments> leagueSeason;

    public Season(int year, AMEDYSystem AMEDYSystem, LeagueRankPolicy leagueRankPolicy) {
        this.year = year;
        this.AMEDYSystem = AMEDYSystem;
        this.leagueRankPolicy = leagueRankPolicy;
        this.teamBudget = new HashMap<>();
        leagueSeason = new HashMap<>();
    }

    public Season(int year, BuisinessLayer.LogicalOperations.AMEDYSystem AMEDYSystem) {
        this.year = year;
        this.AMEDYSystem = AMEDYSystem;
    }

    public int getYear() {
        return year;
    }

    public void putNewLeagueAssignment(League league, SeasonLeagueAssignments seasonLeagueAssignments) {
        if (league != null && seasonLeagueAssignments != null)
            this.leagueSeason.put(league,seasonLeagueAssignments);
        if (!league.isSeasonExist(this))
            league.putNewSeasonAssignment(this,seasonLeagueAssignments);
    }

    public LeagueRankPolicy getLeagueRankPolicy(League league) {
        if (league != null) {
            for (League l: this.leagueSeason.keySet()) {
                if (l.equals(league))
                    return l.getLeagueRankPolicy();
            }
        }
        return null;
    }

    public boolean isRefereeAssigned(League league, Referee referee) {
        if (referee != null && this.leagueSeason.get(league).isRefereeAssigned(referee))
            return true;
        return false;
    }

    public boolean addRefereeToSeason(League league, Referee referee) {
        if (referee != null) {
            this.leagueSeason.get(league).assignRefereeToSeason(referee);
            return true;
        }
        return false;
    }

    public boolean isLeagueExist(League league) {
        if (league != null) {
            return this.leagueSeason.keySet().contains(league);
        }
        return false;
    }

    public SeasonLeagueAssignments getSeasonLeagueAssignments(League league) {
        if (league != null)
            return this.leagueSeason.get(league);
        return null;
    }

    public Referee getReferee(League league, Referee referee) {
        return this.leagueSeason.get(league).getReferee(referee);
    }

    public boolean equals(Season s) {
        return s.year == this.year;
    }

    //TODO: assigning matches after using MatchAssigningPolicy
}
