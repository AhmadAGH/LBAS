package tu.hw.lbas;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import tu.hw.lbas.UserManager.User;

public class Event
{
    int eventID;
    User user;
    String eventName;
    double lat,lng,rad;
    Time startTime,endTime;
    ArrayList<String> eventDays;
    public Event(int eventID, User user, String eventName, double lat, double lng,double rad, Time startTime, Time endTime, ArrayList<String> eventDays) {
        this.eventID = eventID;
        this.user = user;
        this.eventName = eventName;
        this.lat = lat;
        this.lng = lng;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDays = eventDays;
        this.rad = rad;
    }

    public Event(User user, String eventName, double lat, double lng, double rad, Time startTime, Time endTime, ArrayList<String> eventDays) {
        this.user = user;
        this.eventName = eventName;
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDays = eventDays;
    }

    public Event(int eventID, User user, String eventName, double lat, double lng, double rad, Time startTime, Time endTime) {
        this.eventID = eventID;
        this.user = user;
        this.eventName = eventName;
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ArrayList<String> getEventDays() {
        return eventDays;
    }

    public void setEventDays(ArrayList<String> eventDays) {
        this.eventDays = eventDays;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) { this.endTime = endTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", user=" + user +
                ", eventName='" + eventName + '\'' +
                ", lat=" + lat +
                ", Lng=" + lng +
                ", startTime=" + startTime +
                ", EndTime=" + endTime +
                ", eventDays=" + eventDays +
                '}';
    }
}
