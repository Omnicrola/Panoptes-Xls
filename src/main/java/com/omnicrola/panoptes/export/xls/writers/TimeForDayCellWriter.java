package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.IExportDataContainer;
import com.omnicrola.panoptes.export.xls.TimesheetLineItem;
import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;

/**
 * Created by omnic on 11/7/2015.
 */
public class TimeForDayCellWriter implements ICellWriter {
    private final char column;
    private final int rowNumber;
    private final TimesheetLineItem timesheetLineItem;
    private int dayToWrite;

    public TimeForDayCellWriter(char column, int rowNumber, TimesheetLineItem timesheetLineItem, int dayToWrite) {
        this.column = column;
        this.rowNumber = rowNumber;
        this.timesheetLineItem = timesheetLineItem;
        this.dayToWrite = dayToWrite;
    }

    @Override
    public void write(IWorksheetRow worksheetRow) {
        ICell cell = worksheetRow.getCell(this.column, this.rowNumber);
        float totalTimeForDay = this.timesheetLineItem.getTimeForDay(this.dayToWrite);
        cell.setValue(totalTimeForDay);
    }
}
