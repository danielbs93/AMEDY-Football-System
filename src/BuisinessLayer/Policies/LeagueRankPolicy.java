package BuisinessLayer.Policies;

import BuisinessLayer.LogicalOperations.Team;

public interface LeagueRankPolicy {

    Double calcRank(Team team);
    boolean equals(LeagueRankPolicy leagueRankPolicy);
    String getName();

}
