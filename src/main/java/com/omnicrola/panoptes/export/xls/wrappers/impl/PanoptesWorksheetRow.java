package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * Created by omnic on 11/8/2015.
 */
public class PanoptesWorksheetRow implements IWorksheetRow {
    private XSSFRow xssfRow;

    public PanoptesWorksheetRow(XSSFRow xssfRow) {
        this.xssfRow = xssfRow;
    }

    @Override
    public ICell getCell(char columnLetter) {
        int index = convertLetterToColumnIndex(columnLetter);
        XSSFCell cell = this.xssfRow.getCell(index);
        System.err.println("Cell was null! row: " + xssfRow.getRowNum() + " cell:" + columnLetter + "(" + index + ")");
        return new PanoptesCell(cell);
    }

    private int convertLetterToColumnIndex(int columnLetter) {
        return columnLetter - 64;
    }

    @Override
    public int getRowNumber() {
        return this.xssfRow.getRowNum();
    }

    public XSSFRow getXssfRow() {
        return xssfRow;
    }
}
