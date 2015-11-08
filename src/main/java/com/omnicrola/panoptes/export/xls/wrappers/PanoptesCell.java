package com.omnicrola.panoptes.export.xls.wrappers;

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
        if(value == 0){
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
}
