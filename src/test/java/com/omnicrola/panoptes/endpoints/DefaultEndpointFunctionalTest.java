package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.export.XlsWriterFactory;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.message.internal.DataSourceProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by omnic on 11/14/2015.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(XlsWriterFactory.class)
public class DefaultEndpointFunctionalTest extends JerseyTest {

    private XlsWriterFactory mockXlsWriterFactory;

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(DataSourceProvider.class);
        this.mockXlsWriterFactory = PowerMockito.mock(XlsWriterFactory.class);
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

    @Test
    public void testWriteXls_NullTimeblocks_Returns400() throws Exception {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.workStatements = new ArrayList<>();
        exportDataContainer.timeblocks = null;
        assertRequestReturns400(exportDataContainer);
    }

    @Test
    public void testWriteXls_NullWorkStatements_Returns400() throws Exception {
        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.timeblocks = new ArrayList<>();
        exportDataContainer.workStatements = null;
        assertRequestReturns400(exportDataContainer);
    }

    private void assertRequestReturns400(ExportDataContainer dataContainer) {
        Response response = makeRequest(dataContainer);
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
        Mockito.verifyZeroInteractions(this.mockXlsWriterFactory);
    }


    private Response makeRequest(ExportDataContainer dataContainer) {
        return target()
                .path("write")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dataContainer, MediaType.APPLICATION_JSON));
    }

}
