package com.omnicrola.panoptes.data;

public class TimeData {

    private String project;
    private String card;
    private String role;

    public TimeData(){
    }

    public TimeData(String project, String card, String role) {
        this.project = project;
        this.card = card;
        this.role = role;
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
}
