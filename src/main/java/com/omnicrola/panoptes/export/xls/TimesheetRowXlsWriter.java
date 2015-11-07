package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * Created by omnic on 11/7/2015.
 */
public class TimesheetRowXlsWriter {
    public void write(IWorksheetRow worksheetRow, TimesheetLineItem lineItem) {

    }

//    private void writeTimesheetRow(XSSFRow sheetRow, TimesheetLineItem dataRow) {
//
//        boolean billableToMenlo = dataRow.isBillableToMenlo();
//        boolean billableToClient = dataRow.isBillableToClient();
//
//        setCellValue(sheetRow, 1, dataRow.getWorkStatement().getClient());
//        setCellValue(sheetRow, 2, dataRow.getWorkStatement().getProjectCode());
//        setCellValue(sheetRow, 3, dataRow.getDescription());
//        setCellValue(sheetRow, 4, billableToMenlo);
//        setCellValue(sheetRow, 5, billableToClient);
//
//        setCellValue(sheetRow, 6, dataRow.getTimeForDay(0), true);
//        setCellValue(sheetRow, 7, dataRow.getTimeForDay(1), true);
//        setCellValue(sheetRow, 8, dataRow.getTimeForDay(2), true);
//        setCellValue(sheetRow, 9, dataRow.getTimeForDay(3), true);
//        setCellValue(sheetRow, 10, dataRow.getTimeForDay(4), true);
//        setCellValue(sheetRow, 11, dataRow.getTimeForDay(5), true);
//        setCellValue(sheetRow, 12, dataRow.getTimeForDay(6), true);
//
//        int rowIndex = sheetRow.getRowNum() + 1;
//        sheetRow.getCell(13).setCellFormula("SUM(G" + rowIndex + ":M" + rowIndex + ")");
//        sheetRow.getCell(14).setCellFormula("IF((E" + rowIndex + "=\"Y\"),N" + rowIndex + ",0)");
//        sheetRow.getCell(15).setCellFormula("IF((F" + rowIndex + "=\"Y\"),N" + rowIndex + ",0)");
//
//    }
}
