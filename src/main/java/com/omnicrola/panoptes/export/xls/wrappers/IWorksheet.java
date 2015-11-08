package com.omnicrola.panoptes.export.xls.wrappers;

/**
 * Created by omnic on 11/7/2015.
 */
public interface IWorksheet {
    IWorksheetRow getRow(int rowNumber);

    void insertRowsAt(int insertPosition, int rowsToInsert);

    void copyRow(int sourceRowIndex, int destinationRowIndex);
}
