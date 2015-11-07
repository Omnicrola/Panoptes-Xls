package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

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


        ICell mockCell1 = mock(ICell.class);
        ICell mockCell2 = mock(ICell.class);
        ICell mockCell3 = mock(ICell.class);
        ICell mockCell4 = mock(ICell.class);
        ICell mockCell5 = mock(ICell.class);
        ICell mockCell6 = mock(ICell.class);
        ICell mockCell7 = mock(ICell.class);
        ICell mockCell8 = mock(ICell.class);
        ICell mockCell9 = mock(ICell.class);
        ICell mockCell10 = mock(ICell.class);
        ICell mockCell11 = mock(ICell.class);
        ICell mockCell12 = mock(ICell.class);
        ICell mockCell13 = mock(ICell.class);
        ICell mockCell14 = mock(ICell.class);
        ICell mockCell15 = mock(ICell.class);

        when(this.mockRow.getCell('B')).thenReturn(mockCell1);
        when(this.mockRow.getCell('C')).thenReturn(mockCell2);
        when(this.mockRow.getCell('D')).thenReturn(mockCell3);
        when(this.mockRow.getCell('E')).thenReturn(mockCell4);
        when(this.mockRow.getCell('F')).thenReturn(mockCell5);
        when(this.mockRow.getCell('G')).thenReturn(mockCell6);
        when(this.mockRow.getCell('H')).thenReturn(mockCell7);
        when(this.mockRow.getCell('I')).thenReturn(mockCell8);
        when(this.mockRow.getCell('J')).thenReturn(mockCell9);
        when(this.mockRow.getCell('K')).thenReturn(mockCell10);
        when(this.mockRow.getCell('L')).thenReturn(mockCell11);
        when(this.mockRow.getCell('M')).thenReturn(mockCell12);

        when(this.mockRow.getCell('N')).thenReturn(mockCell13);
        when(this.mockRow.getCell('O')).thenReturn(mockCell14);
        when(this.mockRow.getCell('P')).thenReturn(mockCell15);

        when(this.mockLineItem.getWorkStatement()).thenReturn(this.mockWorkStatement);

        when(this.mockLineItem.getDescription()).thenReturn(expectedDescription);
        when(this.mockLineItem.isBillableToClient()).thenReturn(false);
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

        verify(mockCell1).setValue(expectedClient);
        verify(mockCell2).setValue(expectedProjectCode);
        verify(mockCell3).setValue(expectedDescription);

        verify(mockCell4).setValue("Y");
        verify(mockCell5).setValue("N");

        verify(mockCell6).setValue(expectedTime1);
        verify(mockCell7).setValue(expectedTime2);
        verify(mockCell8).setValue(expectedTime3);
        verify(mockCell9).setValue(expectedTime4);
        verify(mockCell10).setValue(expectedTime5);
        verify(mockCell11).setValue(expectedTime6);
        verify(mockCell12).setValue(expectedTime7);

    }

    private float randomFloat() {
        return (float) (Math.random() * 100f);
    }
}