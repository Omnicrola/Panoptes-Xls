package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * Created by omnic on 11/7/2015.
 */
public class TimesheetRowXlsWriter {
    public void write(IWorksheetRow worksheetRow, TimesheetLineItem lineItem) {


        boolean billableToMenlo = lineItem.isBillableToMenlo();
        boolean billableToClient = lineItem.isBillableToClient();

        setCellValue(worksheetRow, 'B', lineItem.getWorkStatement().getClient());
        setCellValue(worksheetRow, 'C', lineItem.getWorkStatement().getProjectCode());
        setCellValue(worksheetRow, 'D', lineItem.getDescription());
        setCellValue(worksheetRow, 'E', billableToMenlo);
        setCellValue(worksheetRow, 'F', billableToClient);

        setCellValue(worksheetRow, 'G', lineItem.getTimeForDay(0));
        setCellValue(worksheetRow, 'H', lineItem.getTimeForDay(1));
        setCellValue(worksheetRow, 'I', lineItem.getTimeForDay(2));
        setCellValue(worksheetRow, 'J', lineItem.getTimeForDay(3));
        setCellValue(worksheetRow, 'K', lineItem.getTimeForDay(4));
        setCellValue(worksheetRow, 'L', lineItem.getTimeForDay(5));
        setCellValue(worksheetRow, 'M', lineItem.getTimeForDay(6));

        int rowIndex = worksheetRow.getRowNumber() + 1;
        worksheetRow.getCell('N').setFormula("SUM(G" + rowIndex + ":M" + rowIndex + ")");
        worksheetRow.getCell('O').setFormula("IF((E" + rowIndex + "=\"Y\"),N" + rowIndex + ",0)");
        worksheetRow.getCell('P').setFormula("IF((F" + rowIndex + "=\"Y\"),N" + rowIndex + ",0)");

    }

    private void setCellValue(IWorksheetRow worksheetRow, char column, String value) {
        worksheetRow.getCell(column).setValue(value);
    }

    private void setCellValue(IWorksheetRow worksheetRow, char column, boolean value) {
        worksheetRow.getCell(column).setValue(value ? "Y" : "N");
    }

    private void setCellValue(IWorksheetRow worksheetRow, char column, float value) {
        ICell cell = worksheetRow.getCell(column);
        if (value == 0) {
            cell.clear();
        } else {
            cell.setValue(value);
        }
    }
}
