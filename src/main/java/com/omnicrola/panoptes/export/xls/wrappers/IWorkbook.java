package com.omnicrola.panoptes.export.xls.wrappers;

import java.io.OutputStream;

/**
 * Created by omnic on 11/7/2015.
 */
public interface IWorkbook {
    IWorksheet getSheet(int sheetNumber);

    void write(OutputStream outputStream);
}
