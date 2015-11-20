package com.omnicrola.panoptes.data;

import com.omnicrola.panoptes.endpoints.TimeData;

import java.util.List;

/**
 * Created by omnic on 11/5/2015.
 */
public class ExportDataContainer {

    @Required
    public List<TimeData> timeblocks;
    @Required
    public List<WorkStatement> workStatements;
}
