package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.export.XlsWriterFactory;
import com.omnicrola.panoptes.export.xls.StreamingXlsOutput;
import com.omnicrola.panoptes.export.xls.XlsWriter;
import com.omnicrola.panoptes.export.xls.wrappers.IWorkbook;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 * Created by omnic on 10/25/2015.
 */
@Path("write")
public class DefaultEndpoint extends RestApiEndpoint {

    @GET
    public Response defaultAction() {
        System.out.println("GET");
        return Response.status(HttpServletResponse.SC_OK).entity("Hello Panoptes!").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Object createXls(ExportDataContainer exportDataContainer) {
        if (modelHasRequiredFields(exportDataContainer)) {
            return constructXlsFromData(exportDataContainer);
        } else {
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }
    }

    private StreamingOutput constructXlsFromData(ExportDataContainer exportDataContainer) {
        try {
            XlsWriter excelExporter = XlsWriterFactory.build();
            IWorkbook workbook = excelExporter.write(exportDataContainer);
            return new StreamingXlsOutput(workbook);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
