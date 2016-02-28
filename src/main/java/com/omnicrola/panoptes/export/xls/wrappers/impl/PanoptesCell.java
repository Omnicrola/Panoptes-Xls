package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * Created by omnic on 11/7/2015.
 */
public class PanoptesCell implements ICell {

    private XSSFCell xssfCell;

    public PanoptesCell(XSSFCell xssfCell) {
        this.xssfCell = xssfCell;
    }

    public XSSFCell getXssfCell() {
        return xssfCell;
    }

    @Override
    public void setValue(String value) {
        this.xssfCell.setCellValue(value);
    }

    @Override
    public void setValue(float value) {
        if (value == 0) {
            clear();
        } else {
            this.xssfCell.setCellValue(value);
        }
    }

    @Override
    public void clear() {
        this.xssfCell.setCellType(Cell.CELL_TYPE_BLANK);
    }

    @Override
    public void setFormula(String formula) {

    }

    @Override
    public String toString() {
        String rawValue = (this.xssfCell == null) ? "null" : getString();
        int rowIndex = (this.xssfCell == null) ? -1 : this.xssfCell.getRowIndex();
        int columnIndex = (this.xssfCell == null) ? -1 : this.xssfCell.getColumnIndex();
        return "PanoptesCell{" +
                "(" + rowIndex + ", " + columnIndex + ") " +
                rawValue +
                '}';
    }

    private String getString() {
        int cellType = this.xssfCell.getCellType();
        if (cellType == XSSFCell.CELL_TYPE_STRING) {
            return this.xssfCell.getStringCellValue();
        } else if (cellType == XSSFCell.CELL_TYPE_FORMULA) {
            return this.xssfCell.getCellFormula();
        } else if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(this.xssfCell.getNumericCellValue());
        } else if (cellType == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (cellType == XSSFCell.CELL_TYPE_BLANK) {
            return "[BLANK]";
        } else {
            return " ???";
        }
    }
}
