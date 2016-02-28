package com.omnicrola.panoptes.export;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimesheetDate {

    private final static SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final Date date;

    private TimesheetDate() {
        this.date = new Date();
    }

    public TimesheetDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return (Date) this.date.clone();
    }

    @Override
    public String toString() {
        return SHORT_DATE_FORMAT.format(this.date);
    }

}
