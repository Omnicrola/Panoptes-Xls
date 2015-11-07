package com.omnicrola.panoptes.export.xls.writers;

import com.omnicrola.panoptes.export.xls.TimesheetLineItem;
import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;

/**
 * Created by omnic on 11/7/2015.
 */
public class BillableItemCellWriter implements ICellWriter {
    private TimesheetLineItem timesheetLineItem;
    private int rowNumber;

    public BillableItemCellWriter(TimesheetLineItem timesheetLineItem, int rowNumber) {
        this.timesheetLineItem = timesheetLineItem;
        this.rowNumber = rowNumber;
    }

    @Override
    public void write(IWorksheetRow worksheetRow) {
        boolean billableToMenlo = this.timesheetLineItem.isBillableToMenlo();
        boolean billableToClient = this.timesheetLineItem.isBillableToClient();

        setValue(billableToMenlo, worksheetRow.getCell('E', rowNumber));
        setValue(billableToClient, worksheetRow.getCell('F', rowNumber));
    }

    private void setValue(boolean yesOrNo, ICell cell) {
        cell.setValue(yesOrNo ? "Y" : "N");
    }
}
