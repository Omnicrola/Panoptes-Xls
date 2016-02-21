package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.TimeData;
import com.omnicrola.panoptes.data.WorkStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimesheetLineItemProvider {

    public List<TimesheetLineItem> buildDataRows(List<TimeData> timeblocks) {
        HashMap<String, WorkStatement> projectNameMap = new HashMap<>();
        HashMap<String, TimesheetLineItem> rowMap = new HashMap<>();

        for (TimeData timeData : timeblocks) {
            TimesheetLineItem exportDataRow = getRowForTimeBlock(rowMap, timeData, projectNameMap);
            exportDataRow.addTime(timeData.getDayOfWeek(), timeData.getElapsedTimeInHours());
        }
        return new ArrayList<>(rowMap.values());
    }

    private String getHashKey(TimeData timeblock) {
        String project = timeblock.getProject();
        String card = timeblock.getCard();
        String hashKey = project + card;
        return hashKey;

    }

    private TimesheetLineItem getRowForTimeBlock(HashMap<String, TimesheetLineItem> rowMap, TimeData timeBlock,
                                             HashMap<String, WorkStatement> projectNameMap) {
        String hashKey = getHashKey(timeBlock);
        if (rowMap.containsKey(hashKey)) {
            return rowMap.get(hashKey);
        }
        TimesheetLineItem exportDataRow = createNewRow(rowMap, timeBlock, projectNameMap);

        return exportDataRow;
    }

    private TimesheetLineItem createNewRow(HashMap<String, TimesheetLineItem> rowMap,
                                       TimeData timeBlock,
                                       HashMap<String, WorkStatement> projectNameMap) {
        String project = timeBlock.getProject();
        String card = timeBlock.getCard();
        String role = timeBlock.getRole();

        WorkStatement workStatement = WorkStatement.EMPTY;
        if (projectNameMap.containsKey(project)) {
            workStatement = projectNameMap.get(project);
        }
        TimesheetLineItem exportDataRow = new TimesheetLineItem(
                workStatement,
                project,
                role,
                card,
                true,
                true);
        rowMap.put(getHashKey(timeBlock), exportDataRow);
        return exportDataRow;
    }

    public List<TimesheetLineItem> insertBlankRows(List<TimesheetLineItem> dataList) {
        List<Integer> indexes = new ArrayList<>();

        if (!dataList.isEmpty()) {
            String lastProject = dataList.get(0).getWorkStatement().getProjectName();

            for (TimesheetLineItem exportDataRow : dataList) {
                String projectName = exportDataRow.getWorkStatement().getProjectName();
                if (!projectName.equals(lastProject)) {
                    lastProject = projectName;
                    indexes.add(dataList.indexOf(exportDataRow));
                }
            }

            int arrayEnd = indexes.size() - 1;
            for (int i = arrayEnd; i >= 0; i--) {
                Integer index = indexes.get(i);
                dataList.add(index, TimesheetLineItem.EMPTY);
            }
        }
        return dataList;
    }
}
