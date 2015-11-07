package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.IExportDataContainer;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;

/**
 * Created by omnic on 11/7/2015.
 */
public interface ICellWriter {
    public void write(IWorksheetRow worksheetRow);
}
