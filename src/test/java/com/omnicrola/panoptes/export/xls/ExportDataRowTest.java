package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.WorkStatement;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by omnic on 10/31/2015.
 */
public class ExportDataRowTest {
    @Test
    public void testConstructorArgs() throws Exception {

        WorkStatement mockWorkStatement = mock(WorkStatement.class);

        String project = "project";
        String role = "role";
        String card = "card";
        boolean billToMenlo = true;
        boolean billToClient = true;

        TimesheetLineItem exportDataRow = new TimesheetLineItem(mockWorkStatement, project, role, card, billToMenlo, billToClient);

        assertEquals(exportDataRow.getCard(), card);
        assertEquals(role, exportDataRow.getRole());
        assertEquals(billToClient, exportDataRow.isBillableToClient());
        assertEquals(billToMenlo, exportDataRow.isBillableToMenlo());
        assertSame(mockWorkStatement, exportDataRow.getWorkStatement());

        String expectedDescription = project + " " + role + " " + card;
        assertEquals(expectedDescription, exportDataRow.getDescription());
    }

    @Test
    public void testAddsTimeToCorrectDays() throws Exception {

        TimesheetLineItem exportDataRow = createExportDataRow();

        float day0 = randomFloat();
        float day1 = randomFloat();
        float day2 = randomFloat();
        float day3 = randomFloat();
        float day4 = randomFloat();
        float day5 = randomFloat();
        float day6 = randomFloat();

        float day0a = randomFloat();
        float day1a = randomFloat();
        float day2a = randomFloat();
        float day3a = randomFloat();
        float day4a = randomFloat();
        float day5a = randomFloat();
        float day6a = randomFloat();

        exportDataRow.addTime(0, day0);
        exportDataRow.addTime(1, day1);
        exportDataRow.addTime(2, day2);
        exportDataRow.addTime(3, day3);
        exportDataRow.addTime(4, day4);
        exportDataRow.addTime(5, day5);
        exportDataRow.addTime(6, day6);

        assertEquals(day0, exportDataRow.getTimeForDay(0), 0);
        assertEquals(day1, exportDataRow.getTimeForDay(1), 0);
        assertEquals(day2, exportDataRow.getTimeForDay(2), 0);
        assertEquals(day3, exportDataRow.getTimeForDay(3), 0);
        assertEquals(day4, exportDataRow.getTimeForDay(4), 0);
        assertEquals(day5, exportDataRow.getTimeForDay(5), 0);
        assertEquals(day6, exportDataRow.getTimeForDay(6), 0);

        exportDataRow.addTime(0, day0a);
        exportDataRow.addTime(1, day1a);
        exportDataRow.addTime(2, day2a);
        exportDataRow.addTime(3, day3a);
        exportDataRow.addTime(4, day4a);
        exportDataRow.addTime(5, day5a);
        exportDataRow.addTime(6, day6a);


        assertEquals(day0 + day0a, exportDataRow.getTimeForDay(0), 0);
        assertEquals(day1 + day1a, exportDataRow.getTimeForDay(1), 0);
        assertEquals(day2 + day2a, exportDataRow.getTimeForDay(2), 0);
        assertEquals(day3 + day3a, exportDataRow.getTimeForDay(3), 0);
        assertEquals(day4 + day4a, exportDataRow.getTimeForDay(4), 0);
        assertEquals(day5 + day5a, exportDataRow.getTimeForDay(5), 0);
        assertEquals(day6 + day6a, exportDataRow.getTimeForDay(6), 0);
    }
    @Test
    public void testCalculatesTotalTime() throws Exception {

        TimesheetLineItem exportDataRow = createExportDataRow();

        float day0 = randomFloat();
        float day1 = randomFloat();
        float day2 = randomFloat();
        float day3 = randomFloat();
        float day4 = randomFloat();
        float day5 = randomFloat();
        float day6 = randomFloat();

        exportDataRow.addTime(0, day0);
        exportDataRow.addTime(1, day1);
        exportDataRow.addTime(2, day2);
        exportDataRow.addTime(3, day3);
        exportDataRow.addTime(4, day4);
        exportDataRow.addTime(5, day5);
        exportDataRow.addTime(6, day6);

        float expectedTime = day0+day1+day2+day3+day4+day5+day6;
        assertEquals(expectedTime, exportDataRow.getTotalTime(), 0);
    }

    private float randomFloat() {
        return (float) (Math.random() * 100);
    }

    private TimesheetLineItem createExportDataRow() {
        WorkStatement mockWorkStatement = mock(WorkStatement.class);
        return new TimesheetLineItem(mockWorkStatement, "project", "role", "card", true, true);
    }
}