package com.github.tkmtmkt;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
public class Sample {

    @GET
    @Path("message")
    @Produces("text/plain")
    public String getMessage(
            @QueryParam("hello") String hello,
            @QueryParam("world") String world) {
        return hello + " " + world + "!!";
    }
}
