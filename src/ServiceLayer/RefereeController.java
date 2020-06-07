package ServiceLayer;

import BuisinessLayer.LogicalOperations.Event;
import BuisinessLayer.LogicalOperations.Match;
import BuisinessLayer.LogicalOperations.Referee;
import Exceptions.IllegalValueException;

import java.util.List;

public class RefereeController extends ProfileEventMakerController {

    public RefereeController() {
    }

    public List<Match> viewAssignGames(Referee referee) throws Exception {
        if (referee == null)
            throw new IllegalValueException();
        return referee.viewMyMatches();
    }

    public boolean addEventToMatch(Referee referee, Event event, Match match) throws Exception {
        if (referee == null || event == null || match == null)
            throw new IllegalValueException();
        if (referee.addEventToAMatch(event,match))
            return true;
        return false;
    }
    public boolean updateEventToMatch(Referee referee, Event old, Event updated, Match match) throws Exception {
        if (referee == null || old == null || updated == null || match == null)
            throw new IllegalValueException();
        if (referee.updateEventMatch(old, updated, match))
            return true;
        return false;
    }
}
