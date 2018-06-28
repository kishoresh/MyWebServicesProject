package com.employee.exception;
/*
 * The error message object as an XML/Jason message to be presented to the user in the response.
 * Instead of Tomcat default error page, we need to bubble up this error message object.
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	private String errormessage;
	private int errorCode;
	private String documentations;
	
	public ErrorMessage() {
		// TODO Auto-generated constructor stub
	}	
	
	public ErrorMessage(String errormessage, int errorCode, String documentations) {
		super();
		this.errormessage = errormessage;
		this.errorCode = errorCode;
		this.documentations = documentations;
	}

	public String getErrormessage() {
		return errormessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public String getDocumentations() {
		return documentations;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public void setDocumentations(String documentations) {
		this.documentations = documentations;
	}
	
	
}
