package com.omnicrola.panoptes.endpoints;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.omnicrola.test.util.TestUtil.assertAnnotationPresent;
import static com.omnicrola.test.util.TestUtil.assertClassHasAnnotation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by Eric on 10/10/2015.
 */
public abstract class EndpointTest {

    protected static final String ACCESS_CONTROL_HEADER = "Access-Control-Allow-Origin";

    protected static void verifyResponseHeadersWhereSet(HttpServletResponse mockServlet) {
        verify(mockServlet).setHeader("Content-Type", "application/json");
        verify(mockServlet).setHeader("Content-Type", "application/json");
    }

    protected static void assertResponseHasCrossOriginHeader(Response response) {
        Optional<Map.Entry<String, List<String>>> header = response.getStringHeaders()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equals(ACCESS_CONTROL_HEADER))
                .findFirst();

        assertTrue(ACCESS_CONTROL_HEADER + " header was not found", header.isPresent());
        List<String> allowedDomains = header.get().getValue();
        assertEquals(1, allowedDomains.size());
        assertEquals("*", allowedDomains.get(0));
    }

    public static void assertMethodProducesJson(Method method) {
        Produces producesAnnotation = assertAnnotationPresent(method, Produces.class);
        String[] productionTypes = producesAnnotation.value();
        assertEquals(1, productionTypes.length);
        assertEquals(MediaType.APPLICATION_JSON, productionTypes[0]);
    }

    public static void assertMethodConsumesJson(Method method) {
        Consumes producesAnnotation = assertAnnotationPresent(method, Consumes.class);
        String[] productionTypes = producesAnnotation.value();
        assertEquals(1, productionTypes.length);
        assertEquals(MediaType.APPLICATION_JSON, productionTypes[0]);
    }

    public static void assertApiPath(Class<?> endpointClass, String expectedPath) {
        Path pathAnnotation = assertClassHasAnnotation(Path.class, endpointClass);
        assertEquals(expectedPath, pathAnnotation.value());
    }

}
