package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TimesheetRowXlsWriterTest {
    @Mock
    IWorksheet mockWorksheet;
    @Mock
    IWorksheetRow mockRow;
    @Mock
    TimesheetLineItem mockLineItem;
    @Mock
    WorkStatement mockWorkStatement;

    @Test
    public void testWriteRow() throws Exception {
        String expectedDescription = "I've got a lovely bunch of cocoanuts";
        String expectedProjectCode = "FE92872";
        String expectedClient = "Subach Ennies";

        when(this.mockLineItem.getWorkStatement()).thenReturn(this.mockWorkStatement);

        when(this.mockLineItem.getDescription()).thenReturn(expectedDescription);
        when(this.mockLineItem.isBillableToClient()).thenReturn(true);
        when(this.mockLineItem.isBillableToMenlo()).thenReturn(true);
        when(this.mockWorkStatement.getProjectCode()).thenReturn(expectedProjectCode);
        when(this.mockWorkStatement.getClient()).thenReturn(expectedClient);

        float expectedTime1 = randomFloat();
        float expectedTime2 = randomFloat();
        float expectedTime3 = randomFloat();
        float expectedTime4 = randomFloat();
        float expectedTime5 = randomFloat();
        float expectedTime6 = randomFloat();
        float expectedTime7 = randomFloat();
        when(this.mockLineItem.getTimeForDay(0)).thenReturn(expectedTime1);
        when(this.mockLineItem.getTimeForDay(1)).thenReturn(expectedTime2);
        when(this.mockLineItem.getTimeForDay(2)).thenReturn(expectedTime3);
        when(this.mockLineItem.getTimeForDay(3)).thenReturn(expectedTime4);
        when(this.mockLineItem.getTimeForDay(4)).thenReturn(expectedTime5);
        when(this.mockLineItem.getTimeForDay(5)).thenReturn(expectedTime6);
        when(this.mockLineItem.getTimeForDay(6)).thenReturn(expectedTime7);


        TimesheetRowXlsWriter timesheetRowXlsWriter = new TimesheetRowXlsWriter();
        timesheetRowXlsWriter.write(this.mockRow, this.mockLineItem);


    }

    private float randomFloat() {
        return (float) (Math.random() * 100f);
    }
}