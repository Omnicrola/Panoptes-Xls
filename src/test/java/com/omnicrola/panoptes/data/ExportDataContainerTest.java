package com.omnicrola.panoptes.data;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.lang.reflect.Field;
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
        assertEquals(null, exportDataContainer.timeblocks);
        assertEquals(null, exportDataContainer.workStatements);
        exportDataContainer.timeblocks = new ArrayList<TimeData>();
        exportDataContainer.workStatements = new ArrayList<WorkStatement>();
    }




}