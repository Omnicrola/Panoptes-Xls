package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.PanoptesException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by omnic on 11/7/2015.
 */
public class XlsFileLoader {

    public static XSSFWorkbook loadTemplate(String filename) {
        try {
            InputStream fileInputStream = XlsFileLoader.class.getResourceAsStream(filename);
            if (fileInputStream == null) {
                throw new FileNotFoundException(filename);
            }
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            return xssfWorkbook;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PanoptesException("Export failed.\nThe invoice template \""
                    + filename + "\" could not be found.");
        } catch (IOException e) {
            throw new PanoptesException(
                    "Export failed.\nAn I/O error was encountered while reading the inoice template file.");
        }
    }
}
