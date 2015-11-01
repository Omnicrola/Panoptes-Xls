package com.omnicrola.panoptes.data;

import com.omnicrola.panoptes.export.DateWrapper;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

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
        Date startTime = new Date(3938734l);
        Date endTime = new Date(79345344);
        TimeData timeData = new TimeData(project, card, role, startTime, endTime);

        assertEquals(role, timeData.getRole());
        assertEquals(project, timeData.getProject());
        assertEquals(card, timeData.getCard());
        assertEquals(startTime, timeData.getStartTime());
        assertEquals(endTime, timeData.getEndTime());

    }

    @Test
    public void testGetDayOfWeek_Friday() throws Exception {
        Date startTime = new GregorianCalendar(2015, 9, 28, 13, 27, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 9, 31, 13, 54, 0).getTime();
        System.out.println(startTime);

        TimeData timeData = new TimeData("project", "card", "role", startTime, endTime);
        assertEquals(3, timeData.getDayOfWeek(), 0);
    }

    @Test
    public void testGetDayOfWeek_Wednesday() throws Exception {
        Date startTime = new GregorianCalendar(2015, 9, 30, 13, 27, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 9, 31, 13, 54, 0).getTime();
        System.out.println(startTime);

        TimeData timeData = new TimeData("project", "card", "role", startTime, endTime);
        assertEquals(5, timeData.getDayOfWeek(), 0);
    }

    @Test
    public void testGetTimeElapsed_case1() throws Exception {
        Date startTime = new GregorianCalendar(2015, 10, 1, 13, 27, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 10, 1, 13, 54, 0).getTime();
        float expectedHoursElapsed = 0.45f;

        TimeData timeData = new TimeData("project", "card", "role", startTime, endTime);
        assertEquals(expectedHoursElapsed, timeData.getElapsedTimeInHours(), 0);
    }

    @Test
    public void testGetTimeElapsed_case2() throws Exception {
        Date startTime = new GregorianCalendar(2015, 10, 1, 13, 15, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 10, 1, 14, 45, 0).getTime();
        float expectedHoursElapsed = 1.5f;

        TimeData timeData = new TimeData("project", "card", "role", startTime, endTime);
        assertEquals(expectedHoursElapsed, timeData.getElapsedTimeInHours(), 0);

    }

    @Test
    public void testGetTimeElapsed_case3() throws Exception {
        Date startTime = new GregorianCalendar(2015, 10, 1, 8, 00, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 10, 1, 22, 15, 0).getTime();
        float expectedHoursElapsed = 14.25f;

        TimeData timeData = new TimeData("project", "card", "role", startTime, endTime);
        assertEquals(expectedHoursElapsed, timeData.getElapsedTimeInHours(), 0);

    }

    @Test
    public void testHasCorrectFields() throws Exception {
        setClassUnderTest(TimeData.class);

        checkFieldIsPresent("project", String.class);
        checkRequiredFieldIsPresent("card", String.class);
        checkRequiredFieldIsPresent("role", String.class);
        checkRequiredFieldIsPresent("startTime", Date.class);
        checkRequiredFieldIsPresent("endTime", Date.class);
    }

}