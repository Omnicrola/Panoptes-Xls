package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.export.XlsWriterFactory;
import com.omnicrola.panoptes.export.xls.XlsWriter;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.message.internal.DataSourceProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 11/14/2015.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(XlsWriterFactory.class)
public class DefaultEndpointFunctionalTest extends JerseyTest {

    private static final String ERROR_MALFORMED_CONTAINER = "Malformed JSON : 'timeBlocks' and 'workStatements' cannot be null";
    private static final String ERROR_MALFORMED_TIMEBLOCK = "Malformed JSON : A 'timeblock' must have the following properties : 'project', 'card', 'role', 'startTime', 'endTime'";
    private static final String ERROR_MALFORMED_SOW = "Malformed JSON : A 'workStatement' must have the following properties : 'projectName', 'client', 'projectCode', 'sowCode', 'billableRate'";

    private XlsWriter mockXlsWriter;


    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(XlsWriterFactory.class);
        this.mockXlsWriter = mock(XlsWriter.class);
        when(XlsWriterFactory.build()).thenReturn(this.mockXlsWriter);
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(DefaultEndpoint.class);
    }

    @Test
    public void testWriteXls_ReturnsXlsSpreadsheet() throws Exception {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.workStatements = new ArrayList<>();
        exportDataContainer.timeblocks = new ArrayList<>();

        Response response = makeRequest(exportDataContainer);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        InputStream inputStream = response.readEntity(InputStream.class);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("testOutput.xls"));
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        fileOutputStream.close();

    }

//    @Test
//    public void testWriteXls_DeserializesDateCorrectly() throws Exception {
//
//
//        Date expectedStartTime = new GregorianCalendar(2015, 10, 11, 13, 1, 2).getTime();
//        Date expectedEndTime = new GregorianCalendar(2015, 9, 10, 14, 2, 3).getTime();
//        TimeData timeData = TimeDataTest.createTimeData("p", "c", "r", expectedStartTime, expectedEndTime);
//        ExportDataContainer exportDataContainer = createExportDataContainer(timeData);
//        Response response = makeRequest(exportDataContainer);
//
////        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
//        assertEquals("", response.readEntity(String.class));
//
//        ArgumentCaptor<ExportDataContainer> containerCaptor = ArgumentCaptor.forClass(ExportDataContainer.class);
//        verify(mockXlsWriter).write(containerCaptor.capture());
//        List<TimeData> timeblocks = containerCaptor.getValue().timeblocks;
//        assertEquals(1, timeblocks.size());
//        TimeData actualTimeData = timeblocks.get(0);
//
//        assertEquals(expectedStartTime, actualTimeData.getStartTime());
//        assertEquals(expectedEndTime, actualTimeData.getEndTime());
//    }

    @Test
    public void testWriteXls_NullTimeblocks_Returns400() throws Exception {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.workStatements = new ArrayList<>();
        exportDataContainer.timeblocks = null;
        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_CONTAINER);
    }

    @Test
    public void testWriteXls_NullWorkStatements_Returns400() throws Exception {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.timeblocks = new ArrayList<>();
        exportDataContainer.workStatements = null;
        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_CONTAINER);
    }

    @Test
    public void testWriteXls_Timeblock_NullProjectReturns400() throws Exception {
        TimeData timeData = TimeDataTest.createTimeData(null, "card", "role", new Date(), new Date());
        ExportDataContainer exportDataContainer = createExportDataContainer(timeData);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_TIMEBLOCK);
    }

    @Test
    public void testWriteXls_Timeblock_NullCardReturns400() throws Exception {
        TimeData timeData = TimeDataTest.createTimeData("project", null, "role", new Date(), new Date());
        ExportDataContainer exportDataContainer = createExportDataContainer(timeData);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_TIMEBLOCK);
    }

    @Test
    public void testWriteXls_Timeblock_NullRoleReturns400() throws Exception {
        TimeData timeData = TimeDataTest.createTimeData("project", "card", null, new Date(), new Date());
        ExportDataContainer exportDataContainer = createExportDataContainer(timeData);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_TIMEBLOCK);
    }

    @Test
    public void testWriteXls_Timeblock_NullStartDateReturns400() throws Exception {
        TimeData timeData = TimeDataTest.createTimeData("project", "card", "role", null, new Date());
        ExportDataContainer exportDataContainer = createExportDataContainer(timeData);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_TIMEBLOCK);
    }

    @Test
    public void testWriteXls_Timeblock_NullEndDateReturns400() throws Exception {
        TimeData timeData = createTimeData("project", "card", "role", new Date(), null);
        ExportDataContainer exportDataContainer = createExportDataContainer(timeData);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_TIMEBLOCK);
    }

    private TimeData createTimeData(String project, String card, String role, Date startDate, Date endDate) {
        TimeData timeData = new TimeData();
        timeData.project = project;
        timeData.card = card;
        timeData.role = role;
        timeData.startTime = (startDate == null) ? "" : TimeDataTest.DATE_FORMAT.format(startDate);
        timeData.endTime = (endDate == null) ? "" : TimeDataTest.DATE_FORMAT.format(endDate);
        return timeData;
    }
    @Test
    public void testWriteXls_WorkStatement_NullProjectNameReturns400() throws Exception {
        WorkStatement workStatement = new WorkStatement(null, "client", "projectCode", "sowCode", 23324.45f);
        ExportDataContainer exportDataContainer = createExportDataContainer(workStatement);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_SOW);
    }

    @Test
    public void testWriteXls_WorkStatement_NullClientReturns400() throws Exception {
        WorkStatement workStatement = new WorkStatement("projectName", null, "projectCode", "sowCode", 23324.45f);
        ExportDataContainer exportDataContainer = createExportDataContainer(workStatement);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_SOW);
    }

    @Test
    public void testWriteXls_WorkStatement_NullProjectCodeReturns400() throws Exception {
        WorkStatement workStatement = new WorkStatement("projectName", "client", null, "sowCode", 23324.45f);
        ExportDataContainer exportDataContainer = createExportDataContainer(workStatement);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_SOW);
    }

    @Test
    public void testWriteXls_WorkStatement_NullSowCodeReturns400() throws Exception {
        WorkStatement workStatement = new WorkStatement("projectName", "client", "projectCode", null, 23324.45f);
        ExportDataContainer exportDataContainer = createExportDataContainer(workStatement);

        assertRequestReturns400(exportDataContainer, ERROR_MALFORMED_SOW);
    }

    private ExportDataContainer createExportDataContainer(TimeData timeData) {
        ExportDataContainer exportDataContainer = new ExportDataContainer();

        exportDataContainer.timeblocks = Arrays.asList(timeData);
        exportDataContainer.workStatements = new ArrayList<>();
        return exportDataContainer;
    }

    private ExportDataContainer createExportDataContainer(WorkStatement workStatement) {
        ExportDataContainer exportDataContainer = new ExportDataContainer();

        exportDataContainer.timeblocks = new ArrayList<>();
        exportDataContainer.workStatements = Arrays.asList(workStatement);
        return exportDataContainer;
    }

    private void assertRequestReturns400(ExportDataContainer dataContainer, String expectedResponse) {
        Response response = makeRequest(dataContainer);
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
        assertEquals(expectedResponse, response.readEntity(String.class));
        Mockito.verifyZeroInteractions(this.mockXlsWriter);
    }


    private Response makeRequest(ExportDataContainer dataContainer) {
        return target()
                .path("write")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dataContainer, MediaType.APPLICATION_JSON));
    }

}
