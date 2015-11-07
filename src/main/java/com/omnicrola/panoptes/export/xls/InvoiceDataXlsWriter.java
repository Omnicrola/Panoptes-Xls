package com.omnicrola.panoptes.export.xls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InvoiceDataXlsWriter {

    private static final int TEMPORARY_TEMPLATE_INDEX = 99;
    private final XlsUtilityToolbox toolbox;

    public InvoiceDataXlsWriter(XlsUtilityToolbox toolbox) {
        this.toolbox = toolbox;
    }

    private void insertRowData(XSSFRow newRow, InvoiceRow invoiceRow) {
        newRow.getCell(0).setCellValue(invoiceRow.getDescription());
        newRow.getCell(1).setCellValue(invoiceRow.getWorkStatement().getClient());
        newRow.getCell(2).setCellValue(invoiceRow.getWorkStatement().getSowCode());

        float totalValue = invoiceRow.getTotalValue();
        float billableRate = invoiceRow.getWorkStatement().getBillableRate();
        newRow.getCell(3).setCellValue(totalValue);
        newRow.getCell(4).setCellValue(billableRate);

        int rowNum = newRow.getRowNum() + 1;
        newRow.getCell(5).setCellFormula("E" + rowNum + "*D" + rowNum);
    }

    public void writeInvoiceData(XSSFWorkbook workbook, Map<String, InvoiceRow> sowToInvoiceMap) {
//        int totalNewRows = sowToInvoiceMap.size();
//
//        final int insertPoint = XlsExporter.INVOICE_ROW_INSERTION_POSITION;
//
//        XSSFRow templateRow = invoiceSheet.createRow(TEMPORARY_TEMPLATE_INDEX);
//        this.toolbox.copyRow(invoiceSheet.getRow(insertPoint), templateRow);
//        if (totalNewRows > 1) {
//            invoiceSheet.shiftRows(insertPoint, 100, totalNewRows - 1, true, true);
//        }
//
//        Collection<InvoiceRow> invoiceRows = sowToInvoiceMap.values();
//        int rowPosition = 0;
//        for (InvoiceRow invoiceRow : invoiceRows) {
//            XSSFRow newRow = invoiceSheet.createRow(rowPosition + insertPoint);
//            this.toolbox.copyRow(templateRow, newRow);
//            insertRowData(newRow, invoiceRow);
//            rowPosition++;
//        }
//
//        int end = insertPoint + totalNewRows;
//        String formula = "SUM(F17:F" + end + ")";
//
//        invoiceSheet.getRow(end).getCell(5).setCellFormula(formula);
//
//        invoiceSheet.removeRow(templateRow);
    }
}
