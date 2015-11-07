package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.ExportDataContainer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XlsFileLoader.class)
public class ExcelExporterTest {
    @Mock
    XSSFWorkbook mockWorkbook;
    @Mock
    InvoiceLineItemProvider invoiceLineItemProvider;
    @Mock
    TimesheetLineItemProvider timesheetLineItemProvider;
    @Mock
    PersonalDataXlsWriter personalDataWriter;
    @Mock
    InvoiceDataXlsWriter invoiceDataWriter;
    @Mock
    TimesheetDataXlsWriter timesheetDataXlsWriter;

    @Test
    public void testLoadsDataFromProvidersIntoWriters() throws Exception {
        setFileLoaderToReturnWorkbook();
        ExportDataContainer mockDataContainer = createDataContainer();
        List<TimesheetLineItem> expectedTimesheetRows = Collections.unmodifiableList(new ArrayList<>());
        Map<String, InvoiceRow> expectedInvoiceRows = Collections.unmodifiableMap(new HashMap<>());

        when(timesheetLineItemProvider.buildDataRows(any(List.class))).thenReturn(expectedTimesheetRows);
        when(invoiceLineItemProvider.create(any(List.class))).thenReturn(expectedInvoiceRows);

        XlsExporter excelExporter = createExporter();

        XSSFWorkbook workbook = excelExporter.build(mockDataContainer);
        assertSame(this.mockWorkbook, workbook);
        verify(timesheetLineItemProvider).buildDataRows(mockDataContainer.timeblocks);
        verify(invoiceLineItemProvider).create(expectedTimesheetRows);
        verify(timesheetDataXlsWriter).writeTimesheetData(this.mockWorkbook, expectedTimesheetRows);
        verify(invoiceDataWriter).writeInvoiceData(this.mockWorkbook, expectedInvoiceRows);
    }

    private ExportDataContainer createDataContainer() {
        ExportDataContainer mockDataContainer = new ExportDataContainer();
        mockDataContainer.timeblocks = Collections.unmodifiableList(new ArrayList<>());
        mockDataContainer.workStatements = Collections.unmodifiableList(new ArrayList<>());
        return mockDataContainer;
    }

    private void setFileLoaderToReturnWorkbook() {
        PowerMockito.mockStatic(XlsFileLoader.class);
        when(XlsFileLoader.loadTemplate("invoiceTemplate.xlsx")).thenReturn(this.mockWorkbook);
    }

    private XlsExporter createExporter() {
        return new XlsExporter(this.timesheetLineItemProvider, this.invoiceLineItemProvider, this.personalDataWriter, this.timesheetDataXlsWriter, this.invoiceDataWriter);
    }
}