package BuisinessLayer.Policies;

import BuisinessLayer.LogicalOperations.Team;
import javafx.util.Pair;
import java.util.LinkedList;

public interface MatchAssignmentPolicy {

     LinkedList<Pair<Team, Team>> assginMatches(LinkedList<Team> allTeams);
     String getName();
     boolean equals(MatchAssignmentPolicy matchAssignmentPolicy);

}
