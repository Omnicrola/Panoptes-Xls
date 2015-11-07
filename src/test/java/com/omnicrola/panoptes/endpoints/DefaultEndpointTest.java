package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.export.XlsBuilderFactory;
import com.omnicrola.panoptes.export.xls.ExcelExporter;
import com.omnicrola.panoptes.export.xls.StreamingXlsOutput;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import static com.omnicrola.test.util.TestUtil.assertAnnotationPresent;
import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by omnic on 10/25/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(XlsBuilderFactory.class)
public class DefaultEndpointTest extends EndpointTest {

    @Mock
    ExcelExporter mockXlsBuilder;
    @Mock
    XSSFWorkbook mockWorkbook;

    @Test
    public void testHasCorrectAnnotations() throws Exception {
        Class<DefaultEndpoint> defaultEndpointClass = DefaultEndpoint.class;
        assertApiPath(defaultEndpointClass, "build");

        Method defaultAction = defaultEndpointClass.getDeclaredMethod("defaultAction");
        assertAnnotationPresent(defaultAction, GET.class);

        Method postAction = defaultEndpointClass.getDeclaredMethod("createXls");
        assertAnnotationPresent(postAction, POST.class);
    }

    @Test
    public void testExportsAnXls() throws Exception {
        PowerMockito.mockStatic(XlsBuilderFactory.class);

        ExportDataContainer exportDataContainer = new ExportDataContainer();
        exportDataContainer.workStatements = Collections.unmodifiableList(new ArrayList<>());
        exportDataContainer.timeblocks = Collections.unmodifiableList(new ArrayList<>());

        when(XlsBuilderFactory.build()).thenReturn(this.mockXlsBuilder);
        when(this.mockXlsBuilder.build(exportDataContainer)).thenReturn(this.mockWorkbook);

        DefaultEndpoint defaultEndpoint = new DefaultEndpoint();
        StreamingOutput streamingOutput = defaultEndpoint.createXls(exportDataContainer);

        StreamingXlsOutput streamingXlsOutput = assertIsOfType(StreamingXlsOutput.class, streamingOutput);
        assertSame(this.mockWorkbook, streamingXlsOutput.getWorkbook());
    }

    @Test
    public void testDefaultActionReturnsHelperText() throws Exception {
        DefaultEndpoint defaultEndpoint = new DefaultEndpoint();
        Response response = defaultEndpoint.defaultAction();
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("Hello Panoptes!", response.getEntity());
    }

}