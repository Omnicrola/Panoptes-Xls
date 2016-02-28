package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.export.TemplateConfiguration;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/7/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XlsFileLoader.class)
public class XlsWriterTest {
    @Mock
    IWorkbook mockWorkbook;
    @Mock
    IWorksheet mockWorksheet;
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
    @Mock
    TemplateConfiguration mockTemplateConfig;

    @Test
    public void testPassesDataFromContainerToWriters() throws Exception {
        setFileLoaderToReturnWorkbook();

        int expectedTimsheetIndex = 293;
        when(this.mockTemplateConfig.getIndexOfTimesheetSheet()).thenReturn(expectedTimsheetIndex);
        when(this.mockWorkbook.getSheet(expectedTimsheetIndex)).thenReturn(this.mockWorksheet);

        ExportDataContainer mockDataContainer = createDataContainer();
        List<TimesheetLineItem> expectedTimesheetRows = Collections.unmodifiableList(new ArrayList<>());
        Map<String, InvoiceRow> expectedInvoiceRows = Collections.unmodifiableMap(new HashMap<>());

        when(timesheetLineItemProvider.buildDataRows(any(List.class))).thenReturn(expectedTimesheetRows);
        when(invoiceLineItemProvider.create(any(List.class))).thenReturn(expectedInvoiceRows);

        XlsWriter excelExporter = createExporter();

        IWorkbook workbook = excelExporter.write(mockDataContainer);
        assertSame(this.mockWorkbook, workbook);
        verify(timesheetLineItemProvider).buildDataRows(mockDataContainer.timeblocks);
        verify(invoiceLineItemProvider).create(expectedTimesheetRows);
        verify(timesheetDataXlsWriter).write(this.mockWorksheet, expectedTimesheetRows);
        verify(invoiceDataWriter).writeInvoiceData(this.mockWorkbook, expectedInvoiceRows);
        verify(personalDataWriter).write(this.mockWorkbook, mockDataContainer.personalData,mockDataContainer.weekEnding);
    }

    private ExportDataContainer createDataContainer() {
        ExportDataContainer mockDataContainer = new ExportDataContainer();
        mockDataContainer.timeblocks = Collections.unmodifiableList(new ArrayList<>());
        mockDataContainer.workStatements = Collections.unmodifiableList(new ArrayList<>());
        return mockDataContainer;
    }

    private void setFileLoaderToReturnWorkbook() {
        PowerMockito.mockStatic(XlsFileLoader.class);
        when(XlsFileLoader.loadTemplate("/invoiceTemplate.xlsx")).thenReturn(this.mockWorkbook);
    }

    private XlsWriter createExporter() {
        return new XlsWriter(this.timesheetLineItemProvider,
                this.invoiceLineItemProvider,
                this.personalDataWriter,
                this.timesheetDataXlsWriter,
                this.invoiceDataWriter,
                this.mockTemplateConfig);
    }
}