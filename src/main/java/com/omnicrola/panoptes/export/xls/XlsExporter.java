package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.ExportDataContainer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


public class XlsExporter {

    private static final String TEMPLATE_FILENAME = "invoiceTemplate.xlsx";

    static final char[] ALPHANUMERIC = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    static final int TIMESHEET_ROW_INSERT_POSITION = 8;
    static final int INVOICE_ROW_INSERTION_POSITION = 16;
    static final int SHEET_TIMESHEET = 0;
    static final int SHEET_INVOICE = 1;

    private  TimesheetLineItemProvider timesheetLineItemProvider;
    private  InvoiceLineItemProvider invoiceLineItemProvider;
    private PersonalDataXlsWriter personalDataWriter;
    private TimesheetDataXlsWriter timesheetDataXlsWriter;
    private InvoiceDataXlsWriter invoiceDataXlsWriter;

    public XlsExporter(TimesheetLineItemProvider timesheetLineItemProvider, InvoiceLineItemProvider invoiceLineItemProvider,
                       PersonalDataXlsWriter personalDataWriter, TimesheetDataXlsWriter timesheetDataXlsWriter, InvoiceDataXlsWriter invoiceDataXlsWriter) {
        this.timesheetLineItemProvider = timesheetLineItemProvider;
        this.invoiceLineItemProvider = invoiceLineItemProvider;
        this.personalDataWriter = personalDataWriter;
        this.timesheetDataXlsWriter = timesheetDataXlsWriter;
        this.invoiceDataXlsWriter = invoiceDataXlsWriter;
    }

    public XSSFWorkbook build(ExportDataContainer exportDataContainer) {
        XSSFWorkbook workbook = XlsFileLoader.loadTemplate(TEMPLATE_FILENAME);
        List<TimesheetLineItem> timesheetRows = this.timesheetLineItemProvider.buildDataRows(exportDataContainer.timeblocks);
        Map<String, InvoiceRow> invoiceRows = this.invoiceLineItemProvider.create(timesheetRows);
        this.timesheetDataXlsWriter.writeTimesheetData(workbook, timesheetRows);
        this.invoiceDataXlsWriter.writeInvoiceData(workbook, invoiceRows);
        return workbook;
    }

    public TimesheetLineItemProvider getTimesheetLineItemProvider() {
        return timesheetLineItemProvider;
    }

    public InvoiceLineItemProvider getInvoiceLineItemProvider() {
        return invoiceLineItemProvider;
    }

    public PersonalDataXlsWriter getPersonalDataWriter() {
        return personalDataWriter;
    }

    public TimesheetDataXlsWriter getTimesheetDataXlsWriter() {
        return timesheetDataXlsWriter;
    }

    public InvoiceDataXlsWriter getInvoiceDataXlsWriter() {
        return invoiceDataXlsWriter;
    }


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

}
