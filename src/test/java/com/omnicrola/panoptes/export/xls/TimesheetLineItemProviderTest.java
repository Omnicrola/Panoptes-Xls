package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.WorkStatement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 10/31/2015.
 */
public class TimesheetLineItemProviderTest {

    @Test
    public void testBuildDataRows_NoData() throws Exception {
        ArrayList<TimeData> timeblocks = new ArrayList<>();

        TimesheetLineItemProvider exportModelBuilder = createExportModelBuilder();

        List<TimesheetLineItem> exportDataRows = exportModelBuilder.buildDataRows(timeblocks);
        assertEquals(0, exportDataRows.size());
    }


    @Test
    public void testBuildDataRows_OneResultRow() throws Exception {
        ArrayList<TimeData> timeblocks = new ArrayList<>();
        String project1 = "project 1";
        String card1 = "card 1";
        String role1 = "role DEV";
        float elapsedTime1 = randomFloat();
        float elapsedTime2 = randomFloat();
        float elapsedTime3 = randomFloat();
        float expectedTotalTime = elapsedTime1 + elapsedTime2 + elapsedTime3;

        timeblocks.add(createTime(project1, card1, role1, elapsedTime1, 0));
        timeblocks.add(createTime(project1, card1, role1, elapsedTime2, 0));
        timeblocks.add(createTime(project1, card1, role1, elapsedTime3, 0));

        TimesheetLineItemProvider exportModelBuilder = createExportModelBuilder();

        List<TimesheetLineItem> exportDataRows = exportModelBuilder.buildDataRows(timeblocks);
        assertEquals(1, exportDataRows.size());
        checkDataRow(project1, card1, role1, expectedTotalTime, exportDataRows.get(0));
    }

    @Test
    public void testPassesListToSorter() throws Exception {
        TimesheetLineItemProvider exportModelBuilder = createExportModelBuilder();

    }

    @Test
    public void testBuildDataRows_ThreeResultRow() throws Exception {
        ArrayList<TimeData> timeblocks = new ArrayList<>();
        String project1 = "project 01";
        String project2 = "project 02";
        String card1 = "card 01";
        String card2 = "card 02";
        String role1 = "role DEV";
        float elapsedTime1 = randomFloat();
        float elapsedTime2 = randomFloat();
        float elapsedTime3 = randomFloat();
        float elapsedTime4 = randomFloat();
        float elapsedTime5 = randomFloat();

        float expectedTimeProject1Card1 = elapsedTime1;
        float expectedTimeProject1Card2 = elapsedTime3;
        float expectedTimeProject2Card1 = elapsedTime2 + elapsedTime4 + elapsedTime5;

        timeblocks.add(createTime(project1, card1, role1, elapsedTime1, 0));
        timeblocks.add(createTime(project1, card2, role1, elapsedTime3, 0));

        timeblocks.add(createTime(project2, card1, role1, elapsedTime2, 0));
        timeblocks.add(createTime(project2, card1, role1, elapsedTime4, 0));
        timeblocks.add(createTime(project2, card1, role1, elapsedTime5, 0));

        TimesheetLineItemProvider exportModelBuilder = createExportModelBuilder();

        List<TimesheetLineItem> exportDataRows = exportModelBuilder.buildDataRows(timeblocks);
        assertEquals(3, exportDataRows.size());

        checkDataRow(project1, card2, role1, expectedTimeProject1Card2, exportDataRows.get(0));
        checkDataRow(project1, card1, role1, expectedTimeProject1Card1, exportDataRows.get(1));
        checkDataRow(project2, card1, role1, expectedTimeProject2Card1, exportDataRows.get(2));
    }

    private void checkDataRow(String project1, String card1, String role1, float expectedTotalTime, TimesheetLineItem exportDataRow) {
        assertEquals(card1, exportDataRow.getCard());

        String expectedDescription = project1 + " " + role1 + " " + card1;
        assertEquals(expectedDescription, exportDataRow.getDescription());
        assertEquals(role1, exportDataRow.getRole());
        assertEquals(expectedTotalTime, exportDataRow.getTotalTime(), 0);
    }

