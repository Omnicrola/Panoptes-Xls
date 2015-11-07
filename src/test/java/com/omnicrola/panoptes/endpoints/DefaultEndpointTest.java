package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.export.xls.StreamingXlsOutput;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.lang.reflect.Method;

import static com.omnicrola.test.util.TestUtil.assertAnnotationPresent;
import static com.omnicrola.test.util.TestUtil.assertIsOfType;
import static org.junit.Assert.*;

/**
 * Created by omnic on 10/25/2015.
 */
public class DefaultEndpointTest extends EndpointTest {

    @Test
    public void testHasCorrectAnnotations() throws Exception {
        Class<DefaultEndpoint> defaultEndpointClass = DefaultEndpoint.class;
        assertApiPath(defaultEndpointClass, "build");

        Method defaultAction = defaultEndpointClass.getDeclaredMethod("defaultAction");
        assertAnnotationPresent(defaultAction, GET.class);

        Method postAction = defaultEndpointClass.getDeclaredMethod("exportXls");
        assertAnnotationPresent(postAction, POST.class);
    }

    @Test
    public void testExportsAnXls() throws Exception {
        DefaultEndpoint defaultEndpoint = new DefaultEndpoint();
        StreamingOutput streamingOutput = defaultEndpoint.exportXls();
        StreamingXlsOutput streamingXlsOutput = assertIsOfType(StreamingXlsOutput.class, streamingOutput);
        assertIsOfType(XSSFWorkbook.class, streamingXlsOutput.getWorkbook());
    }

    @Test
    public void testDefaultActionReturnsHelperText() throws Exception {
        DefaultEndpoint defaultEndpoint = new DefaultEndpoint();
        Response response = defaultEndpoint.defaultAction();
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("Hello Panoptes!", response.getEntity());
    }

}