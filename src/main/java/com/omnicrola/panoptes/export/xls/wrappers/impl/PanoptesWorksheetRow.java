package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
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
        return new PanoptesCell(this.xssfRow.getCell(index));
    }

    private int convertLetterToColumnIndex(int columnLetter) {
        return columnLetter - 65;
    }

    @Override
    public int getRowNumber() {
        return this.xssfRow.getRowNum();
    }

    public XSSFRow getXssfRow() {
        return xssfRow;
    }
}
