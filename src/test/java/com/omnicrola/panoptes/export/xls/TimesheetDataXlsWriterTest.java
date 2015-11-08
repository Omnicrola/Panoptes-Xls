package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.export.TemplateConfiguration;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.omnicrola.test.util.TestUtil.randomInt;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TimesheetDataXlsWriterTest {
    @Mock
    IWorkbook mockWorkbook;
    @Mock
    IWorksheet mockWorksheet;
    @Mock
    TimesheetRowXlsWriter mockRowWriter;
    @Mock
    TemplateConfiguration mockTemplateConfiguration;

    @Test
    public void testWrite_PassesRowsToRowWriter() throws Exception {
        int expectedInsertPosition = setConfigToReturnInsertPosition();

        IWorksheetRow mockWorksheetRow1 = mock(IWorksheetRow.class);
        IWorksheetRow mockWorksheetRow2 = mock(IWorksheetRow.class);
        IWorksheetRow mockWorksheetRow3 = mock(IWorksheetRow.class);
        when(this.mockWorksheet.getRow(expectedInsertPosition)).thenReturn(mockWorksheetRow1);
        when(this.mockWorksheet.getRow(expectedInsertPosition + 1)).thenReturn(mockWorksheetRow2);
        when(this.mockWorksheet.getRow(expectedInsertPosition + 2)).thenReturn(mockWorksheetRow3);

        ArrayList<TimesheetLineItem> timesheetRows = new ArrayList<>();
        TimesheetLineItem lineItem1 = mock(TimesheetLineItem.class);
        TimesheetLineItem lineItem2 = mock(TimesheetLineItem.class);
        TimesheetLineItem lineItem3 = mock(TimesheetLineItem.class);
        timesheetRows.add(lineItem1);
        timesheetRows.add(lineItem2);
        timesheetRows.add(lineItem3);

        TimesheetDataXlsWriter timesheetDataXlsWriter = createTimesheetWriter();
        timesheetDataXlsWriter.write(this.mockWorksheet, timesheetRows);

        verify(this.mockRowWriter).write(mockWorksheetRow1, lineItem1);
        verify(this.mockRowWriter).write(mockWorksheetRow2, lineItem2);
        verify(this.mockRowWriter).write(mockWorksheetRow3, lineItem3);

    }

    @Test
    public void testWrite_CopiesNewRowsFromTemplateRow() throws Exception {
        int insertIndex = setConfigToReturnInsertPosition();
        int expectedTemplateRowIndex = insertIndex - 1;
        setWorksheetToReturnRows();
        List<TimesheetLineItem> lineItems = createLineItems(3);

        TimesheetDataXlsWriter timesheetWriter = createTimesheetWriter();
        timesheetWriter.write(this.mockWorksheet, lineItems);

        verify(this.mockWorksheet).copyRow(expectedTemplateRowIndex, insertIndex);
        verify(this.mockWorksheet).copyRow(expectedTemplateRowIndex, insertIndex + 1);
        verify(this.mockWorksheet).copyRow(expectedTemplateRowIndex, insertIndex + 2);
    }

    @Test
    public void testWrite_callsCopyBeforeCallingRowWriter() throws Exception {
        int insertPosition = setConfigToReturnInsertPosition();
        setWorksheetToReturnRows();

        doAnswer((invocation) -> {
            verifyZeroInteractions(this.mockRowWriter);
            return null;
        })
                .when(this.mockWorksheet)
                .copyRow(anyInt(), anyInt());

        List<TimesheetLineItem> lineItems = createLineItems(1);
        TimesheetDataXlsWriter timesheetWriter = createTimesheetWriter();
        timesheetWriter.write(this.mockWorksheet, lineItems);

        verify(this.mockWorksheet).copyRow(anyInt(), anyInt());

    }

    @Test
    public void testWrite_InsertsRowsToFitNewTimesheetRows() throws Exception {
        int insertPosition = setConfigToReturnInsertPosition();
        setWorksheetToReturnRows();

        int expectedRowCount = 16;
        List<TimesheetLineItem> timesheetLineItems = createLineItems(expectedRowCount);


        TimesheetDataXlsWriter timesheetWriter = createTimesheetWriter();
        timesheetWriter.write(this.mockWorksheet, timesheetLineItems);

        verify(this.mockWorksheet).insertRowsAt(insertPosition, expectedRowCount);
    }

    private List<TimesheetLineItem> createLineItems(int count) {
        ArrayList<TimesheetLineItem> timesheetLineItems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            timesheetLineItems.add(mock(TimesheetLineItem.class));
        }
        return timesheetLineItems;
    }

    private int setConfigToReturnInsertPosition() {
        int expectedInsertPosition = randomInt();
        when(mockTemplateConfiguration.getTimesheetRowInsertPosition()).thenReturn(expectedInsertPosition);
        return expectedInsertPosition;
    }

    private void setWorksheetToReturnRows() {
        when(this.mockWorksheet.getRow(anyInt())).thenReturn(mock(IWorksheetRow.class));
    }

    private TimesheetDataXlsWriter createTimesheetWriter() {
        return new TimesheetDataXlsWriter(this.mockTemplateConfiguration, this.mockRowWriter);
    }
}