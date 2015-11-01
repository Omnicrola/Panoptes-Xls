package com.omnicrola.panoptes.data;

import java.util.Date;

public class TimeData {

    @Required
    private String project;
    @Required
    private String card;
    @Required
    private String role;
    @Required
    private  Date startTime;
    @Required
    private  Date endTime;

    public TimeData(){
    }

    public TimeData(String project, String card, String role, Date startTime, Date endTime) {
        this.project = project;
        this.card = card;
        this.role = role;
        this.startTime = startTime;
        this.endTime = endTime;
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
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public float getElapsedTimeInHours() {
        float elapsedSeconds = (this.endTime.getTime() - this.startTime.getTime()) /1000f;
        float elapsedHours = elapsedSeconds / 60f / 60f;
        return elapsedHours;
    }

    public int getDayOfWeek() {
        return this.startTime.getDay();
    }
}
