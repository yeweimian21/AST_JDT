package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;



/**
 * A bean for storing data about a lab procedure.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class LabProcedureBean {
	
	public final static String In_Transit = "In Transit";
	public final static String Received = "Received";
	public final static String Testing = "Testing";
	public final static String Pending = "Pending";
	public final static String Completed = "Completed";
	
	
	public final static String Allow = "ALLOWED";
	public final static String Restrict = "RESTRICTED";

	
	/**
	 * Unique 10-digit number that does not start with 9
	 */
	 private long pid;	 
	 
	 /**
	  * Unique identifier for a laboratory procedure of a patient
	  */
	 private long procedureID;
	 
	 /**
	  * Digits of the format nnnnn-n 
	  */
	 private String loinc; 
	
	 /**
	  * One of (NOT YET RECEIVED, PENDING, COMPLETED)
	  */
	 private String status = "";
	 
	 /**
	  * Up to 500 alphanumeric characters
	  */
	 private String commentary = "";
	 
	 /**
	  * Up to 500 alphanumeric characters
	  */
	 private String results = "";
	 
	 /**
	  * Office VisitID	Identifier that specifies the office visit in 
	  * which the laboratory procedure was ordered
	  */
	 private long ovID; 
	 
	 /**
	  * Date/Time of last status update 	Timestamp
	  */
	 private Timestamp timestamp;
	 
	 /**
	  * permission granted by lhcp who ordered test:
	  * "ALLOWED", "RESTRICTED"
	  */
	 private String rights = Allow;
	 
	 /**
	  * The assigned Lab Tech ID
	  */
	 private long LTID;
	 
	 /**
	  * This is the priority of the Lab Procedure (from 1-3)
	  */
	 private int priorityCode;
	 
	 /**
	  * Whether or not a patient has viewed the lab procedure
	  */
	 private boolean viewedByPatient = false;
	 
	 /**
	 * The result as a numerical value.  Stored as a string to ensure accuracy.
	 * The number may have an optional sign and units, then one of the following formats:
	 * 		DIGIT+ (unit)				(one or more digits)
	 *      DIGIT+ . DIGIT* (unit)		(one or more digits, a decimal point, and zero or more digits)
	 *      . DIGIT+ (unit)			(a decimal point and one or more digits)
	 */
	private String numericalResult = "";
	
	/**
	 * The units for the results
	 * 
	 */
	private String numericalResultUnit = "";
	 
	 /**
	 * The upper bound of the confidence interval. 
	 */
	private String upperBound = "";
	 
	 /**
	 * The lower bound of the confidence interval.  
	 */
	private String lowerBound = "";
	 
	 
	 public LabProcedureBean(){
	 }

	/**
	 * Unique 10-digit number that does not start with 9
	 */
	public long getPid() {
		return pid;
	}


	/**
	 * Unique 10-digit number that does not start with 9
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}


	/**
	  * Unique identifier for a laboratory procedure of a patient
	  */
	public long getProcedureID() {
		return procedureID;
	}


	/**
	  * Unique identifier for a laboratory procedure of a patient
	  */
	public void setProcedureID(long procedureID) {
		this.procedureID = procedureID;
	}


	 /**
	  * Digits of the format nnnnn-n 
	  */
	public String getLoinc() {
		return loinc;
	}


	 /**
	  * Digits of the format nnnnn-n 
	  */
	public void setLoinc(String loinc) {
		this.loinc = loinc;
	}


	/**
	  * One of (NOT YET RECEIVED, PENDING, COMPLETED)
	  */
	public String getStatus() {
		return status;
	}


	/**
	  * One of (NOT YET RECEIVED, PENDING, COMPLETED)
	  */
	public void setStatus(String status) {
		this.status = status;
	}


	 /**
	  * Up to 500 alphanumeric characters
	  */
	public String getCommentary() {
		return commentary;
	}


	 /**
	  * Up to 500 alphanumeric characters
	  */
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}


	 /**
	  * Up to 500 alphanumeric characters
	  */
	public String getResults() {
		return results;
	}


	 /**
	  * Up to 500 alphanumeric characters
	  */
	public void setResults(String results) {
		this.results = results;
	}


	 /**
	 * @return the numericalResult
	 */
	public String getNumericalResult() {
		return numericalResult;
	}
	
	/**
	 * Get the numerical result as a double.  If the result is blank or null, 
	 * NaN is returned.
	 * @return The numerical result as a double.
	 */
	public double getNumericalResultAsDouble() {
		if (numericalResult != null && numericalResult.length() > 0) {
			return Double.parseDouble(numericalResult);
		} else {
			return Double.NaN;
		}
	}

	/**
	 * @param numericalResult the numericalResult to set
	 */
	public void setNumericalResult(String numericalResult) {
		this.numericalResult = numericalResult;
	}
	
	public String getNumericalResultUnit() {
		return numericalResultUnit;
	}
	
	public void setNumericalResultUnit(String numericalResultUnit) {
		this.numericalResultUnit = numericalResultUnit;
	}

	/**
	 * @return the upperBound
	 */
	public String getUpperBound() {
		return upperBound;
	}
	
	/**
	 * Get the upper bound as a double.  If the result is blank or null, 
	 * NaN is returned.
	 * @return The upper bound as a double.
	 */
	public double getUpperBoundAsDouble() {
		if (upperBound != null && upperBound.length() > 0) {
			return Double.parseDouble(upperBound);
		} else {
			return Double.NaN;
		}
	}

	/**
	 * @param upperBound the upperBound to set
	 */
	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	}

	/**
	 * @return the lowerBound
	 */
	public String getLowerBound() {
		return lowerBound;
	}
	
	/**
	 * Get the lower bound as a double.  If the result is blank or null, 
	 * NaN is returned.
	 * @return The lower bound as a double.
	 */
	public double getLowerBoundAsDouble() {
		if (lowerBound != null && lowerBound.length() > 0) {
			return Double.parseDouble(lowerBound);
		} else {
			return Double.NaN;
		}
	}

	/**
	 * @param lowerBound the lowerBound to set
	 */
	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}

	/**
	  * Office VisitID	Identifier that specifies the office visit in 
	  * which the laboratory procedure was ordered
	  */
	public long getOvID() {
		return ovID;
	}


	 /**
	  * Office VisitID	Identifier that specifies the office visit in 
	  * which the laboratory procedure was ordered
	  */
	public void setOvID(long ovID) {
		this.ovID = ovID;
	}

	public Timestamp getTimestamp() {
		return (Timestamp) timestamp.clone();
	}



	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = (Timestamp) timestamp.clone();
	}

	 /**
	  * permission granted by lhcp who ordered test:
	  * "ALLOWED", "RESTRICTED"
	  */
	public String getRights() {
		return rights;
	}

	 /**
	  * permission granted by lhcp who ordered test:
	  * "ALLOWED", "RESTRICTED"
	  */
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	
	 public void allow(){
		 this.rights = Allow;
	 }
	 
	 public void restrict(){
		 this.rights = Restrict;
	 }
	 
	 public void statusComplete(){
	 	this.status = Completed;
	 }
	 
	 public void statusPending(){
		 this.status = Pending;
	 }
	 
	 /**
	  * sets the status to "In transit"
	  */
	 public void statusInTransit(){
		 this.status = In_Transit;
	 }

	 /**
	  * sets the status to "Received"
	  */
	 public void statusReceived(){
		 this.status = Received;
	 }

	 /**
	  * sets the status to "Testing"
	  */
	 public void statusTesting(){
		 this.status = Testing;
	 }
	 
	/**
	 * This method sets the lab technicians ID
	 * @param lTID
	 */
	public void setLTID(long LTID) {
		this.LTID = LTID;
	}


	/**
	 * Gets the lab techs ID
	 * @return The id of the assigned lab tech
	 */
	public long getLTID() {
		return LTID;
	}


	/**
	 * Sets the priority of the lab procedure
	 * @param priorityCode
	 */
	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}


	/**
	 * Gets the priority of the lab procedure
	 * @return the priority code
	 */
	public int getPriorityCode() {
		return priorityCode;
	}

	/**
	 * Returns whether or not the patient has viewed the lab procedure
	 * @return
	 */
	public boolean isViewedByPatient() {
		return viewedByPatient;
	}

	/**
	 * Sets whether or not a patient has viewed the lab procedure. Cannot be true until status is Completed 
	 * @param viewedByPatient
	 */
	public void setViewedByPatient(boolean viewedByPatient) {
		if (status.equals(Completed)) {
			this.viewedByPatient = viewedByPatient;
		}
	}
}
