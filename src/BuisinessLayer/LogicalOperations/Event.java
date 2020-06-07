package BuisinessLayer.LogicalOperations;

import Exceptions.IllegalValueException;

import java.time.LocalTime;
import java.util.Date;

public class Event {
    private Date date;
    private LocalTime time;
    private int gameMinute;
    private EventType eventType;
    private Player playerEventMaker;
    private String description;

    public Event(Date date, LocalTime time, int gameMinute, EventType eventType, Player player, String details) {
        this.date = date;
        this.time = time;
        this.gameMinute = gameMinute;
        this.eventType = eventType;
        this.playerEventMaker = player;
        this.description = details;
    }

    //-----Getters & Setters-----//


    public Date getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getGameMinute() {
        return gameMinute;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Player getPlayerEventMaker() throws Exception{
        if (this.playerEventMaker == null)
            throw new IllegalValueException("This match event has not occurred by any player");
        return playerEventMaker;
    }

    public String getDescription() {
        return description;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setGameMinute(int gameMinute) {
        this.gameMinute = gameMinute;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setPlayerEventMaker(Player playerEventMaker) {
        this.playerEventMaker = playerEventMaker;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Event event) {
        if (event != null && this.date.equals(event.date) && this.time.equals(event.time)
            && this.eventType.equals(event.eventType) && this.gameMinute == event.gameMinute)
            return true;
        return false;
    }
}
