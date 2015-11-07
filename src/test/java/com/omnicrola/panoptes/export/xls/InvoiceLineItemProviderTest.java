package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.WorkStatement;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
public class InvoiceLineItemProviderTest {

    @Test
    public void testCreatesOneRowForEachProject() throws Exception {
        ArrayList<TimesheetLineItem> exportRows = new ArrayList<>();
        float expectedTime1 = 23.5f;
        String expectedProject1 = "Hermes";
        String expectedProjectCode1 = "CODE 5150";
        TimesheetLineItem lineItem1 = createLineItem(expectedTime1, expectedProject1, expectedProjectCode1);
        exportRows.add(lineItem1);

        float expectedTime2 = 235.5f;
        String expectedProject2 = "Bond";
        String expectedProjectCode2 = "CODE 007";
        TimesheetLineItem lineItem2 = createLineItem(expectedTime2, expectedProject2, expectedProjectCode2);
        exportRows.add(lineItem2);

        InvoiceLineItemProvider invoiceLineItemProvider = new InvoiceLineItemProvider();
        Map<String, InvoiceRow> invoiceLineItems = invoiceLineItemProvider.create(exportRows);

        assertEquals(2, invoiceLineItems.size());
        checkInvoiceRow(expectedTime1, expectedProject1, lineItem1, invoiceLineItems.get(expectedProjectCode1));
        checkInvoiceRow(expectedTime2, expectedProject2, lineItem2, invoiceLineItems.get(expectedProjectCode2));

    }

    @Test
    public void testCombinesRowsForProjectsWithTheSameCode() throws Exception {
        ArrayList<TimesheetLineItem> exportRows = new ArrayList<>();
        float expectedTime1 = 675.5f;
        String expectedProject1 = "Hermes";
        String expectedProjectCode1 = "CODE 007";
        TimesheetLineItem lineItem1 = createLineItem(expectedTime1, expectedProject1, expectedProjectCode1);
        exportRows.add(lineItem1);

        float expectedTime2 = 235.5f;
        String expectedProject2 = "Not Hermes";
        TimesheetLineItem lineItem2 = createLineItem(expectedTime2, expectedProject2, expectedProjectCode1);
        exportRows.add(lineItem2);

        InvoiceLineItemProvider invoiceLineItemProvider = new InvoiceLineItemProvider();
        Map<String, InvoiceRow> invoiceLineItems = invoiceLineItemProvider.create(exportRows);

        assertEquals(1, invoiceLineItems.size());
        checkInvoiceRow(expectedTime1 + expectedTime2, expectedProject1 + ", " + expectedProject2, lineItem1, invoiceLineItems.get(expectedProjectCode1));
    }

    private void checkInvoiceRow(float expectedTime1, String expectedDescription, TimesheetLineItem lineItem1, InvoiceRow invoiceRow) {
        assertEquals(expectedTime1, invoiceRow.getTotalValue(), 0);
        assertEquals(expectedDescription, invoiceRow.getDescription());
        assertEquals(lineItem1.getWorkStatement(), invoiceRow.getWorkStatement());
    }

    private TimesheetLineItem createLineItem(float expectedTime, String expectedProject, String expectedProjectCode) {
        TimesheetLineItem mockLineItem = mock(TimesheetLineItem.class);
        when(mockLineItem.getTotalTime()).thenReturn(expectedTime);

        WorkStatement mockWorkStatement = mock(WorkStatement.class);
        when(mockLineItem.getWorkStatement()).thenReturn(mockWorkStatement);
        when(mockWorkStatement.getProjectName()).thenReturn(expectedProject);
        when(mockWorkStatement.getProjectCode()).thenReturn(expectedProjectCode);
        return mockLineItem;
    }


}