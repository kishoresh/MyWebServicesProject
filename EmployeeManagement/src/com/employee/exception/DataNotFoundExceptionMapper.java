package com.employee.exception;
/*
 * Exception Mapper class : 
 * Create a new ExceptionMapper class which have to implement the ExceptionMapper interface and override the toResponse() method.
 * To handle the Custom exception 'Data not found' exception we create DataNotFoundExceptionMapper
 * When an exception is raised, REST checks for a mapped Exception Mapper for that exception.
 * If it finds the mapper class, the toResponse() method of the mapper class is called.
 * All new class's have to be registered with JAX-RS using @Provider annotation. 
 * The return type Response is than populated with proper Status and content before it is build.
 */
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider                         //This will register the ExceptionMapper class with JAX-RS
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "http://google.com");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}
	

}
