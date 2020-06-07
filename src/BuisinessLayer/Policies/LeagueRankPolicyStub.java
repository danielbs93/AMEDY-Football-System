package BuisinessLayer.Policies;

import BuisinessLayer.LogicalOperations.Team;

public class LeagueRankPolicyStub implements LeagueRankPolicy {

    //Fields
    private String name;

    public LeagueRankPolicyStub(String name) {
        this.name = name;
    }

    @Override
    public Double calcRank(Team team) {
        return new Double(0);
    }

    @Override
    public boolean equals(LeagueRankPolicy leagueRankPolicy) {
        return this.name.equals(leagueRankPolicy.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            return true;
        }
        return false;
    }
}