    @Test
    public void testBuildDataRows_AddsTimeToCorrectDay() throws Exception {
        ArrayList<TimeData> timeblocks = new ArrayList<>();
        String project1 = "project 1";
        String card1 = "card 1";
        String role1 = "role DEV";
        float elapsedTime1 = randomFloat();
        float elapsedTime2 = randomFloat();
        float elapsedTime3 = randomFloat();
        float expectedTotalTime = elapsedTime1 + elapsedTime2 + elapsedTime3;

        timeblocks.add(createTime(project1, card1, role1, elapsedTime1, 1));
        timeblocks.add(createTime(project1, card1, role1, elapsedTime2, 2));
        timeblocks.add(createTime(project1, card1, role1, elapsedTime3, 3));

        TimesheetLineItemProvider exportModelBuilder = createExportModelBuilder();

        List<TimesheetLineItem> exportDataRows = exportModelBuilder.buildDataRows(timeblocks);
        assertEquals(1, exportDataRows.size());
        TimesheetLineItem exportDataRow = exportDataRows.get(0);
        assertEquals(card1, exportDataRow.getCard());

        String expectedDescription = project1 + " " + role1 + " " + card1;
        assertEquals(expectedDescription, exportDataRow.getDescription());
        assertEquals(role1, exportDataRow.getRole());

        assertEquals(expectedTotalTime, exportDataRow.getTotalTime(), 0);
        assertEquals(0, exportDataRow.getTimeForDay(0), 0);
        assertEquals(elapsedTime1, exportDataRow.getTimeForDay(1), 0);
        assertEquals(elapsedTime2, exportDataRow.getTimeForDay(2), 0);
        assertEquals(elapsedTime3, exportDataRow.getTimeForDay(3), 0);
        assertEquals(0, exportDataRow.getTimeForDay(4), 0);
        assertEquals(0, exportDataRow.getTimeForDay(5), 0);
        assertEquals(0, exportDataRow.getTimeForDay(6), 0);
    }

    @Test
    public void testInsertBlankRows() throws Exception {
        String expectedProjectName1 = "Abba";
        String expectedProjectName2 = "Babba";
        String expectedProjectName3 = "Zabba";

        ArrayList<TimesheetLineItem> dataRows = new ArrayList<>();
        dataRows.add(createExportDataRow(expectedProjectName1));
        dataRows.add(createExportDataRow(expectedProjectName1));
        dataRows.add(createExportDataRow(expectedProjectName2));
        dataRows.add(createExportDataRow(expectedProjectName2));
        dataRows.add(createExportDataRow(expectedProjectName2));
        dataRows.add(createExportDataRow(expectedProjectName3));

        TimesheetLineItemProvider exportModelBuilder = new TimesheetLineItemProvider();
        List<TimesheetLineItem> resultRows = exportModelBuilder.insertBlankRows(dataRows);

        assertEquals(8, resultRows.size());
        assertProject(expectedProjectName1, resultRows.get(0));
        assertProject(expectedProjectName1, resultRows.get(1));
        assertProject("", resultRows.get(2));
        assertProject(expectedProjectName2, resultRows.get(3));
        assertProject(expectedProjectName2, resultRows.get(4));
        assertProject(expectedProjectName2, resultRows.get(5));
        assertProject("", resultRows.get(6));
        assertProject(expectedProjectName3, resultRows.get(7));
    }

    private void assertProject(String expectedProjectName, TimesheetLineItem actualDataRow) {
        assertEquals(expectedProjectName, actualDataRow.getWorkStatement().getProjectName());
    }

    private TimesheetLineItem createExportDataRow(String expectedProjectName1) {
        WorkStatement mockStatement = mock(WorkStatement.class);
        when(mockStatement.getProjectName()).thenReturn(expectedProjectName1);
        return new TimesheetLineItem(mockStatement, "", "", "", false, false);
    }

    private float randomFloat() {
        return (float) (Math.random() * 100);
    }

    private TimeData createTime(String project, String card, String role, float elapsedTime, int dayOfWeek) {
        TimeData mockData = mock(TimeData.class);
        when(mockData.getProject()).thenReturn(project);
        when(mockData.getCard()).thenReturn(card);
        when(mockData.getRole()).thenReturn(role);
        when(mockData.getElapsedTimeInHours()).thenReturn(elapsedTime);
        when(mockData.getDayOfWeek()).thenReturn(dayOfWeek);
        return mockData;
    }

    private TimesheetLineItemProvider createExportModelBuilder() {
        return new TimesheetLineItemProvider();
    }


}