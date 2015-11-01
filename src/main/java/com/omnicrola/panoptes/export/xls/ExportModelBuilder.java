package com.omnicrola.panoptes.export.xls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.WorkStatement;

public class ExportModelBuilder {

    private ExportDataRowSorter exportDataRowSorter;

    public ExportModelBuilder() {
        this.exportDataRowSorter = new ExportDataRowSorter();
    }

    public List<ExportDataRow> buildDataRows(List<TimeData> timeblocks) {
        HashMap<String, WorkStatement> projectNameMap = new HashMap<>();
        HashMap<String, ExportDataRow> rowMap = new HashMap<>();

        for (TimeData timeData : timeblocks) {
            ExportDataRow exportDataRow = getRowForTimeBlock(rowMap, timeData, projectNameMap);
            exportDataRow.addTime(timeData.getDayOfWeek(), timeData.getElapsedTimeInHours());
        }

        return this.exportDataRowSorter.sort(new ArrayList<>(rowMap.values()));
    }

//    public HashMap<String, InvoiceRow> buildInvoiceRows(XSSFWorkbook workbook,
//                                                        List<ExportDataRow> exportList) {
//        HashMap<String, InvoiceRow> sowToRowMap = new HashMap<>();
//
//        for (ExportDataRow exportDataRow : exportList) {
//            if (exportDataRow != ExportDataRow.EMPTY) {
//                String projectCode = exportDataRow.getWorkStatement().getProjectCode();
//                String projectName = exportDataRow.getWorkStatement().getProjectName();
//
//                if (sowToRowMap.containsKey(projectCode)) {
//                    InvoiceRow invoiceRow = sowToRowMap.get(projectCode);
//                    invoiceRow.addTime(projectName, exportDataRow.getTotalTime());
//                } else {
//                    WorkStatement workStatement = exportDataRow.getWorkStatement();
//                    InvoiceRow invoiceRow = new InvoiceRow(workStatement);
//                    invoiceRow.addTime(projectName, exportDataRow.getTotalTime());
//                    sowToRowMap.put(projectCode, invoiceRow);
//                }
//            }
//        }
//        return sowToRowMap;
//    }


    private String getHashKey(TimeData timeblock) {
        String project = timeblock.getProject();
        String card = timeblock.getCard();
        String hashKey = project + card;
        return hashKey;

    }

    private ExportDataRow getRowForTimeBlock(HashMap<String, ExportDataRow> rowMap, TimeData timeBlock,
                                             HashMap<String, WorkStatement> projectNameMap) {
        String hashKey = getHashKey(timeBlock);
        if (rowMap.containsKey(hashKey)) {
            return rowMap.get(hashKey);
        }
        ExportDataRow exportDataRow = createNewRow(rowMap, timeBlock, projectNameMap);

        return exportDataRow;
    }

    private ExportDataRow createNewRow(HashMap<String, ExportDataRow> rowMap,
                                       TimeData timeBlock,
                                       HashMap<String, WorkStatement> projectNameMap) {
        String project = timeBlock.getProject();
        String card = timeBlock.getCard();
        String role = timeBlock.getRole();

        WorkStatement workStatement = WorkStatement.EMPTY;
        if (projectNameMap.containsKey(project)) {
            workStatement = projectNameMap.get(project);
        }
        ExportDataRow exportDataRow = new ExportDataRow(
                workStatement,
                project,
                role,
                card,
                true,
                true);
        rowMap.put(getHashKey(timeBlock), exportDataRow);
        return exportDataRow;
    }

    private void insertBlankRows(List<ExportDataRow> dataList) {
        List<Integer> list = new ArrayList<>();

        if (!dataList.isEmpty()) {
            String lastProject = dataList.get(0).getWorkStatement().getProjectName();

            for (ExportDataRow exportDataRow : dataList) {
                String projectName = exportDataRow.getWorkStatement().getProjectName();
                if (!projectName.equals(lastProject)) {
                    lastProject = projectName;
                    list.add(dataList.indexOf(exportDataRow));
                }
            }

            int arrayEnd = list.size() - 1;
            for (int i = arrayEnd; i >= 0; i--) {
                Integer index = list.get(i);
                dataList.add(index, ExportDataRow.EMPTY);
            }
        }
    }
}
