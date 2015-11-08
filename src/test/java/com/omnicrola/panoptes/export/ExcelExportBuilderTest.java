package com.omnicrola.panoptes.export;

import com.omnicrola.panoptes.export.xls.*;
import org.junit.Test;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;

/**
 * Created by omnic on 11/7/2015.
 */
public class ExcelExportBuilderTest {
    @Test
    public void testBuildsExcelExporter() throws Exception {
        XlsWriter xlsExporter = assertIsOfType(XlsWriter.class, XlsWriterFactory.build());

        assertIsOfType(TimesheetLineItemProvider.class, xlsExporter.getTimesheetLineItemProvider());
        assertIsOfType(InvoiceLineItemProvider.class, xlsExporter.getInvoiceLineItemProvider());
        assertIsOfType(InvoiceDataXlsWriter.class, xlsExporter.getInvoiceDataXlsWriter());
        assertIsOfType(TimesheetDataXlsWriter.class, xlsExporter.getTimesheetDataXlsWriter());
        assertIsOfType(PersonalDataXlsWriter.class, xlsExporter.getPersonalDataWriter());
    }
}