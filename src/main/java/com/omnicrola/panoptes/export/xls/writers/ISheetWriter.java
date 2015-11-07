package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.IExportDataContainer;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;

/**
 * Created by omnic on 11/7/2015.
 */
public interface ISheetWriter {
    public void write(IWorkbook workbook, IExportDataContainer dataContainer);
}
