package BuisinessLayer.LogicalOperations;

import Exceptions.AssertAlreadyExistException;

import java.util.ArrayList;
import java.util.Date;

public class EventDiary {

    //Fields
    private Date date;

    //Connections
    private Match match;
    private ArrayList<Event> events;

    public EventDiary(Date date, Match match) {
        this.date = date;
        this.match = match;
        this.events = new ArrayList<>();
    }

    //-----Getters-----//

    public Date getDate() {
        return date;
    }

    public Match getMatch() {
        return match;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     *
     * @param event
     * @return true if successfully added to event diary
     * @throws Exception if the event is already exist
     */
    public boolean addEvent(Event event) throws Exception{
        if (event != null) {
            if (isEventExist(event))
                throw new AssertAlreadyExistException("Event already exist");
            this.events.add(event);
            return true;
        }
        return false;
    }

    /**
     *
     * @param event
     * @return true if the exact event is already in the event diary
     */
    public boolean isEventExist(Event event) {
        for (Event e: this.events) {
            if (e.equals(event))
                return true;
        }
        return false;
    }

    /**
     *
     * @param toBeRemoved
     * @return true if successfully event removed from the list
     */
    public boolean removeEvent(Event toBeRemoved) {
        if (this.events.contains(toBeRemoved)) {
            this.events.remove(toBeRemoved);
            return true;
        }
        return false;
    }
}
