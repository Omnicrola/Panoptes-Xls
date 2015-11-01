package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.WorkStatement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 10/31/2015.
 */
public class InvoiceRowTest {

    private WorkStatement mockWorkStatement;

    @Before
    public void setup() {
        this.mockWorkStatement = mock(WorkStatement.class);
    }

    @Test
    public void testGetConstructorArgs() throws Exception {
        String expectedDescription = "my name";
        when(this.mockWorkStatement.getProjectName()).thenReturn(expectedDescription);

        InvoiceRow invoiceRow = createInvoiceRow();
        assertSame(this.mockWorkStatement, invoiceRow.getWorkStatement());
        assertEquals(expectedDescription, invoiceRow.getDescription());
    }


    @Test
    public void testKeepsTotalTime() throws Exception {
        InvoiceRow invoiceRow = createInvoiceRow();
        float time1 = randomFloat();
        float time2 = randomFloat();
        float time3 = randomFloat();
        float time4 = randomFloat();

        String projectName = "project";
        invoiceRow.addTime(projectName, time1);
        invoiceRow.addTime(projectName, time2);
        assertEquals(time1 + time2, invoiceRow.getTotalValue(), 0);

        invoiceRow.addTime(projectName, time3);
        invoiceRow.addTime(projectName, time4);
        assertEquals(time1 + time2 + time3 + time4, invoiceRow.getTotalValue(), 0);
    }

    @Test
    public void testAddingSameProjectDoesNotAppendName() throws Exception {
        String firstProject = "oscar";
        when(this.mockWorkStatement.getProjectName()).thenReturn(firstProject);

        InvoiceRow invoiceRow = createInvoiceRow();
        assertEquals(firstProject, invoiceRow.getDescription());
        invoiceRow.addTime(firstProject, randomFloat());
    }

    @Test
    public void testAddingProjectAppendsDescription() throws Exception {
        String firstProject = "oscar";
        String secondProject = "snuffalupagus";
        String thirdProject = "big bird";
        when(this.mockWorkStatement.getProjectName()).thenReturn(firstProject);

        InvoiceRow invoiceRow = createInvoiceRow();
        invoiceRow.addTime(firstProject, randomFloat());
        invoiceRow.addTime(secondProject, randomFloat());
        invoiceRow.addTime(thirdProject, randomFloat());

        String expectedDescription = firstProject + ", " + secondProject + ", " + thirdProject;
        assertEquals(expectedDescription, invoiceRow.getDescription());
    }

    private float randomFloat() {
        return (float) (Math.random() * 100);
    }

    private InvoiceRow createInvoiceRow() {
        return new InvoiceRow(this.mockWorkStatement);
    }
}