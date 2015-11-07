package com.omnicrola.panoptes.export;

import com.omnicrola.panoptes.export.xls.*;

/**
 * Created by omnic on 11/7/2015.
 */
public class XlsBuilderFactory {


    public static XlsExporter build() {
        return new XlsExporter(
                new TimesheetLineItemProvider(),
                new InvoiceLineItemProvider(),
                new PersonalDataXlsWriter(null),
                new TimesheetDataXlsWriter(null),
                new InvoiceDataXlsWriter(null)
        );
    }
}
