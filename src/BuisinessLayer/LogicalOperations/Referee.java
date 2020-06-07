package BuisinessLayer.LogicalOperations;

import Exceptions.*;
import Exceptions.ListIsEmptyException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Referee extends ProfileEventMaker
{
    private RefereeType type;
    private ArrayList<Match> myMatches;
//    public stubRedreeTest dataBase;

    public Referee(String name, RefereeType refereeType) {
        super(name,"Referee");
        this.type = refereeType;
        myMatches = new ArrayList<>();
    }

    //-----Getters-----//
    public RefereeType getRefereeType() {
        return this.type;
    }

    public ArrayList<Match> getMyMatches() {
        return myMatches;
    }

    /**
     *
     * @return list of referee's matches
     * @throws Exception list is empty
     */
    public ArrayList<Match> viewMyMatches() throws Exception {
        if (this.myMatches.size() == 0)
            throw new ListIsEmptyException("You don't have any match assignments yet");
        return myMatches;
    }

    /**
     *
     * @param match
     * @throws Exception if a null parameter was sent
     * @throws Exception if the match you want to add is already exist
     */
    public void addMatch(Match match) throws Exception {
        if (match == null)
            throw new IllegalValueException();
        Match found = isExistAndReturn(match);
        if (found != null)
            throw new AssertAlreadyExistException("Match is already exist in " + this.name + " matches list");
        this.myMatches.add(match);
    }

    /**
     *
     * @param event
     * @param match
     * @return true if the event were added to the match that is happening now
     * @throws Exception according to checkPermissions method
     */
    public boolean addEventToAMatch(Event event, Match match) throws Exception {
        if (event == null || match == null)
            throw new IllegalValueException();
        Match found = isExistAndReturn(match);
        if (checkPermissions(found)) {
            if (found.addEventToDiaryEvent(event))
                return true;
        }
        return false;
    }

    /**
     *
     * @param oldEvent getting by extracting it from the diaryEvent
     * @param newEvent creating one based on the old event with the desired fields
     * @param match
     * @return true only if the oldEvent has been successfully removed and added the newEvent
     */
    public boolean updateEventMatch(Event oldEvent, Event newEvent, Match match) throws Exception {
        if (oldEvent == null || newEvent == null || match == null)
            throw new IllegalValueException();
        Match found = isExistAndReturn(match);
        if (checkPermissions(found)) {
            if (found.getEventDiary().isEventExist(newEvent))
                throw new AssertAlreadyExistException("The updated event is already exist");
            if (found.removeEventFromMatch(oldEvent) && found.addEventToDiaryEvent(newEvent))
                return true;
        }
        return false;
    }

    /**
     *
     * @param found
     * @return true only if all permissions are correct
     * @throws AssertIsNotExistException if match does not exist
     * @throws PermissionDeniedException if the match is not occurring now and the referee is type of SideReferee
     * @throws PermissionDeniedException if the match is not occurring now and 5 hours have passed since game over and the referee type is MainReferee
     */
    private boolean checkPermissions(Match found) throws Exception{
        if (found == null)
            throw new AssertIsNotExistException("Match does not exist in your matches list");
        if (!isMatchOccurringNow(found)) {
            if (!this.type.equals(RefereeType.MainReferee)) {
                throw new PermissionDeniedException(this.name + ", you don't have the permissions to add events after the match has ended");
            }
            else {
                if (is5HoursPassedSinceMatchEnded(found)) {
                    throw new PermissionDeniedException(this.name + ", you can't insert new event since 5 hours have passed from the end of the match");
                }
            }
        }
        return true;
    }

    /**
     *
     * @param found
     * @return true if 5 hours have passed since the match over
     */
    private boolean is5HoursPassedSinceMatchEnded(Match found) {
        if (found != null) {
            //adding to local time of match (match start time) 90 min of game and the extension for game
            long hourPassedSinceMatchOver = Duration.between(found.getTime().plusMinutes(90+found.getExtensionOfMatchTime()), LocalTime.now()).toMinutes();
            if (hourPassedSinceMatchOver  < 300)
                return true;
        }
        return false;
    }

    /**
     *
     * @param match
     * @return true if the match's date is today and if it is occurring now (with extension)
     */
    private boolean isMatchOccurringNow(Match match) {
        Date now = new Date(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth().getValue(),
                LocalDateTime.now().getDayOfMonth()
        );
        if (now.equals(match.getDate()) && match.getTime().isBefore(LocalTime.now())){
           long passedMinutesSinceStart = Duration.between(match.getTime(),LocalTime.now()).toMinutes();
           if (passedMinutesSinceStart < (90 + match.getExtensionOfMatchTime())) {
               return true;
           }
        }
        return false;
    }

    /**
     *
     * @param match
     * @return the correct match if exist in the referee's matches list or null
     */
    public Match isExistAndReturn(Match match) {
        for (Match m: this.myMatches) {
            if (m.equals(match))
                return m;
        }
        return null;
    }

    /**
     * the method uses UseCase 3.6.1 - ViewMyPersonalInfo.
     * the method gets all the personal info of the entity.
     */
    @Override
    public List<Pair> getPersonalInfo() {
        this.allPersonalInfo = new ArrayList<>();
        this.allPersonalInfo.add(new Pair("Type", "Referee"));
        this.allPersonalInfo.add(new Pair("name", this.name));
        this.allPersonalInfo.add(new Pair("refereeType", this.getRefereeType().name()));
        return this.allPersonalInfo;
    }
}

