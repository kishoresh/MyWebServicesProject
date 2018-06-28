package com.employee;
/*
 * Employee DAO class to Add, Update & Delete employees. To Get a single and all the Employees.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.employee.exception.DataNotFoundException;

public class EmployeeDAO {
	private static Connection con;
	
	private static final String selectAllEmp = "SELECT empID,name, department, dob, salary FROM mydb.emp";
	private static final String selectEmp = "SELECT empID,name, department, dob, salary FROM mydb.emp where empID = ?";
	private static final String insertEmp = "INSERT into mydb.emp(empID,name, department, dob, salary) values(?, ?, ?, ?, ?)";
	private static final String updateEmp = "UPDATE mydb.emp set name = ?, department = ?, dob=? where empID = ?";
	private static final String deleteEmp = "DELETE from mydb.emp where empID = ?";
	
	public EmployeeDAO() {
		super();
		con = getDBConnection();
	}
	
	//Get all the Employees
	public List<Employee> getAllEmployees(){				
		List<Employee> empList = new ArrayList<>();
		//get all the Emp details from DB
		try{
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(selectAllEmp);
			while (rs.next()){
				Employee emp = new Employee();
				emp.setEmpID(rs.getInt(1));
				emp.setEmpName(rs.getString(2));
				emp.setDept(rs.getString(3));
				Date dt = new Date(rs.getDate(4).getTime());
				emp.setEmpDOB(dt);
				emp.setSalary(rs.getFloat(5));
				empList.add(emp);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}

		return empList;
	}
	
	//Get an Employee. It is tweaked to get a NullPointer exception.
	public Employee getEmployee(int empID){
		Employee emp = null; //new Employee();  //This is to commented create the Nullpointer exception. 
		try{
			PreparedStatement ps = con.prepareStatement(selectEmp);
			ps.setInt(1, empID);
			ResultSet rs = ps.executeQuery();	
			//emp.setEmpID(empID);     //This line will raise a nullpointer error, as emp is not yet created.
			while (rs.next()){
				emp = new Employee();
				emp.setEmpID(empID);
				emp.setEmpName(rs.getString(2));
				emp.setDept(rs.getString(3));
				//emp.setEmpDOB(rs.getDate(4));   //This works but on calling as REST API it does not work.
				Date dt = new Date(rs.getDate(4).getTime()); //This is done to convert java.sql.Date to java.util.Date, and create a new instant of Date
				emp.setEmpDOB(dt);
				emp.setSalary(rs.getFloat(5));
			}
			if (emp == null){     //This is to test how to capture the Exception.
				throw new DataNotFoundException("Employee with ID #" + empID + " not found.");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}		
		return emp;	
	}
	
	//Get all employees for a particular DOB Year.
	public List<Employee> getAllEmployeesForYear(int year){
		List<Employee> empListForYear = new ArrayList<>();
		List<Employee> empList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		
		empList = getAllEmployees();
		for(Employee emp : empList){
			cal.setTime(emp.getEmpDOB());     //Setting the calendar year for the EmpDOB field.
			if(cal.get(Calendar.YEAR) == year){
				empListForYear.add(emp);
			}
		}
		return empListForYear;		
	}
	
	//Get all Employees but paginated, i.e. from start to a particular size or number of the list.
	public List<Employee> getAllEmployeesPaginated(int start, int size){
		List<Employee> empList = new ArrayList<>();	
		empList = getAllEmployees();
		if (start + size > empList.size()) return empList;
		return empList.subList(start, start+size);		
	}
	
	//Add an Employee
	public int addEmployee(Employee emp){
		int ret = -1;
		try{
			PreparedStatement ps = con.prepareStatement(insertEmp);
			ps.setInt(1, emp.getEmpID());
			ps.setString(2, emp.getEmpName());
			ps.setString(3, emp.getDept());
			ps.setTimestamp(4, new Timestamp(emp.getEmpDOB().getTime()));
			ps.setFloat(5, emp.getSalary());
			ps.execute();
			ret = 0;
		}catch (SQLException e){			
			e.printStackTrace();
		}
		return ret;
	}
	
	//update an Employee
	public int updateEmployee(Employee emp){
		int ret = -1;
		try{
			PreparedStatement ps = con.prepareStatement(updateEmp);
			ps.setString(1, emp.getEmpName());
			ps.setString(2, emp.getDept());
			ps.setTimestamp(3, new Timestamp(emp.getEmpDOB().getTime()));
			ps.setInt(4, emp.getEmpID());
			ps.execute();
			ret = 0;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	//Delete an employee
	public int deleteEmployee(int empID){
		int ret = -1;
		try{
			PreparedStatement ps = con.prepareStatement(deleteEmp);
			ps.setInt(1, empID);
			ps.execute();
			ret = 0;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	//Get the MySQL DB Connection 
	private static Connection getDBConnection(){
		//Connection con = null;	
		try{ 
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Class.forName("com.mysql.jdbc.Driver");
			try{
				//con = DriverManager.getConnection("jdbc:sqlserver://localhost\\sqlexpress?integratedSecurity=true", "COMAKEIT\\kishores", "KANtu123");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false","kishores", "KANtu1234");				
			}catch (SQLException e){
				e.printStackTrace();
			}
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}				
		return con;		
	}
	
	public static void main(String[] args){
		con = getDBConnection();
		EmployeeDAO empDAO = new EmployeeDAO();
		System.out.println(con.toString());		

		Employee eAdd = new Employee(42, "Amit Patil10", "Development", new Date(), 50000.00F);
		empDAO.addEmployee(eAdd);
		empDAO.updateEmployee(eAdd);
		//empDAO.deleteEmployee(1);
		
		List<Employee> ls = empDAO.getAllEmployees();
		for(Employee e : ls){
			System.out.println(e.toString());
		}
	}
}
