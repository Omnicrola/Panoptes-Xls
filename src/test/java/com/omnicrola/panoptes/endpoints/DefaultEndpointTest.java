package com.omnicrola.panoptes.endpoints;

import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

import static com.omnicrola.test.util.TestUtil.assertAnnotationPresent;
import static org.junit.Assert.*;

/**
 * Created by omnic on 10/25/2015.
 */
public class DefaultEndpointTest extends EndpointTest{

    @Test
    public void testHasCorrectAnnotations() throws Exception {
        Class<DefaultEndpoint> defaultEndpointClass = DefaultEndpoint.class;
        assertApiPath(defaultEndpointClass, "build");

        Method defaultAction = defaultEndpointClass.getDeclaredMethod("defaultAction");
        assertAnnotationPresent(defaultAction, GET.class);
    }

    @Test
    public void testDefaultActionReturnsHelperText() throws Exception {
        DefaultEndpoint defaultEndpoint = new DefaultEndpoint();
        Response response = defaultEndpoint.defaultAction();
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("Hello Panoptes!", response.getEntity());
    }
    
}