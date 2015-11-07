package com.omnicrola.panoptes.export;

import com.omnicrola.panoptes.export.xls.ExcelExporter;

/**
 * Created by omnic on 11/7/2015.
 */
public class XlsBuilderFactory {


    public static ExcelExporter build() {
        return new ExcelExporter();
    }
}
