package edu.ncsu.csc.itrust.beans;

import java.util.Date;


/**
 * MacronutrientsBean.java
 * Version 1
 * 03/31/2015
 * Copyright notice: none
 * Contains all of the information for an entry into the Food Diary.
 */
public class MacronutrientsBean {
	
	public static final double HEIGHT = 6.25;
	public static final double WEIGHT = 10;
	public static final int YEARS = 5;
	public static final int ADD = 5;
	public static final long MS_PER_DAY = 1000 * 60 * 60 * 24;
	public static final double DAYS_PER_YEAR = 365.242;
	
	/**
	 * height
	 */
	private float height;
	
	/**
	 * weight
	 */
	private float weight;
	
	/**
	 * age
	 */
	private int years;
	
	/**
	 * Mifflin-st Jeor
	 */
	private double msj;
	
	/**
	 * The MID of the user this Food Entry belongs to
	 */
	private long patientID;

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getYears() {
		return years;
	}
	
	public void setYears(Date date) {		
		double years = (new Date().getTime() - date.getTime()) / MS_PER_DAY / DAYS_PER_YEAR;
		this.years = (int) years;
	}

	/**
	 * Get height of the patient
	 * @return height of the patient
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Set patient height
	 * @param height height of patient
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Mifflin-St Jeor Value
	 * @return Mifflin-St Jeor Value 
	 */
	public double getMsj() {
		return msj;
	}

	/**
	 * Calculate Mifflin-St. Jeor
	 * 
	 */
	public void setMsj(float height, float weight, int years) {
		this.msj = (WEIGHT * weight + HEIGHT * height + ADD - YEARS * years);
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
}
