package com.omnicrola.panoptes.export.xls.wrappers.impl;

import com.omnicrola.panoptes.export.xls.XssfUtilities;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * Created by omnic on 11/7/2015.
 */
public class PanoptesWorksheet implements IWorksheet {
    private XSSFSheet xssfSheet;

    public PanoptesWorksheet(XSSFSheet xssfSheet) {
        this.xssfSheet = xssfSheet;
    }

    public XSSFSheet getXssfSheet() {
        return xssfSheet;
    }

    @Override
    public IWorksheetRow getRow(int rowNumber) {
        XSSFRow row = this.xssfSheet.getRow(rowNumber);
        if (row == null) {
            row = this.xssfSheet.createRow(rowNumber);
        }
        return new PanoptesWorksheetRow(row);
    }

    @Override
    public void insertRowsAt(int insertPosition, int rowsToInsert) {
        if (rowsToInsert == 0) {
            return;
        }
        this.xssfSheet.shiftRows(insertPosition, xssfSheet.getPhysicalNumberOfRows(), rowsToInsert, true, true);
    }

    @Override
    public void copyRow(int sourceRowIndex, int destinationRowIndex) {
        XSSFRow sourceRow = this.xssfSheet.getRow(sourceRowIndex);
        XSSFRow destinationRow = this.xssfSheet.getRow(destinationRowIndex);
        XssfUtilities.copyRow(sourceRow, destinationRow);
    }
}
