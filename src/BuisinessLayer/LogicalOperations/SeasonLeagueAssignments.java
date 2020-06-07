package BuisinessLayer.LogicalOperations;

import BuisinessLayer.Policies.MatchAssignmentPolicy;

import java.util.ArrayList;

public class SeasonLeagueAssignments {
    //Connections
    protected League league;
    protected Season season;
    private MatchAssignmentPolicy myMatchAssignment;
    private ArrayList<Referee> seasonReferees; // allocated by the RFA

    public SeasonLeagueAssignments(League league, Season season) {
        this.league = league;
        this.season = season;
        //Generate connections
        this.league.putNewSeasonAssignment(season,this);
        this.season.putNewLeagueAssignment(league,this);
        this.seasonReferees = new ArrayList<>();
    }

    public League getLeague() {
        return league;
    }

    public Season getSeason() {
        return season;
    }

    public MatchAssignmentPolicy getMyMatchAssignment() {
        return myMatchAssignment;
    }

    public void setMyMatchAssignment(MatchAssignmentPolicy myMatchAssignment) {
        this.myMatchAssignment = myMatchAssignment;
    }

    public Referee getReferee(Referee referee) {
        if (referee != null && isRefereeAssigned(referee))
            return this.seasonReferees.get(this.seasonReferees.indexOf(referee));
        return null;
    }

    public Referee getRefereeByName(String refereeName) {
        if (refereeName != null && !refereeName.isEmpty()) {
            for (Referee r: this.seasonReferees) {
                if (r.getName().equals(refereeName))
                    return r;
            }
        }
        return null;
    }

    public boolean isRefereeAssigned(Referee referee) {
        if (referee != null && this.seasonReferees.contains(referee))
            return true;
        return false;
    }

    public boolean assignRefereeToSeason(Referee referee) {
        if (referee != null && !isRefereeAssigned(referee)) {
            this.seasonReferees.add(referee);
            return true;
        }
        return false;
    }

    public boolean equals(SeasonLeagueAssignments other) {
        if (other != null && other.league.equals(this.league) && other.season.equals(this.season))
            return true;
        return false;
    }
}
