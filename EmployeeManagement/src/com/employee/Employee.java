package com.employee;

import java.util.Date;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name="employee")   //This did not generated xml response.
public class Employee {	
	//public static enum Department {ADMIN, DEVELOPMENT, MANAGEMENT};
	
	private int empID;
	private String empName;	
	private Date empDOB;
	private String dept;
	private Float salary;

	public Employee(){    //This is reqd, otherwise err : constructor Employee() is un-defined.
		super();
	}
	
	public Employee(int empID, String empName, String dept, Date empDOB, Float sal) {
		super();
		this.empID = empID;
		this.empName = empName;
		this.empDOB = empDOB;
		this.dept = dept;
		this.salary = sal;
	}
	
	public int getEmpID() {
		return empID;
	}
	public String getEmpName() {
		return empName;
	}
	public Date getEmpDOB() {
		return empDOB;
	}
	public String getDept() {
		return dept;
	}
	//@XmlAnyElement
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	//@XmlAnyElement
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	//@XmlElement
	public void setEmpDOB(Date empDOB) {
		this.empDOB = empDOB;
	}
	//@XmlElement
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", empName=" + empName + ", empDOB=" + empDOB + ", dept=" + dept + "]";
	}
	


}
