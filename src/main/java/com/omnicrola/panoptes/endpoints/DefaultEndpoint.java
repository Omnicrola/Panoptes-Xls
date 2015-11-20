package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.data.WorkStatement;
import com.omnicrola.panoptes.export.XlsWriterFactory;
import com.omnicrola.panoptes.export.xls.StreamingXlsOutput;
import com.omnicrola.panoptes.export.xls.XlsWriter;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 10/25/2015.
 */
@Path("write")
public class DefaultEndpoint extends RestApiEndpoint {

    private static final String ERROR_MALFORMED_CONTAINER = "Malformed JSON : 'timeBlocks' and 'workStatements' cannot be null";
    private static final String ERROR_MALFORMED_TIMEBLOCK = "Malformed JSON : A 'timeblock' must have the following properties : 'project', 'card', 'role', 'startTime', 'endTime'";
    private static final String ERROR_MALFORMED_SOW = "Malformed JSON : A 'workStatement' must have the following properties : 'projectName', 'client', 'projectCode', 'sowCode', 'billableRate'";


    @GET
    public Response defaultAction() {
        System.out.println("GET");
        return Response.status(HttpServletResponse.SC_OK).entity("Hello Panoptes!").build();
    }

    @OPTIONS
    public void defaultAction(@Context HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Object createXls(@Context HttpServletResponse response, ExportDataContainer exportDataContainer) {
        try {
            Optional<String> dataIsNotProperlyFormed = dataIsProperlyFormed(exportDataContainer);
            if (dataIsNotProperlyFormed.isPresent()) {
                return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity(dataIsNotProperlyFormed.get()).build();
            } else {
                response.setHeader("Content-Type", "application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setStatus(HttpServletResponse.SC_OK);
                return constructXlsFromData(exportDataContainer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Optional<String> dataIsProperlyFormed(ExportDataContainer exportDataContainer) {
        boolean containerIsMissingFields = !modelHasRequiredFields(exportDataContainer);
        if (containerIsMissingFields) {
            return Optional.of(ERROR_MALFORMED_CONTAINER);
        }
        if (containsAnyMalformedObjects(exportDataContainer.timeblocks)) {
            return Optional.of(ERROR_MALFORMED_TIMEBLOCK);
        }
        if (containsAnyMalformedObjects(exportDataContainer.workStatements)) {
            return Optional.of(ERROR_MALFORMED_SOW);
        }

        return Optional.empty();
    }

    private boolean containsAnyMalformedObjects(List<?> objects) {
        for (Object singleObject : objects) {
            boolean isMissingFields = !modelHasRequiredFields(singleObject);
            if (isMissingFields) {
                return true;
            }
        }
        return false;
    }

    private StreamingOutput constructXlsFromData(ExportDataContainer exportDataContainer) {
        XlsWriter excelExporter = XlsWriterFactory.build();
        IWorkbook workbook = excelExporter.write(exportDataContainer);
        return new StreamingXlsOutput(workbook);
    }
}
