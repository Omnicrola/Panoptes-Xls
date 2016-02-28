package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.export.xls.XssfUtilities;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesWorksheet;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesWorksheetRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static com.omnicrola.test.util.TestUtil.randomInt;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyZeroInteractions;

/**
 * Created by omnic on 11/8/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XssfUtilities.class)
public class PanoptesWorksheetTest {
    @Mock
    XSSFSheet mockSheet;

    @Test
    public void testGetSheet() throws Exception {
        PanoptesWorksheet panoptesWorksheet = createSheet();
        assertSame(this.mockSheet, panoptesWorksheet.getXssfSheet());
    }

    @Test
    public void testGetRow() throws Exception {
        XSSFRow mockRow = mock(XSSFRow.class);
        int expectedRowNumber = anyInt();
        when(this.mockSheet.getRow(expectedRowNumber)).thenReturn(mockRow);

        PanoptesWorksheet sheet = createSheet();
        IWorksheetRow row = sheet.getRow(expectedRowNumber);

        PanoptesWorksheetRow panoptesWorksheetRow = assertIsOfType(PanoptesWorksheetRow.class, row);
        assertSame(mockRow, panoptesWorksheetRow.getXssfRow());
    }

    @Test
    public void testCopyRow() throws Exception {
        int sourceIndex = randomInt();
        int destinationIndex = randomInt();
        mockStatic(XssfUtilities.class);

        XSSFRow expectedRow = mock(XSSFRow.class);
        XSSFRow expectedDestinationRow = mock(XSSFRow.class);
        when(this.mockSheet.getRow(sourceIndex)).thenReturn(expectedRow);
        when(this.mockSheet.getRow(destinationIndex)).thenReturn(expectedDestinationRow);

        PanoptesWorksheet sheet = createSheet();
        sheet.copyRow(sourceIndex, destinationIndex);

        PowerMockito.verifyStatic();
        XssfUtilities.copyRow(expectedRow, expectedDestinationRow);
        this.mockSheet.createRow(2);
    }

    @Test
    public void testInsertRowsAt() throws Exception {
        int insertPosition = randomInt();
        int rowsToInsert = randomInt();

        int physicalRowCount = randomInt();
        when(this.mockSheet.getPhysicalNumberOfRows()).thenReturn(physicalRowCount);

        PanoptesWorksheet sheet = createSheet();
        sheet.insertRowsAt(insertPosition, rowsToInsert);

        verify(this.mockSheet).shiftRows(insertPosition, physicalRowCount, rowsToInsert, true, true);
    }

    @Test
    public void testInsertRowsAt_IgnoresZeroShifts() throws Exception {
        int insertPosition = randomInt();
        int rowsToInsert = 0;

        int physicalRowCount = randomInt();
        when(this.mockSheet.getPhysicalNumberOfRows()).thenReturn(physicalRowCount);

        PanoptesWorksheet sheet = createSheet();
        sheet.insertRowsAt(insertPosition, rowsToInsert);

        verifyZeroInteractions(this.mockSheet);
    }

    private PanoptesWorksheet createSheet() {
        return new PanoptesWorksheet(mockSheet);
    }
}