package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.ExportDataContainer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XlsFileLoader.class)
public class ExcelExporterTest {
    @Mock
    XSSFWorkbook mockWorkbook;

    @Test
    public void testReturnsTemplate() throws Exception {
        PowerMockito.mockStatic(XlsFileLoader.class);
        when(XlsFileLoader.loadTemplate("invoiceTemplate.xlsx")).thenReturn(this.mockWorkbook);

        ExcelExporter excelExporter = new ExcelExporter();
        XSSFWorkbook workbook = excelExporter.build(new ExportDataContainer());
        assertSame(this.mockWorkbook, workbook);
    }
}