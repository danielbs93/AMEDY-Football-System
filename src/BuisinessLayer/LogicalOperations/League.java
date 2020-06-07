package BuisinessLayer.LogicalOperations;

import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicy;
import javafx.util.Pair;

import java.util.*;

public class League {

    //Fields
    private LeagueRank leagueRank;
    private LeagueRankPolicy leagueRankPolicy;

    //Connections
    private HashMap<Season,SeasonLeagueAssignments> seasonLeague;
    private List<Team> teams;

    public League(LeagueRank leagueRank) {
        this.leagueRank = leagueRank;

        this.seasonLeague = new HashMap<>();
        this.teams = new LinkedList<>();
    }

    public LeagueRank getLeagueRank() {
        return leagueRank;
    }

    public LeagueRankPolicy getLeagueRankPolicy() {
        return leagueRankPolicy;
    }

    public Season getSeason(Season s) {
        if (s != null && this.seasonLeague.keySet().contains(s))
            return getSeasonByYear(s.getYear());
        return null;
    }

    public ArrayList<Season> getAllSeasons() {
        return new ArrayList<>(this.seasonLeague.keySet());
    }

    public Collection<SeasonLeagueAssignments> getAllSeasonLeagueAssignments() {return this.seasonLeague.values();}

    public Season getSeasonByYear(int year) {
        for (Season s: this.seasonLeague.keySet()) {
            if (s.getYear() == year)
                return s;
        }
        return null;
    }

    public void putNewSeasonAssignment(Season season, SeasonLeagueAssignments seasonLeagueAssignments) {
        if (season != null && seasonLeagueAssignments != null) {
            this.seasonLeague.put(season, seasonLeagueAssignments);
            if (!season.isLeagueExist(this))
                season.putNewLeagueAssignment(this,seasonLeagueAssignments);
        }
    }


    public boolean equals(League league) {
        if (league == null || !(league instanceof League))
            return super.equals(league);
        return league.getLeagueRank().toString().equals(this.leagueRank.toString());
    }

    public boolean isLeagueRankePolicyExist(LeagueRankPolicy leagueRankPolicy) {
        if (this.leagueRankPolicy != null && this.leagueRankPolicy.equals(leagueRankPolicy))
            return true;
        return false;
    }

    public boolean setLeagueRankPolicy(LeagueRankPolicy leagueRankPolicy) {
        if (leagueRankPolicy != null) {
            this.leagueRankPolicy = leagueRankPolicy;
            return true;
        }
        return false;
    }

    /**
     * This function calculating ranking score for each team that belongs to this league
     * @return sorted array of pair which the key is the score rank and the value is the team
     */
    public ArrayList<Pair<Double,Team>> getSortedRankTable() {
        ArrayList<Pair<Double,Team>> result = new ArrayList<>();
        for (Team t: this.teams) {
            Double tRank = this.leagueRankPolicy.calcRank(t);
            result.add(new Pair<>(tRank,t));
        }
        Comparator<Pair<Double,Team>> myRankComparator = Comparator.comparingDouble(Pair::getKey);
        Collections.sort(result,myRankComparator);
        return result;
    }

    public boolean setMatchAssignmentPolicyToAllSeasonLeagues( MatchAssignmentPolicy matchAssignmentPolicy) {
        for (SeasonLeagueAssignments sla: this.seasonLeague.values()) {
            sla.setMyMatchAssignment(matchAssignmentPolicy);
        }
        return true;
    }

    public boolean isSeasonExist(Season season) {
        if (season != null)
            return this.seasonLeague.keySet().contains(season);
        return false;
    }

    public SeasonLeagueAssignments getSeasonLeagueAssignment(Season season) {
        return this.seasonLeague.get(season);
    }
}

