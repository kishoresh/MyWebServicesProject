package com.employee;

public enum Department {
	ADMIN("Admin"), 
	DEVELOPMENT("Development"), 
	MANAGEMENT("Management"),
	HR("HR");
	
	String dept;
	Department(String dept){
		this.dept = dept;
	}	
	
	public Department getDept(String s){
	  return this;	
	}
	
	public String getDepartment(){
		return this.dept;
	}
	

}
