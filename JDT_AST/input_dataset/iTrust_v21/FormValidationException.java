package edu.ncsu.csc.itrust.exception;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * This exception is used specifically for when an action involves the correct
 * and proper submission of a web form.  Form Validation is handled by a series of
 * other classes, but when form validation is incorrect or incomplete, this exception
 * is thrown. 
 */
public class FormValidationException extends Exception {
	private static final long serialVersionUID = 1L;
	private ErrorList errorList;

	/**
	 * Constructor with error messages passed as a list of parameters to the method.
	 * @param errorMessages The list of error messages to be returned in the special form validation box.
	 */
	public FormValidationException(String... errorMessages) {
		errorList = new ErrorList();
		for (String msg : errorMessages) {
			errorList.addIfNotNull(msg);
		}
	}

	/**
	 * Constructor with error messages as a special ErrorList data type.
	 * @param errorList An ErrorList object which contains the list of error messages.
	 */
	public FormValidationException(ErrorList errorList) {
		this.errorList = errorList;
	}

	/**
	 * Returns the error list as a java.util.List of Strings.
	 * @return The error list
	 */
	public List<String> getErrorList() {
		return errorList.getMessageList();
	}

	/**
	 * The error message will be displayed at the top of the iTrust page as in other iTrust Exceptions.
	 */
	@Override
	public String getMessage() {
		return "This form has not been validated correctly. The following field are not properly filled in: "
				+ errorList.toString();
	}
	

	/**
	 * The special formatting for error messages is then kept in one place.
	 * @param out The output writer (in this case a JSPWriter) where the formatted list will go.
	 * @throws IOException If the writer is incorrect.
	 */
	public void printHTML(JspWriter out) throws IOException {
		out.print("<h2>Information not valid</h2><div class=\"errorList\">");
		for (String errorMessage : errorList) {
			out.print(StringEscapeUtils.escapeHtml(errorMessage) + "<br />");
		}
		out.print("</div>");
	}
	
	/** Like printHTML, except a string is returned. */
	public String printHTMLasString() {
		StringBuffer buf = new StringBuffer();
		for (String errorMessage : errorList) {
			buf.append(StringEscapeUtils.escapeHtml(errorMessage));
			buf.append("<br />");
		}
		String r = "<h2>Information not valid</h2><div class=\"errorList\">" + buf.toString() + "</div>";
		return r;
	}
}
