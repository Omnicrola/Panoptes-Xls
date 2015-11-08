package com.omnicrola.panoptes.export.xls.wrappers;

import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * Created by omnic on 11/8/2015.
 */
public class PanoptesWorksheetRow implements IWorksheetRow{
    private XSSFRow xssfRow;

    public PanoptesWorksheetRow(XSSFRow xssfRow) {
        this.xssfRow = xssfRow;
    }

    @Override
    public ICell getCell(char columnLetter) {
        int index = convertLetterToColumnIndex(columnLetter);
        return new PanoptesCell(this.xssfRow.getCell(index));
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
