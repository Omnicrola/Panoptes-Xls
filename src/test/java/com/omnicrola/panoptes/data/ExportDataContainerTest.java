package com.omnicrola.panoptes.data;

import org.junit.Test;

import java.util.ArrayList;

import static com.omnicrola.test.util.TestUtil.assertRequiredFieldIsPresent;
import static org.junit.Assert.assertEquals;

/**
 * Created by omnic on 11/5/2015.
 */
public class ExportDataContainerTest {

    @Test
    public void testHasTimeData() throws Exception {
        assertRequiredFieldIsPresent("timeblocks", ExportDataContainer.class);
        assertRequiredFieldIsPresent("workStatements", ExportDataContainer.class);
    }

    @Test
    public void testDefaults() throws Exception {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        assertEquals(new ArrayList<TimeData>(), exportDataContainer.timeblocks);
        assertEquals(new ArrayList<WorkStatement>(), exportDataContainer.workStatements);
        exportDataContainer.timeblocks = new ArrayList<TimeData>();
        exportDataContainer.workStatements = new ArrayList<WorkStatement>();
    }




}