package com.omnicrola.panoptes.data;

import java.util.ArrayList;

/**
 * Created by omnic on 11/5/2015.
 */
public class ExportDataContainer {

    @Required
    public ArrayList<TimeData> timeblocks;
    @Required
    public ArrayList<WorkStatement> workStatements;
}
