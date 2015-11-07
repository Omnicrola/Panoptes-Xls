package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    public void testWriteData() throws Exception {
        IWorksheetRow mockWorksheetRow1 = mock(IWorksheetRow.class);
        IWorksheetRow mockWorksheetRow2 = mock(IWorksheetRow.class);
        IWorksheetRow mockWorksheetRow3 = mock(IWorksheetRow.class);

        ArrayList<TimesheetLineItem> timesheetRows = new ArrayList<>();
        TimesheetLineItem lineItem1 = mock(TimesheetLineItem.class);
        TimesheetLineItem lineItem2 = mock(TimesheetLineItem.class);
        TimesheetLineItem lineItem3 = mock(TimesheetLineItem.class);
        timesheetRows.add(lineItem1);
        timesheetRows.add(lineItem2);
        timesheetRows.add(lineItem3);

        TimesheetDataXlsWriter timesheetDataXlsWriter = new TimesheetDataXlsWriter(this.mockRowWriter);
        timesheetDataXlsWriter.write(this.mockWorksheet, timesheetRows);
        
        verify(this.mockRowWriter).write(mockWorksheetRow1, lineItem1);
        verify(this.mockRowWriter).write(mockWorksheetRow2, lineItem2);
        verify(this.mockRowWriter).write(mockWorksheetRow3, lineItem3);

    }
}