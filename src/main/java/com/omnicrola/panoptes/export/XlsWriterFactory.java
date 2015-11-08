package com.omnicrola.panoptes.export;

import com.omnicrola.panoptes.export.xls.*;

/**
 * Created by omnic on 11/7/2015.
 */
public class XlsWriterFactory {


    public static XlsWriter build() {
        TemplateConfiguration templateConfig = TemplateConfiguration.INSTANCE;
        TimesheetDataXlsWriter timesheetDataXlsWriter = new TimesheetDataXlsWriter(templateConfig, new TimesheetRowXlsWriter());

        return new XlsWriter(
                new TimesheetLineItemProvider(),
                new InvoiceLineItemProvider(),
                new PersonalDataXlsWriter(),
                timesheetDataXlsWriter,
                new InvoiceDataXlsWriter(),
                templateConfig);
    }
}
