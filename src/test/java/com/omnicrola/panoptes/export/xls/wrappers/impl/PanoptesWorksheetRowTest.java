package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesCell;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesWorksheetRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static com.omnicrola.test.util.TestUtil.randomInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by omnic on 11/8/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({XSSFRow.class, XSSFCell.class})
public class PanoptesWorksheetRowTest {

    private static final char[] ALPHANUMERIC = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    @Mock
    XSSFRow mockXssfRow;
    @Mock
    XSSFCell mockXssfCell;

    @Test
    public void testGetRow() throws Exception {
        PanoptesWorksheetRow panoptesWorksheetRow = createWorksheetRow();
        assertSame(this.mockXssfRow, panoptesWorksheetRow.getXssfRow());
    }
@Test
public void testGetRowNumber() throws Exception {
    int expectedRowNumber = randomInt();

    when(this.mockXssfRow.getRowNum()).thenReturn(expectedRowNumber);

    PanoptesWorksheetRow worksheetRow = createWorksheetRow();
    assertEquals(expectedRowNumber, worksheetRow.getRowNumber());
}
    @Test
    public void testGetCell() throws Exception {
        for (int i = 0; i < ALPHANUMERIC.length; i++) {
            char columnLetter = ALPHANUMERIC[i];
            int cellIndex = i + 1;
            runCase(columnLetter, cellIndex);
        }
    }

    private void runCase(char columnLetter, int cellIndex) {
        XSSFCell expectedCell = mock(XSSFCell.class);
        this.mockXssfRow = mock(XSSFRow.class);
        when(this.mockXssfRow.getCell(anyInt())).thenReturn(expectedCell);

        PanoptesWorksheetRow worksheetRow = createWorksheetRow();
        ICell cell = worksheetRow.getCell(columnLetter);
        PanoptesCell panoptesCell = assertIsOfType(PanoptesCell.class, cell);

        assertSame(expectedCell, panoptesCell.getXssfCell());
        verify(this.mockXssfRow).getCell(cellIndex);
    }

    private PanoptesWorksheetRow createWorksheetRow() {
        return new PanoptesWorksheetRow(this.mockXssfRow);
    }
}