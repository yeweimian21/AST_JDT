package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.enums.SleepType;

/**
 * SleepEntryBean.java Version 1 4/6/2015 Copyright notice: none Contains all
 * of the information for an entry into the Sleep Diary.
 * 
 */
public class SleepEntryBean extends EntryBean {

	/**
	 * Unique Primary key so entries can be edited and deleted
	 */
	private long entryID;

	/**
	 * The Date this sleep was performed
	 */
	private String strDate = new SimpleDateFormat("MM/dd/yyyy")
			.format(new Date());

	/**
	 * Was the sleep nightly or a nap?
	 */
	private SleepType sleepType;

	/**
	 * How many hours were spent exercising?
	 */
	private double hoursSlept;

	/**
	 * The MID of the user this sleep entry belongs to
	 */
	private long patientID;

	/**
	 * EntryID of the label belonging to this entry
	 */
	private long labelID;
	
	/**
	 * Returns the id of this entry so it can be edited/deleted.
	 * 
	 * @return unique id of the sleep entry
	 */
	public long getEntryID() {
		return entryID;
	}

	/**
	 * Sets the id of this entry
	 * 
	 * @param id
	 *            the unique id of a sleep entry
	 */
	public void setEntryID(long id) {
		entryID = id;
	}

	/**
	 * Returns a string representation of when the sleep was performed
	 * 
	 * @return string representation of the date on which the sleep was
	 *         performed
	 */
	public String getStrDate() {
		return strDate;
	}

	/**
	 * Parses the strDate to produce a date in the format MM/dd/yyyy
	 * 
	 * @return the date on which the sleep was performed
	 */
	public Date getDate() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
		} catch (ParseException e) {

			return null;
		}
	}

	/**
	 * Sets the date as a string
	 * 
	 * @param strDate
	 *            when the sleep was performed
	 */
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * Which type of sleep was performed?
	 * 
	 * @return the type of sleep
	 */
	public SleepType getSleepType() {
		return sleepType;
	}

	/**
	 * Sets the sleep type
	 * 
	 * @param sleepType
	 *            what type of sleep was it
	 */
	public void setSleepType(String sleepType) {
		this.sleepType = SleepType.parse(sleepType);
	}

	/**
	 * @return the hoursSlept
	 */
	public double getHoursSlept() {
		return hoursSlept;
	}

	/**
	 * @param hoursSlept
	 *            the hoursSlept to set
	 */
	public void setHoursSlept(double hoursSlept) {
		this.hoursSlept = hoursSlept;
	}

	/**
	 * The patient that performed this sleep
	 * 
	 * @return patient ID that performed this sleep
	 */
	public long getPatientID() {
		return patientID;
	}

	/**
	 * Patient that performed this sleep
	 * 
	 * @param patientID
	 *            patient id of who performed this sleep
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	/**
	 * Label of this meal
	 * @return label of this meal
	 */
	public long getLabelID() {
		return labelID;
	}
	
	/**
	 * Label of this meal
	 * @param label of this meal
	 */
	public void setLabelID(long labelID) {
		this.labelID = labelID;
	}
}