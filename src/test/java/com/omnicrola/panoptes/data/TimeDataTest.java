package com.omnicrola.panoptes.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by omnic on 10/31/2015.
 */
public class TimeDataTest extends ModelTest {
    @Test
    public void testConstructorValues() throws Exception {
        String role = "my role";
        String card = "my card";
        String project = "my project";
        TimeData timeData = new TimeData(project, card, role);

        assertEquals(role, timeData.getRole());
        assertEquals(project, timeData.getProject());
        assertEquals(card, timeData.getCard());
    }

    @Test
    public void testHasCorrectFields() throws Exception {
        setClassUnderTest(TimeData.class);

        checkFieldIsPresent("project", String.class);
        checkRequiredFieldIsPresent("card", String.class);
        checkRequiredFieldIsPresent("role", String.class);
    }

}