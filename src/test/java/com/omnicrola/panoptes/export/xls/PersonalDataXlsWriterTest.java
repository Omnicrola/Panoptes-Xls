package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.PersonalData;
import com.omnicrola.panoptes.export.TemplateConfiguration;
import com.omnicrola.panoptes.export.TimesheetDate;
import com.omnicrola.panoptes.export.xls.wrappers.ICell;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheetRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Created by Eric on 2/21/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonalDataXlsWriterTest {
    @Mock
    private TemplateConfiguration templateConfiguration;

    @Mock
    IWorkbook workbook;

    @Test
    public void testWritesUsername() throws Exception {

        PersonalData personalData = createPersonalData();
        IWorksheet invoiceSheet = mock(IWorksheet.class);
        mockSheet(0);
        when(this.workbook.getSheet(1)).thenReturn(invoiceSheet);

        ICell nameCell = mockCellAt(invoiceSheet, 2, 'A');
        ICell addressCell = mockCellAt(invoiceSheet, 3, 'A');
        ICell payableToCell = mockCellAt(invoiceSheet, 22, 'A');

        String expectedAddress = personalData.address + "\n" +
                personalData.city + ", " + personalData.state + " " + personalData.zip + "\n" +
                personalData.phone + "\n" +
                personalData.email;
        String expectedFullName = personalData.firstName + " " + personalData.lastName;

        PersonalDataXlsWriter personalDataXlsWriter = new PersonalDataXlsWriter(this.templateConfiguration);
        TimesheetDate weekEnding = new TimesheetDate(new Date(1, 1, 1));

        personalDataXlsWriter.write(this.workbook, personalData, weekEnding);

        verify(nameCell).setValue(expectedFullName);
        verify(addressCell).setValue(expectedAddress);
        verify(payableToCell).setValue("Makes all checks payable to " + expectedFullName);
    }


    @Test
    public void testWritesNameOnFirstSheet() throws Exception {
        PersonalData personalData = createPersonalData();
        TimesheetDate weekEnding = new TimesheetDate(new Date(2006, 2, 2));

        String expectedFullName = personalData.firstName + " " + personalData.lastName;
        String expectedCompany = personalData.companyName;
        String expectedDate = weekEnding.toString();

        mockSheet(1);
        IWorksheet invoiceSheet = mock(IWorksheet.class);
        when(this.workbook.getSheet(0)).thenReturn(invoiceSheet);

        ICell nameCell = mockCellAt(invoiceSheet, 2, 'C');
        ICell companyCell = mockCellAt(invoiceSheet, 3, 'C');
        ICell weekEndingCell = mockCellAt(invoiceSheet, 4, 'C');

        PersonalDataXlsWriter personalDataXlsWriter = new PersonalDataXlsWriter(this.templateConfiguration);
        personalDataXlsWriter.write(this.workbook, personalData, weekEnding);

        verify(nameCell).setValue(expectedFullName);
        verify(companyCell).setValue(expectedCompany);
        verify(weekEndingCell).setValue(expectedDate);

    }

    private ICell mockCellAt(IWorksheet invoiceSheet, int row, char column) {
        ICell mockCell = mock(ICell.class);
        IWorksheetRow mockRow = mock(IWorksheetRow.class);
        when(invoiceSheet.getRow(row)).thenReturn(mockRow);
        when(mockRow.getCell(column)).thenReturn(mockCell);
        return mockCell;
    }

    private void mockSheet(int index) {
        IWorksheet worksheet = mock(IWorksheet.class);
        when(this.workbook.getSheet(index)).thenReturn(worksheet);
        IWorksheetRow worksheetRow = mock(IWorksheetRow.class);
        when(worksheet.getRow(anyInt())).thenReturn(worksheetRow);
        when(worksheetRow.getCell(anyChar())).thenReturn(mock(ICell.class));
    }

    private PersonalData createPersonalData() {
        PersonalData personalData = new PersonalData();
        personalData.firstName = "John";
        personalData.lastName = "Doe";
        personalData.companyName = "All The Bits Inc.";
        personalData.address = "123 Fake St";
        personalData.city = "AnyTown";
        personalData.state = "MI";
        personalData.zip = "12346";
        personalData.phone = "111-222-3333";
        personalData.email = "nobody@nowhere.com";

        return personalData;
    }

}