package com.omnicrola.panoptes.export.xls;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.omnicrola.panoptes.PanoptesException;
import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.export.DateWrapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExporter   {

    private static final String TEMPLATE_FILENAME = "invoiceTemplate.xlsx";

    static final char[] ALPHANUMERIC = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    static final int TIMESHEET_ROW_INSERT_POSITION = 8;
    static final int INVOICE_ROW_INSERTION_POSITION = 16;
    static final int SHEET_TIMESHEET = 0;
    static final int SHEET_INVOICE = 1;

    private  ExportModelBuilder exportModelBuilder;
    private  PersonalDataExporter personalDataExporter;
    private  TimesheetDataExporter dataExporter;
    private  InvoiceExporter invoiceExporter;

    public XSSFWorkbook build(ExportDataContainer exportDataContainer) {
        return XlsFileLoader.loadTemplate(TEMPLATE_FILENAME);
    }

//    public ExcelExporter( ExportModelBuilder exportModelBuilder,
//            PersonalDataExporter personalDataWriter, TimesheetDataExporter dataExporter,
//            InvoiceExporter invoiceExporter) {
//        this.exportModelBuilder = exportModelBuilder;
//        this.personalDataExporter = personalDataWriter;
//        this.dataExporter = dataExporter;
//        this.invoiceExporter = invoiceExporter;
//    }
//
//    private void actuallySaveFile(OutputStream outputStream, XSSFWorkbook workbook)   {
//        try {
//            workbook.write(outputStream);
//
//        } catch (IOException e) {
//            throw new PanoptesException(e);
//        }
//    }
//

//
//    private void refreshFormulas(XSSFWorkbook workbook) {
//        XSSFFormulaEvaluator formulaEvaluator = workbook.getCreationHelper()
//                .createFormulaEvaluator();
//        Iterator<XSSFSheet> sheetIterator = workbook.iterator();
//        while (sheetIterator.hasNext()) {
//            Iterator<Row> rowIterator = sheetIterator.next().iterator();
//            while (rowIterator.hasNext()) {
//                Iterator<Cell> cellIterator = rowIterator.next().iterator();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    formulaEvaluator.evaluateFormulaCell(cell);
//                }
//            }
//        }
//    }
//
//
//    public void writeDataToFile(File destination, DateWrapper weekEnding, TimeblockSet timeblocks)
//            throws PanoptesException {
//
//        XSSFWorkbook workbook = loadInvoiceTemplate();
//        List<ExportDataRow> exportList = this.exportModelBuilder.buildDataRows(timeblocks);
//
//        if (exportList.isEmpty()) {
//            throw new PanoptesException("No data to save!");
//        } else {
//            HashMap<String, InvoiceRow> invoiceRows = this.exportModelBuilder.buildInvoiceRows(
//                    workbook, exportList);
//
//            this.personalDataExporter.writePersonalData(workbook,
//                    this.controller.getPersonalData(), weekEnding);
//            this.dataExporter.writeTimesheetData(workbook, exportList);
//            this.invoiceExporter.writeInvoiceData(workbook.getSheetAt(SHEET_INVOICE), invoiceRows);
//
//            refreshFormulas(workbook);
//            actuallySaveFile(destination, workbook);
//        }
//    }

}
