package com.omnicrola.panoptes.endpoints;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by omnic on 10/25/2015.
 */
@Path("build")
public class DefaultEndpoint {

    @GET
    public Response defaultAction() {
        return Response.status(HttpServletResponse.SC_OK).entity("Hello Panoptes!").build();
    }
}
