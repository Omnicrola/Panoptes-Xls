package com.omnicrola.panoptes.export.xls;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TimesheetDataExporter {

    private static final int INDEX_OF_TOTALS_ROW = 9;
    private static final int TIMESHEET_PROJECT_SUM_COLUMN = 16;
    private final XlsUtilityToolbox toolbox;

    public TimesheetDataExporter(XlsUtilityToolbox toolbox) {
        this.toolbox = toolbox;
    }

    private void reWriteSumFormulas(XSSFWorkbook workbook, int numberOfNewRows) {
        XSSFSheet timesheet = workbook.getSheetAt(0);
        XSSFRow totalsRow = timesheet.getRow(INDEX_OF_TOTALS_ROW + numberOfNewRows);
        int start = ExcelExporter.TIMESHEET_ROW_INSERT_POSITION;
        int end = start + numberOfNewRows;
        for (int i = 6; i <= 15; i++) {
            reWriteVerticalSumFormula(totalsRow, i, start, end);
        }
    }

    private void reWriteVerticalSumFormula(XSSFRow totalsRow, int index, int start, int end) {
        char letter = ExcelExporter.ALPHANUMERIC[index];
        totalsRow.getCell(index).setCellFormula("SUM(" + letter + start + ":" + letter + end + ")");
    }

    private void setCellValue(XSSFRow sheetRow, int columnIndex, boolean value) {
        String translated = value ? "Y" : "N";
        setCellValue(sheetRow, columnIndex, translated);
    }

    private void setCellValue(XSSFRow row, int columnIndex, float floatValue, boolean clearIfZero) {
        XSSFCell cell = row.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK);
        if (clearIfZero && floatValue == 0.0) {
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        } else {
            cell.setCellValue(floatValue);
        }
    }

    private void setCellValue(XSSFRow row, int columnIndex, String stringValue) {
        XSSFCell cell = row.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK);
        cell.setCellValue(stringValue);
    }

    private void writeEmptyRow(XSSFRow sheetRow, ExportDataRow exportRow) {
        for (int i = 1; i <= 15; i++) {
            XSSFCell cell = sheetRow.getCell(i);
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        }
    }

    private void writeProjectSumFormula(XSSFRow lastRowOfProject, int projectSectionStart,
            int projectSectionEnd) {
        XSSFCell sumCell = lastRowOfProject.getCell(TIMESHEET_PROJECT_SUM_COLUMN);
        sumCell.setCellFormula("SUM(N" + projectSectionStart + ":N" + projectSectionEnd + ")");
    }

    public void writeTimesheetData(XSSFWorkbook workbook, List<ExportDataRow> exportList) {
        XSSFSheet timesheet = workbook.getSheetAt(ExcelExporter.SHEET_TIMESHEET);
        int insertPosition = ExcelExporter.TIMESHEET_ROW_INSERT_POSITION;
        int numberOfNewRows = exportList.size();

        timesheet.shiftRows(insertPosition, timesheet.getPhysicalNumberOfRows(), numberOfNewRows,
                true, true);
        XSSFRow templateRow = timesheet.getRow(ExcelExporter.TIMESHEET_ROW_INSERT_POSITION - 1);

        int currentRow = ExcelExporter.TIMESHEET_ROW_INSERT_POSITION;
        int projectSectionStart = currentRow;
        for (ExportDataRow exportRow : exportList) {
            XSSFRow sheetRow = timesheet.createRow(currentRow);
            this.toolbox.copyRow(templateRow, sheetRow);
            if (exportRow == ExportDataRow.EMPTY) {
                writeProjectSumFormula(timesheet.getRow(currentRow - 1), projectSectionStart,
                        currentRow);
                writeEmptyRow(sheetRow, exportRow);
                projectSectionStart = currentRow + 1;
            } else {
                writeTimesheetRow(sheetRow, exportRow);
            }
            currentRow++;
        }

        reWriteSumFormulas(workbook, numberOfNewRows);
    }

    private void writeTimesheetRow(XSSFRow sheetRow, ExportDataRow dataRow) {

        boolean billableToMenlo = dataRow.isBillableToMenlo();
        boolean billableToClient = dataRow.isBillableToClient();

        setCellValue(sheetRow, 1, dataRow.getWorkStatement().getClient());
        setCellValue(sheetRow, 2, dataRow.getWorkStatement().getProjectCode());
        setCellValue(sheetRow, 3, dataRow.getDescription());
        setCellValue(sheetRow, 4, billableToMenlo);
        setCellValue(sheetRow, 5, billableToClient);

        setCellValue(sheetRow, 6, dataRow.getTimeForDay(0), true);
        setCellValue(sheetRow, 7, dataRow.getTimeForDay(1), true);
        setCellValue(sheetRow, 8, dataRow.getTimeForDay(2), true);
        setCellValue(sheetRow, 9, dataRow.getTimeForDay(3), true);
        setCellValue(sheetRow, 10, dataRow.getTimeForDay(4), true);
        setCellValue(sheetRow, 11, dataRow.getTimeForDay(5), true);
        setCellValue(sheetRow, 12, dataRow.getTimeForDay(6), true);

        int rowIndex = sheetRow.getRowNum() + 1;
        sheetRow.getCell(13).setCellFormula("SUM(G" + rowIndex + ":M" + rowIndex + ")");
        sheetRow.getCell(14).setCellFormula("IF((E" + rowIndex + "=\"Y\"),N" + rowIndex + ",0)");
        sheetRow.getCell(15).setCellFormula("IF((F" + rowIndex + "=\"Y\"),N" + rowIndex + ",0)");

    }

}
