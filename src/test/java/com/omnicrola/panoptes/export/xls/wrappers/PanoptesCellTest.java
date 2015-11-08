package com.omnicrola.panoptes.export.xls.wrappers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.omnicrola.test.util.TestUtil.randomFloat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by omnic on 11/8/2015.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(XSSFCell.class)
public class PanoptesCellTest {
    @Mock
    XSSFCell mockCell;

    @Test
    public void testGetCell() throws Exception {
        PanoptesCell panoptesCell = createWorksheetCell();
        assertSame(this.mockCell, panoptesCell.getXssfCell());
    }


    @Test
    public void testClear() throws Exception {
        PanoptesCell worksheetCell = createWorksheetCell();
        worksheetCell.clear();

        verify(this.mockCell).setCellType(Cell.CELL_TYPE_BLANK);
    }

    @Test
    public void testSetValue_String() throws Exception {
        String expectedString = "Lump is a fuzzy cat";

        PanoptesCell worksheetCell = createWorksheetCell();
        worksheetCell.setValue(expectedString);

        verify(this.mockCell).setCellValue(expectedString);
    }

    @Test
    public void testSetValue_Float() throws Exception {
        float expectedValue = randomFloat();

        PanoptesCell worksheetCell = createWorksheetCell();
        worksheetCell.setValue(expectedValue);

        verify(this.mockCell).setCellValue(expectedValue);
    }
    @Test
    public void testSetValue_Float_BlanksCellIfZero() throws Exception {


        PanoptesCell worksheetCell = createWorksheetCell();
        worksheetCell.setValue(0);

        verify(this.mockCell).setCellType(Cell.CELL_TYPE_BLANK);
    }

    private PanoptesCell createWorksheetCell() {
        return new PanoptesCell(this.mockCell);
    }
}