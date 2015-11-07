package com.omnicrola.panoptes.export.xls;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import sun.misc.Launcher;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.Assert.assertNotNull;

/**
 * Created by omnic on 11/7/2015.
 */
public class XlsFileLoaderTest {
    @Test
    public void testLoadTemplate_functional() throws Exception {
        String actualTemplateFile = "/invoiceTemplate.xlsx";
        XSSFWorkbook workbook = XlsFileLoader.loadTemplate(actualTemplateFile);
        assertNotNull(workbook);
    }


}