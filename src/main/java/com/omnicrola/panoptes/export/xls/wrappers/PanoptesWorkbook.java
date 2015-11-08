package com.omnicrola.panoptes.export.xls.wrappers;

import com.omnicrola.panoptes.PanoptesException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by omnic on 11/7/2015.
 */
public class PanoptesWorkbook implements IWorkbook {
    private XSSFWorkbook xssfWorkbook;

    public PanoptesWorkbook(XSSFWorkbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    public XSSFWorkbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    @Override
    public IWorksheet getSheet(int sheetNumber) {
        return new PanoptesWorksheet(this.xssfWorkbook.getSheetAt(sheetNumber));
    }

    @Override
    public void write(OutputStream outputStream) {
        try {
            this.xssfWorkbook.write(outputStream);
        } catch (IOException e) {
            throw new PanoptesException(e);
        }
    }
}
