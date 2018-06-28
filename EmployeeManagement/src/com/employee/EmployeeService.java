package com.employee;
/*
 * Employee Service class to test the following :
 * 1. GET with diff kinds of parametres : Use the getAllEmployees() method to test 
 *    a. without any parameters - Get all the employees by calling empDAO.getAllEmployees()
 *    b. with Year as a parameter - Get all employees for a particular DOB Year by calling empDAO.getAllEmployeesForYear(year)
 *    c. Pagination with start Page and Size= No of employees - By calling empDAO.getAllEmployeesPaginated(start, size) 
 *    d. BeanParam - Create a bean class EmployerFilterBean and pass this as a parameter to this method. Get access to all the parameters using Getters & Setters.
 * 
 * 2. GET with Exception Handling : Use the getEmployee() method to test how to handle Exceptions. REST first checks for an ExceptionMapper class, when an exception occurs.
 *    a. NullPointerException - Create our own custom DataNotFoundException and than DataNotFoundExceptionMapper to map the DataNotFoundException.
 *    b. Generic Exception - Create the GenericExceptionMapper class which implements Throwable to capture all kinds of exceptions. 
 * 
 * 3. PUT : Use simple QueryParams to add an Employee - addEmployee()
 * 4. PUT : Use Jason object to add an Employee and Response object to return the response - addEmployee2()    
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/EmployeeService")
public class EmployeeService {
	EmployeeDAO empDAO = new EmployeeDAO();
	private static final String SUCCESS_RESULT = "<result>SUCCESS</result>";
	private static final String FAILURE_RESULT = "<result>FAILURE</result>";
	
	@GET                                    //URI Path : http://localhost:8080/EmployeeManagement/rest/EmployeeService/employees    
	@Path("/employees")                     //URI Path : http://localhost:8080/EmployeeManagement/rest/EmployeeService/employees?year=1973
	@Produces(MediaType.APPLICATION_JSON)   //http://localhost:8080/EmployeeManagement/rest/EmployeeService/employees?start=2&size=3
//	public List<Employee> getAllEmployees(
//			@QueryParam("year") int year,
//			@QueryParam("start") int start,    //Pagination with start Page and Size= No of employees
//			@QueryParam("size") int size){
//		if (year > 0){
//			return empDAO.getAllEmployeesForYear(year);
//		}
//		if (start > 0 && size > 0){
//			return empDAO.getAllEmployeesPaginated(year, size);
//		}
//		return empDAO.getAllEmployees();		
//	}
	//The above commented service method can also be implemented using BeanParam
	public List<Employee> getAllEmployees(@BeanParam EmployerFilterBean filerBean){
		if (filerBean.getYear() > 0){
			return empDAO.getAllEmployeesForYear(filerBean.getYear());
		}
		if (filerBean.getStart() > 0 && filerBean.getSize() > 0){
			return empDAO.getAllEmployeesPaginated(filerBean.getStart(), filerBean.getSize());
		}
		return empDAO.getAllEmployees();		
	}	
	
	@GET
	@Path("/employee/{empID}")                 //URI Path : http://localhost:8080/EmployeeManagement/rest/EmployeeService/employee/4
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getEmployee(@PathParam("empID") int EmpID){
		return empDAO.getEmployee(EmpID);
	}
	
	//Adding an Employee using PUT request and QueryParams
	@PUT       
	@Path("/addemployee")                 //URI Path : http://localhost:8080/EmployeeManagement/rest/EmployeeService/addemployee
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addEmployee(
			@QueryParam("empID") int id, 
			@QueryParam("empName") String name, 
			@QueryParam("dept") String dept, 
			@QueryParam("dob") String dob, 
			@QueryParam("salary") Float sal){
		Date dt = new Date();
		try{
		   dt= new SimpleDateFormat("dd/mm/YYYY").parse(dob);
		}catch (ParseException e){
			e.printStackTrace();			
		}
		Employee emp = new Employee(id, name, dept, dt, sal);
		int result = empDAO.addEmployee(emp);
		if (result == 0){
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}
	
	//Return a Response object with the Status code
	@POST
	@Path("/addemployee2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(Employee emp){
		int ret = empDAO.addEmployee(emp);
		return (Response.status((ret==0) ? Status.CREATED : Status.BAD_REQUEST)  //Set the response status
				.entity(emp)    //Content in the response
				.build());      //Build the response
	}
	
	@DELETE
	@Path("/employees/{empID}")
	@Consumes(MediaType.APPLICATION_ATOM_XML)
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String deleteEmployee(@PathParam("empID") int empID){
		int ret = empDAO.deleteEmployee(empID);
		if (ret == 0){
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}
	
	public int updateEmployee(Employee emp){
		return empDAO.updateEmployee(emp);
	}
	
	public static void main(String[] args){
		EmployeeService empService = new EmployeeService();
		empService.addEmployee(21, "Pankaj Paliwal12", "Admin", "21/01/1974", 24000.00F);
		
//		List<Employee> ls = empService.getAllEmployees(0,0,0);
//		for(Employee e : ls){
//			System.out.println(e.toString());
//		}
		Employee emp = empService.getEmployee(4);
		System.out.println(emp.toString());
		
	}

}
