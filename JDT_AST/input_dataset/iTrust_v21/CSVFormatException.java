package edu.ncsu.csc.itrust.exception;

/**
 * This exception is thrown to indicate any type of error which occurs while
 * parsing a CSV file.
 */
public class CSVFormatException extends Exception {
	/**
	 * Unique identifier for the exception
	 */
	private static final long serialVersionUID = -6933792430135749055L;
	
	/**
	 * The error message for the exception
	 */
	String message;
	
	/**
	 * Constructor initializing the error message string
	 * 
	 * @param string The error message string
	 */
	public CSVFormatException(String string) {
		message=string;
	}
	
	/**
	 * Returns the exception's error message
	 * 
	 * @return The error message from the exception
	 */
	public String getMessage(){
		return message;
	}
	
}
