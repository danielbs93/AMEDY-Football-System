package BuisinessLayer.LogicalOperations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {

    private static AtomicInteger ID_Counter = new AtomicInteger(0);

    //Fields
    private Date date;
    private LocalTime time;//start time
    private Score score;
    private int extensionOfMatchTime;
    private List<Fan> observersList;
    private int matchID;
    //Connections
    private Stadium stadium;
    private Team homeTeam;
    private Team awayTeam;
    private Referee[] referees;
    private EventDiary eventDiary;

    public Match(Date date, LocalTime time, Stadium stadium, Team homeTeam, Team awayTeam, Referee[] referees) throws Exception {
        this.date = date;
        this.time = time;
        this.score = new Score();
        if (stadium == null) {
            this.stadium = homeTeam.getHomeStadium();
        }
        else
            this.stadium = stadium;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referees = referees; //TODO:check if 4
        for (Referee r: this.referees) {
            r.addMatch(this);
        }
        this.eventDiary = new EventDiary(date,this);
        this.extensionOfMatchTime = 0;
        this.observersList = new ArrayList<>();
        this.matchID = ID_Counter.get();
        ID_Counter.incrementAndGet();
    }

    public Match() {
    }

    //-----Getters & Setters-----//
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Referee[] getReferees() {
        return referees.clone();
    }

    public void setReferees(Referee[] referees) throws Exception {
        this.referees = referees;
        for (Referee r: referees) {
            r.addMatch(this);
        }
    }

    public EventDiary getEventDiary() {
        return eventDiary;
    }

    public void setEventDiary(EventDiary eventDiary) {
        this.eventDiary = eventDiary;
    }

    public int getExtensionOfMatchTime() {
        return extensionOfMatchTime;
    }

    public void setExtensionOfMatchTime(int extensionOfMatchTime) {
        this.extensionOfMatchTime = extensionOfMatchTime;
    }

    public int getMatchID() {
        return this.matchID;
    }

    /**
     *
     * @param event
     * @return true if successfully event added to the event diary of this match
     * @throws Exception if the event is already assigned to this match
     */
    public boolean addEventToDiaryEvent(Event event) throws Exception{
        if (event != null) {
            if (this.eventDiary.addEvent(event)) {
                if (event.getEventType().equals(EventType.Extension))
                    this.extensionOfMatchTime = Integer.parseInt(event.getDescription());
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param toBeRemoved
     * @return true if the desired event was removed
     */
    public boolean removeEventFromMatch(Event toBeRemoved) {
        if (toBeRemoved != null && this.eventDiary.removeEvent(toBeRemoved)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param m
     * @return true only if all the boolean predicates are true
     */
    public boolean equals(Match m) {
        if (m != null) {
            if (m.date.equals(this.date) && m.time.equals(this.time)
                && m.homeTeam.equals(this.homeTeam) && m.awayTeam.equals(this.awayTeam))
                return true;
        }
        return false;
    }

    /**
     * add a new Fan Observer to the observers list.
     * @param fanObserver - an observer to be added.
     * @return true - if succeeded adding the new observer. else - return false.
     */
    public boolean addFanObserver(Fan fanObserver)
    {
        if (!this.observersList.contains(fanObserver)) {
            if(this.observersList.add(fanObserver))
            {
                return true;
            }
        }
        return false;
    }

    /**
     *the method checks if the fan exist as an Observer in the Observers List.
     * @param fanObserver - the current fan to check if exist in the Observers List.
     * @return true - if the fan exist in the list. else - return false.
     */
    public boolean isFanObserverExist(Fan fanObserver)
    {
        if(this.observersList.contains(fanObserver))
        {
            return true;
        }
        return false;
    }
}
