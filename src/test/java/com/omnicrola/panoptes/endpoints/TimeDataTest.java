package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ModelTest;
import com.omnicrola.panoptes.endpoints.TimeData;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by omnic on 10/31/2015.
 */
public class TimeDataTest extends ModelTest {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    public void testConstructorValues() throws Exception {
        String role = "my role";
        String card = "my card";
        String project = "my project";
        TimeData timeData = createTimeData(project, card, role, new Date(), new Date());

        assertEquals(role, timeData.getRole());
        assertEquals(project, timeData.getProject());
        assertEquals(card, timeData.getCard());
    }


    @Test
    public void testConvertsDateStrings() throws Exception {
        TimeData timeData = new TimeData();
        timeData.startTime = "2015-11-11T13:01:02";
        timeData.endTime = "2015-10-10T14:02:03";

        Date expectedStartTime = new GregorianCalendar(2015, 10, 11, 13, 1, 2).getTime();
        Date expectedEndTime = new GregorianCalendar(2015, 9, 10, 14, 2, 3).getTime();

        assertEquals(expectedStartTime, timeData.getStartTime());
        assertEquals(expectedEndTime, timeData.getEndTime());
    }

    @Test
    public void testGetDayOfWeek_Friday() throws Exception {
        Date startTime = new GregorianCalendar(2015, 9, 28, 13, 27, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 9, 31, 13, 54, 0).getTime();

        TimeData timeData = createTimeData("project", "card", "role", startTime, endTime);
        assertEquals(3, timeData.getDayOfWeek(), 0);
    }

    @Test
    public void testGetDayOfWeek_Wednesday() throws Exception {
        Date startTime = new GregorianCalendar(2015, 9, 30, 13, 27, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 9, 31, 13, 54, 0).getTime();

        TimeData timeData = createTimeData("project", "card", "role", startTime, endTime);
        assertEquals(5, timeData.getDayOfWeek(), 0);
    }

    @Test
    public void testGetTimeElapsed_case1() throws Exception {
        Date startTime = new GregorianCalendar(2015, 10, 1, 13, 27, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 10, 1, 13, 54, 0).getTime();
        float expectedHoursElapsed = 0.45f;

        TimeData timeData = createTimeData("project", "card", "role", startTime, endTime);
        assertEquals(expectedHoursElapsed, timeData.getElapsedTimeInHours(), 0);
    }

    @Test
    public void testGetTimeElapsed_case2() throws Exception {
        Date startTime = new GregorianCalendar(2015, 10, 1, 13, 15, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 10, 1, 14, 45, 0).getTime();
        float expectedHoursElapsed = 1.5f;

        TimeData timeData = createTimeData("project", "card", "role", startTime, endTime);
        assertEquals(expectedHoursElapsed, timeData.getElapsedTimeInHours(), 0);
    }

    @Test
    public void testGetTimeElapsed_case3() throws Exception {
        Date startTime = new GregorianCalendar(2015, 10, 1, 8, 00, 0).getTime();
        Date endTime = new GregorianCalendar(2015, 10, 1, 22, 15, 0).getTime();
        float expectedHoursElapsed = 14.25f;

        TimeData timeData = createTimeData("project", "card", "role", startTime, endTime);
        assertEquals(expectedHoursElapsed, timeData.getElapsedTimeInHours(), 0);
    }

    @Test
    public void testHasCorrectFields() throws Exception {
        setClassUnderTest(TimeData.class);

        checkFieldIsPresent("project", String.class);
        checkRequiredFieldIsPresent("card", String.class);
        checkRequiredFieldIsPresent("role", String.class);
        checkRequiredFieldIsPresent("startTime", String.class);
        checkRequiredFieldIsPresent("endTime", String.class);
    }


    public static TimeData createTimeData(String project, String card, String role, Date startDate, Date endDate) {
        TimeData timeData = new TimeData();
        timeData.project = project;
        timeData.card = card;
        timeData.role = role;
        timeData.startTime = (startDate == null) ? "" : DATE_FORMAT.format(startDate);
        timeData.endTime = (endDate == null) ? "" : DATE_FORMAT.format(endDate);
        return timeData;
    }
}