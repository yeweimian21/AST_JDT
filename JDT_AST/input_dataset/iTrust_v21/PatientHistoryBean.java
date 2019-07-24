package edu.ncsu.csc.itrust.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A bean for storing historical data about a patient.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class PatientHistoryBean extends PatientBean implements Serializable {
	private static final long serialVersionUID = -6474182977342257877L;
	
	private String changeDateStr = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private long changeMID = 0;
	
	public String getChangeDateStr() {
		return changeDateStr;
	}
	
	public Date getChangeDate() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(changeDateStr);
		} catch (ParseException e) {
			
			return null;
		}
	}
	
	public void setChangeDateStr(String changeDate) {
		this.changeDateStr = changeDate;
	}
	
	public long getChangeMID() {
		return this.changeMID;
	}
	
	public void setChangeMID(long mid) {
		this.changeMID = mid;
	}
	
}
