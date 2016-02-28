package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.PanoptesException;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesWorksheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.OutputStream;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static com.omnicrola.test.util.TestUtil.randomInt;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by omnic on 11/8/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XSSFWorkbook.class)
public class PanoptesWorkbookTest {

    @Mock
    XSSFWorkbook mockWorkbook;

    @Test
    public void testGetter() throws Exception {
        PanoptesWorkbook panoptesWorkbook = createWorkbook();
        assertSame(this.mockWorkbook, panoptesWorkbook.getXssfWorkbook());
    }

    @Test
    public void testGetSheet() throws Exception {
        int expectedId = randomInt();

        XSSFSheet expectedSheet = mock(XSSFSheet.class);
        when(this.mockWorkbook.getSheetAt(expectedId)).thenReturn(expectedSheet);

        PanoptesWorkbook panoptesWorkbook = createWorkbook();
        IWorksheet sheet = panoptesWorkbook.getSheet(expectedId);
        PanoptesWorksheet panoptesWorksheet = assertIsOfType(PanoptesWorksheet.class, sheet);
        assertSame(expectedSheet, panoptesWorksheet.getXssfSheet());
    }

    @Test
    public void testWrite() throws Exception {
        OutputStream mockOutputStream = mock(OutputStream.class);

        PanoptesWorkbook workbook = createWorkbook();
        workbook.write(mockOutputStream);

        verify(this.mockWorkbook).write(mockOutputStream);
    }

    @Test
    public void testWrite_ThrowsPanoptesException() throws Exception {
        OutputStream mockOutputStream = mock(OutputStream.class);

        IOException expectedException = new IOException();
        doThrow(expectedException).when(this.mockWorkbook).write(mockOutputStream);


        boolean assertionWasThrown = false;
        PanoptesWorkbook workbook = createWorkbook();
        try {
            workbook.write(mockOutputStream);
        } catch (Exception e) {
            PanoptesException panoptesException = assertIsOfType(PanoptesException.class, e);
            assertSame(expectedException, panoptesException.getException());
            assertionWasThrown = true;
        }

        assertTrue(assertionWasThrown);
        verify(this.mockWorkbook).write(mockOutputStream);
    }

    private PanoptesWorkbook createWorkbook() {
        return new PanoptesWorkbook(this.mockWorkbook);
    }


}