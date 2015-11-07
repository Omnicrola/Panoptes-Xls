package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.xls.TimesheetLineItem;
import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.omnicrola.test.util.TestUtil.assertImplementsInterface;
import static com.omnicrola.test.util.TestUtil.randomInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BillableItemCellWriterTest {


    @Test
    public void testImplements() throws Exception {
        assertImplementsInterface(BillableItemCellWriter.class, ICellWriter.class);
    }

    @Test
    public void testWrite_True_True() throws Exception {
        runCaseWith(true, true, "Y", "Y");
    }

    @Test
    public void testWrite_True_False() throws Exception {
        runCaseWith(true, false, "Y", "N");
    }

    @Test
    public void testWrite_False_True() throws Exception {
        runCaseWith(false, true, "N", "Y");
    }

    @Test
    public void testWrite_False_False() throws Exception {
        runCaseWith(false, false, "N", "N");
    }

    private void runCaseWith(boolean expectedBillableMenlo, boolean expectedBillableClient, String expectedValue1, String expectedValue2) {
        ICell mockCell1 = mock(ICell.class);
        ICell mockCell2 = mock(ICell.class);
        IWorksheetRow worksheetRow = mock(IWorksheetRow.class);
        TimesheetLineItem timesheetLineItem = mock(TimesheetLineItem.class);

        int expectedRow = randomInt();

        when(worksheetRow.getCell('E', expectedRow)).thenReturn(mockCell1);
        when(worksheetRow.getCell('F', expectedRow)).thenReturn(mockCell2);
        when(timesheetLineItem.isBillableToMenlo()).thenReturn(expectedBillableMenlo);
        when(timesheetLineItem.isBillableToClient()).thenReturn(expectedBillableClient);

        BillableItemCellWriter billableItemCellWriter = new BillableItemCellWriter(timesheetLineItem, expectedRow);
        billableItemCellWriter.write(worksheetRow);

        verify(mockCell1).setValue(expectedValue1);
        verify(mockCell2).setValue(expectedValue2);
    }
}