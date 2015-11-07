package com.omnicrola.panoptes.export.xls.wrappers;

/**
 * Created by omnic on 11/7/2015.
 */
public interface IWorksheetRow {
    ICell getCell(char column);

    int getRowNumber();
}
