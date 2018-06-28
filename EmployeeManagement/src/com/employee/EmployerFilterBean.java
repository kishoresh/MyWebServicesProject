package com.employee;

/*
 * Bean class for all the QueryParams of getAllEmployees() 
 */
import javax.ws.rs.QueryParam;

public class EmployerFilterBean {
	private @QueryParam("year") int year;
	private @QueryParam("start") int start;
	private @QueryParam("size") int size;

	public int getYear() {
		return year;
	}
	public int getStart() {
		return start;
	}
	public int getSize() {
		return size;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public void setSize(int size) {
		this.size = size;
	}

}
