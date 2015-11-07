package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.ExportDataContainer;
import com.omnicrola.panoptes.export.XlsBuilderFactory;
import com.omnicrola.panoptes.export.xls.ExcelExporter;
import com.omnicrola.panoptes.export.xls.StreamingXlsOutput;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 * Created by omnic on 10/25/2015.
 */
@Path("build")
public class DefaultEndpoint {

    @GET
    public Response defaultAction() {
        return Response.status(HttpServletResponse.SC_OK).entity("Hello Panoptes!").build();
    }

    @POST
    @Produces({"application/xls"})
    public StreamingOutput createXls(ExportDataContainer exportDataContainer) {
        ExcelExporter excelExporter = XlsBuilderFactory.build();
        XSSFWorkbook workbook = excelExporter.build(exportDataContainer);
        return new StreamingXlsOutput(workbook);
    }
}
