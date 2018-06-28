package com.tutorialspoint;
/*
 * Below 6 API's are RESTful webservices to demonstrate HTTP method GET, PUT, POST, DELETE & OPTIONS.
 * We can get the output both in XML & Jason format using @Produces(MediaType.APPLICATION_XML) or @Produces(MediaType.APPLICATION_JSON) 
 */
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/UserService")                   //Represents the URI path : http://localhost:8080/MyUserManagement/rest/UserService
public class UserService {
	private static final String SUCCESS_RESULT = "<result>success</result>";
	private static final String FAILURE_RESULT = "<result>failure</result>";
		
	UserDAO userDAO = new UserDAO();
	
	@GET
	@Path("/users")                   //Represents the URI path : http://localhost:8080/MyUserManagement/rest/UserService/users
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
		return userDAO.getAllUsers();
	}

	@GET
	@Path("/users/{userid}")		//Represents the URI path : http://localhost:8080/MyUserManagement/rest/UserService/users/2
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("userid")int id){
		return userDAO.getUser(id);
	}
	
	@PUT
	@Path("/adduser")			 // http://localhost:8080/MyUserManagement/rest/UserService/adduser?id=8&name=Upendra Mishra&profession=Teacher
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createUser(
			@QueryParam ("id") int id, 
			@QueryParam("name") String name, 
			@QueryParam("profession") String profession){
		//	@Context HttpServletResponse servletResponse) throws IOException{
		User user = new User(id, name, profession);
		int result = userDAO.addUser(user);
		if (result == 1){
			return SUCCESS_RESULT;					
		}
		return FAILURE_RESULT;					
	}

	@POST
	@Path("/updateuser")			//http://localhost:8080/MyUserManagement/rest/UserService/updateuser
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)	
	public String updateUser(
			@FormParam("id") int id, 
			@FormParam("name") String name, 
			@FormParam("profession") String profession){
		User user = new User(id, name, profession);
		int result = userDAO.updateUser(user);
		if (result == 1){
			return SUCCESS_RESULT;					
		}
		return FAILURE_RESULT;					
	}
	
	@DELETE
	@Path("/users/{userid}")
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@PathParam("userid") int id){
		int result = userDAO.deleteUser(id);
		if (result == 1){
			return SUCCESS_RESULT;					
		}
		return FAILURE_RESULT;					
	}
	
	@OPTIONS
	@Path("/users")
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOptions(){
		return "<operations>GET, PUT, POST, DELETE</operations>";
	}
}
