package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.data.PersonalData;
import com.omnicrola.panoptes.export.TimesheetDate;
import com.omnicrola.panoptes.export.TemplateConfiguration;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.IWorksheet;

public class PersonalDataXlsWriter {

    private TemplateConfiguration templateConfiguration;

    public PersonalDataXlsWriter(TemplateConfiguration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }

    public void write(IWorkbook workbook, PersonalData personalData, TimesheetDate weekEnding) {
        String firstName = personalData.firstName;
        String lastname = personalData.lastName;
        String fullName = firstName + " " + lastname;

        writeInvoice(workbook, personalData, fullName);
        IWorksheet timesheet = workbook.getSheet(0);
        timesheet.getRow(2).getCell('C').setValue(fullName);
        timesheet.getRow(3).getCell('C').setValue(personalData.companyName);
        timesheet.getRow(4).getCell('C').setValue(weekEnding.toString());
    }

    private void writeInvoice(IWorkbook workbook, PersonalData personalData, String fullName) {
        String address2 = personalData.city + ", " + personalData.state + " " + personalData.zip;
        String addressBlock = personalData.address + "\n" +
                address2 + "\n" +
                personalData.phone + "\n" +
                personalData.email;
        String payableTo = "Makes all checks payable to " + fullName;


        IWorksheet invoiceSheet = workbook.getSheet(1);
        invoiceSheet.getRow(2).getCell('A').setValue(fullName);
        invoiceSheet.getRow(3).getCell('A').setValue(addressBlock);
        invoiceSheet.getRow(22).getCell('A').setValue(payableTo);
    }

}
