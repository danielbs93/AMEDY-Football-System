package BuisinessLayer.LogicalOperations;

import BuisinessLayer.Policies.LeagueRankPolicy;
import BuisinessLayer.Policies.MatchAssignmentPolicy;

import java.util.ArrayList;
import java.util.HashMap;

public class FootballAssociation {

    //Fields
    private String Name;

    //Connections
    private ArrayList<RepresentativeFootballAssociation> AllRFA;
    private ArrayList<LeagueRankPolicy> LeagueRankPolicies;
    private ArrayList<MatchAssignmentPolicy> MatchAssignmentPolicies;
    private HashMap<LeagueRank,League> AllLeagues;
    private ArrayList<Referee> allReferees;

    public FootballAssociation(String name) {
        Name = name;
        this.AllLeagues = new HashMap<>();
        this.AllRFA = new ArrayList<>();
        this.MatchAssignmentPolicies = new ArrayList<>();
        this.LeagueRankPolicies = new ArrayList<>();
        this.allReferees = new ArrayList<>();
    }


    //-----Getters & Setters-----//

    public ArrayList<Referee> getAllReferees() {
        return allReferees;
    }

    public void setAllReferees(ArrayList<Referee> allReferees) {
        this.allReferees = allReferees;
    }

    public League getLeague(LeagueRank leagueRank) {
        if (leagueRank != null && isLeagueExist(leagueRank)){
            return AllLeagues.get(leagueRank);
        }
        return null;
    }

    public Referee getRefereeByName(String name) {
        for (Referee r: this.allReferees) {
            if (r.getName().equals(name))
                return r;
        }
        return null;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<RepresentativeFootballAssociation> getAllRFA() {
        return AllRFA;
    }

    public ArrayList<LeagueRankPolicy> getLeagueRankPolicies() {
        return LeagueRankPolicies;
    }

    public ArrayList<MatchAssignmentPolicy> getMatchAssignmentPolicies() {
        return MatchAssignmentPolicies;
    }

    public ArrayList<League> getAllLeagues() {
        return new ArrayList<>(AllLeagues.values());
    }

    //----Adding----//

    public boolean addToLeagues(League league) {
        if (league != null) {
            this.AllLeagues.put(league.getLeagueRank(),league);
            return true;
        }
        return false;
    }
    public boolean addToRFA(RepresentativeFootballAssociation representativeFootballAssociation) {
        if (representativeFootballAssociation != null) {
            this.AllRFA.add(representativeFootballAssociation);
            return true;
        }
        return false;
    }
    public boolean addMatchAssignmentPolicy(MatchAssignmentPolicy matchAssignmentPolicy) {
        if (matchAssignmentPolicy != null) {
            this.MatchAssignmentPolicies.add(matchAssignmentPolicy);
            return true;
        }
        return false;
    }
    public boolean addLeagueRankPolicy(LeagueRankPolicy leagueRankPolicy) {
        if (leagueRankPolicy != null) {
            this.LeagueRankPolicies.add(leagueRankPolicy);
            return true;
        }
        return false;
    }

    /**
     * updating the old policy to the new one and each league that has this policy will be updated too
     * @param oldPolicy
     * @param newPolicy
     * @return
     */
    public boolean updateLeagueRankPolicy(LeagueRankPolicy oldPolicy, LeagueRankPolicy newPolicy) {
        for (League l: this.AllLeagues.values()) {
            if (l.getLeagueRankPolicy().equals(oldPolicy))
                l.setLeagueRankPolicy(newPolicy);
        }

        this.LeagueRankPolicies.remove(oldPolicy);
        this.LeagueRankPolicies.add(newPolicy);
        return true;
    }

    //-----Existing-----//

    public boolean isLeagueExist(LeagueRank leagueRank) {
        if (leagueRank != null && AllLeagues.containsKey(leagueRank)) {
            return true;
        }
        return false;
    }

    public boolean isLeagueRankPolicyExist(LeagueRankPolicy leagueRankPolicy) {
        if (leagueRankPolicy != null && this.LeagueRankPolicies.contains(leagueRankPolicy))
            return true;
        return false;
    }

    public LeagueRankPolicy isLeagueRankPolicyExistAndGet(String policyName) {
        if (policyName == null || policyName.isEmpty())
            return null;
        for (LeagueRankPolicy lrp: this.LeagueRankPolicies) {
            if (lrp.getName().equals(policyName))
                return lrp;
        }
        return null;
    }

    public boolean isMatchAssignmentPolicyExist(MatchAssignmentPolicy matchAssignmentPolicy) {
        if (matchAssignmentPolicy != null && this.MatchAssignmentPolicies.contains(matchAssignmentPolicy))
            return true;
        return false;
    }

    public MatchAssignmentPolicy getMatchAssignmentPolicy(String policyName) {
        if (policyName == null || policyName.isEmpty())
            return null;
        for (MatchAssignmentPolicy map: this.MatchAssignmentPolicies) {
            if (map.getName().equals(policyName))
                return map;
        }
        return null;
    }

    public boolean setMatchAssignmentPolicyToAllSeasonLeagues(League league, MatchAssignmentPolicy matchAssignmentPolicy) {
        if (league != null && matchAssignmentPolicy != null) {
            League l = getLeague(league.getLeagueRank());
            l.setMatchAssignmentPolicyToAllSeasonLeagues(matchAssignmentPolicy);
            return true;
        }
        return false;
    }
}
