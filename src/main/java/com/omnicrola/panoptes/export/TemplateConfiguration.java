package com.omnicrola.panoptes.export;

/**
 * Created by omnic on 11/8/2015.
 */
public class TemplateConfiguration {
    public static final TemplateConfiguration INSTANCE = new TemplateConfiguration();

    private TemplateConfiguration(){}

    public int getTimesheetRowInsertPosition() {
        return 8;
    }

    public int getIndexOfTimesheetSheet() {
        return 0;
    }

    public int getProjectSumColumn() {
        return 16;
    }

    public int getIndexOfTotalsRow() {
        return 9;
    }
}
