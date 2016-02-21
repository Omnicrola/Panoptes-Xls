package com.omnicrola.panoptes.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeData {

    @Required
    String project;
    @Required
    String card;
    @Required
    String role;
    @Required
    String startTime;
    @Required
    String endTime;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public TimeData() {
    }

    public String getCard() {
        return this.card;
    }

    public String getProject() {
        return this.project;
    }

    public String getRole() {
        return this.role;
    }

    public Date getStartTime() {
        return convertDate(this.startTime);
    }

    public Date getEndTime() {
        return convertDate(this.endTime);
    }

    private Date convertDate(String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }


    public float getElapsedTimeInHours() {
        float elapsedSeconds = (getEndTime().getTime() - getStartTime().getTime()) / 1000f;
        float elapsedHours = elapsedSeconds / 60f / 60f;
        return elapsedHours;
    }

    public int getDayOfWeek() {
        return getStartTime().getDay();
    }
}
