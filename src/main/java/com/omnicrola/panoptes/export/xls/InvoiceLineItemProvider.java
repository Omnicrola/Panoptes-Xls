package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.WorkStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omnic on 11/7/2015.
 */
public class InvoiceLineItemProvider {
    private static class LineItemAccumulator {}

    public Map<String, InvoiceRow> create(List<TimesheetLineItem> timesheetEntries) {
        HashMap<String, InvoiceRow> invoiceRows = new HashMap<>();
        for (TimesheetLineItem timeEntry : timesheetEntries) {
            InvoiceRow invoiceRow = getInvoiceLineItem(invoiceRows, timeEntry);
            invoiceRow.addTime(timeEntry.getWorkStatement().getProjectName(), timeEntry.getTotalTime());
        }
        return invoiceRows;
    }

    private InvoiceRow getInvoiceLineItem(HashMap<String, InvoiceRow> invoiceRows, TimesheetLineItem lineItem) {
        WorkStatement workStatement = lineItem.getWorkStatement();
        String projectCode = workStatement.getProjectCode();
        InvoiceRow invoiceRow = invoiceRows.get(projectCode);
        if (invoiceRow == null) {
            invoiceRow = new InvoiceRow(workStatement);
            invoiceRows.put(projectCode, invoiceRow);
        }
        return invoiceRow;
    }
}
