package com.employee;
/*
 * Demo resource to test other types of Params like :
 * @MatrixParam(“paramname”)
 * @HeaderParam(“paramname”)
 * @CookieParam(“cookiename”)
 * @Context URIInfo
 * @Context HttpHeaders
 * 
 */

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

}
