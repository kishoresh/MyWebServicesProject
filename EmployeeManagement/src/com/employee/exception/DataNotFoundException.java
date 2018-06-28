package com.employee.exception;
/*
 * Custom exception extending from RuntimeException, which is again ultimately derived from Serializable.
 * RuntimeException > Exception > Throwable > Serializable
 * Any serializable class is strongly recommended to have its own serialVersionUID, since the default serialVersionUID 
 * computation is highly sensitive to class details that may vary depending on compiler implementations, and can thus 
 * result in unexpected InvalidClassExceptions during deserialization.
 */
public class DataNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6924973044295651190L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
