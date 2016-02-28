package com.omnicrola.panoptes.export.xls;

import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;
import com.omnicrola.panoptes.export.xls.wrappers.impl.PanoptesWorkbook;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by omnic on 11/7/2015.
 */
public class XlsFileLoaderTest {
    @Test
    public void testLoadTemplate_functional() throws Exception {

        String actualTemplateFile = "/invoiceTemplate.xlsx";
        IWorkbook workbook = XlsFileLoader.loadTemplate(actualTemplateFile);
        PanoptesWorkbook panoptesWorkbook = assertIsOfType(PanoptesWorkbook.class, workbook);

        XSSFWorkbook actualWorkbook = panoptesWorkbook.getXssfWorkbook();
        assertNotNull(actualWorkbook);
        POIXMLProperties.CoreProperties properties = actualWorkbook.getProperties().getCoreProperties();


        Date expectedCreatedDate = new GregorianCalendar(2010, 3, 5, 10, 25, 13).getTime();
        Date expectedModifiedDate = new GregorianCalendar(2016, 0, 7, 9, 56, 54).getTime();

        assertEquals(expectedCreatedDate, properties.getCreated());
        assertEquals(expectedModifiedDate, properties.getModified());
        assertEquals("Carol", properties.getCreator());
        assertEquals("0", properties.getRevision());

    }


}