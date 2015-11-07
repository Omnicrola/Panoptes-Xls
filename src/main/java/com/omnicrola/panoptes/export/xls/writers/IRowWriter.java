package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.IExportDataContainer;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;

/**
 * Created by omnic on 11/7/2015.
 */
public interface IRowWriter {
    public void write(IWorksheet worksheet, IExportDataContainer dataContainer);
}
