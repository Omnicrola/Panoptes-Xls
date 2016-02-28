package com.omnicrola.panoptes.data;


import com.omnicrola.panoptes.export.TimesheetDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 11/5/2015.
 */
public class ExportDataContainer {

    @Required
    public List<TimeData> timeblocks = new ArrayList<>();
    @Required
    public List<WorkStatement> workStatements = new ArrayList<>();
    @Required
    public PersonalData personalData;
    @Required
    public TimesheetDate weekEnding;

}
