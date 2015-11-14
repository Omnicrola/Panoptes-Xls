package com.omnicrola.panoptes.export;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by omnic on 11/8/2015.
 */
public class TemplateConfigurationTest {
    @Test
    public void testValues() throws Exception {
        TemplateConfiguration config = TemplateConfiguration.INSTANCE;
        assertEquals(8, config.getTimesheetRowInsertPosition());
        assertEquals(0, config.getIndexOfTimesheetSheet());
        assertEquals(16, config.getProjectSumColumn());
        assertEquals(9, config.getIndexOfTotalsRow());
    }
}