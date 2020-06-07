package BuisinessLayer.Policies;

import BuisinessLayer.LogicalOperations.Team;
import javafx.util.Pair;

import java.util.LinkedList;

public class MatchAssignmentPolicyStub implements MatchAssignmentPolicy{

    //Fields
    String name;

    public MatchAssignmentPolicyStub(String name) {
        this.name = name;
    }

    @Override
    public LinkedList<Pair<Team, Team>> assginMatches(LinkedList<Team> allTeams) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(MatchAssignmentPolicy matchAssignmentPolicy) {
        return this.name.equals(matchAssignmentPolicy.getName());
    }
}
