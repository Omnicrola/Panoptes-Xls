package com.omnicrola.panoptes;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.data.PersonalData;
import com.omnicrola.panoptes.export.TimesheetDate;
import com.omnicrola.panoptes.export.XlsWriterFactory;
import com.omnicrola.panoptes.export.xls.XlsWriter;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Eric on 2/21/2016.
 */
public class PanoptesFunctionalTest {

    @Test
    public void testExportsXlsFile() throws Exception {
        XlsWriter xlsWriter = XlsWriterFactory.build();
        ExportDataContainer exportDataContainer = getExportDataContainer();
        IWorkbook workbook = xlsWriter.write(exportDataContainer);
        workbook.write(new FileOutputStream(new File("FunctionalTest.xls")));
    }

    private ExportDataContainer getExportDataContainer() {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.weekEnding  = new TimesheetDate(new Date(2016,2,2));
        exportDataContainer.personalData = createPersonalData();
        return exportDataContainer;
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
