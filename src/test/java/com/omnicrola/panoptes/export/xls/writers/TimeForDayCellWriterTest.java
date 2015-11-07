package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.IExportDataContainer;
import com.omnicrola.panoptes.export.xls.TimesheetLineItem;
import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.omnicrola.test.util.TestUtil.randomInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TimeForDayCellWriterTest {
    @Mock
    IWorksheetRow mockWorksheetRow;
    @Mock
    ICell mockCell;
    @Mock
    TimesheetLineItem timesheetLineItem;

    @Test
    public void test() throws Exception {
        float expectedValue = (float) Math.random();
        char column = 'D';
        int row = randomInt();
        int expectedDay = randomInt();

        when(this.mockWorksheetRow.getCell(column, row)).thenReturn(this.mockCell);
        when(this.timesheetLineItem.getTimeForDay(expectedDay)).thenReturn(expectedValue);

        TimeForDayCellWriter timeForDayCellWriter = new TimeForDayCellWriter(column, row, this.timesheetLineItem, expectedDay);
        timeForDayCellWriter.write(this.mockWorksheetRow);

        verify(this.mockCell).setValue(expectedValue);
    }
}