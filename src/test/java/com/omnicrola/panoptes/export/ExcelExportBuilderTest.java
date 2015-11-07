package com.omnicrola.panoptes.export;

import com.omnicrola.panoptes.export.xls.ExcelExporter;
import org.junit.Test;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;

/**
 * Created by omnic on 11/7/2015.
 */
public class ExcelExportBuilderTest {
    @Test
    public void testBuildsExcelExporter() throws Exception {
         assertIsOfType(ExcelExporter.class, XlsBuilderFactory.build());
    }
}