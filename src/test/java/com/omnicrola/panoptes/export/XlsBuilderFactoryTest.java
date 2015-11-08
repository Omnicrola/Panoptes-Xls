package com.omnicrola.panoptes.export;

import com.omnicrola.panoptes.export.xls.*;
import org.junit.Test;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static junit.framework.TestCase.assertSame;

/**
 * Created by omnic on 11/8/2015.
 */
public class XlsBuilderFactoryTest {
    @Test
    public void testBuild() throws Exception {
        XlsWriter xlsWriter = XlsWriterFactory.build();

        assertIsOfType(TimesheetLineItemProvider.class, xlsWriter.getTimesheetLineItemProvider());
        assertIsOfType(InvoiceLineItemProvider.class, xlsWriter.getInvoiceLineItemProvider());

        assertIsOfType(InvoiceDataXlsWriter.class, xlsWriter.getInvoiceDataXlsWriter());

        TimesheetDataXlsWriter timesheetDataXlsWriter = assertIsOfType(TimesheetDataXlsWriter.class, xlsWriter.getTimesheetDataXlsWriter());
        assertIsOfType(TimesheetRowXlsWriter.class, timesheetDataXlsWriter.getTimesheetRowXlsWriter());
        assertSame(TemplateConfiguration.INSTANCE, timesheetDataXlsWriter.getTemplateConfiguration());

        assertIsOfType(PersonalDataXlsWriter.class, xlsWriter.getPersonalDataWriter());

        assertSame(TemplateConfiguration.INSTANCE, xlsWriter.getTemplateConfiguration());
    }
}