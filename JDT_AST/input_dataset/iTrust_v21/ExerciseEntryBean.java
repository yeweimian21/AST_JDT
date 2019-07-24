package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.enums.ExerciseType;

;

/**
 * ExerciseEntryBean.java Version 1 4/2/2015 Copyright notice: none Contains all
 * of the information for an entry into the Exercise Diary.
 */
public class ExerciseEntryBean extends EntryBean {

	/**
	 * Unique Primary key so entries can be edited and deleted
	 */
	private long entryID;

	/**
	 * The Date this exercise was performed
	 */
	private String strDate = new SimpleDateFormat("MM/dd/yyyy")
			.format(new Date());

	/**
	 * Was the exercise cardio or weights?
	 */
	private ExerciseType exerciseType;

	/**
	 * The name of the exercise.
	 */
	private String strName;

	/**
	 * How many hours were spent exercising?
	 */
	private double hoursWorked;

	/**
	 * How many calories were burned?
	 */
	private int caloriesBurned;

	/**
	 * If we're weight training, how many sets were performed?
	 */
	private int numSets;

	/**
	 * If we're weight training, how many reps were in each set?
	 */
	private int numReps;

	/**
	 * The MID of the user this exercise entry belongs to
	 */
	private long patientID;

	/**
	 * EntryID of the label belonging to this entry
	 */
	private long labelID;
	
	/**
	 * Returns the id of this entry so it can be edited/deleted.
	 * 
	 * @return unique id of the exercise entry
	 */
	public long getEntryID() {
		return entryID;
	}

	/**
	 * Sets the id of this entry
	 * 
	 * @param id
	 *            the unique id of a exercise entry
	 */
	public void setEntryID(long id) {
		entryID = id;
	}

	/**
	 * Returns a string representation of when the exercise was performed
	 * 
	 * @return string representation of the date on which the exercise was
	 *         performed
	 */
	public String getStrDate() {
		return strDate;
	}

	/**
	 * Parses the strDate to produce a date in the format MM/dd/yyyy
	 * 
	 * @return the date on which the exercise was performed
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
	 *            when the exercise was performed
	 */
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * Which type of exercise was performed?
	 * 
	 * @return the type of exercise
	 */
	public ExerciseType getExerciseType() {
		return exerciseType;
	}

	/**
	 * Sets the exercise type
	 * 
	 * @param exerciseType
	 *            what type of exercise was it
	 */
	public void setExerciseType(String exerciseType) {
		this.exerciseType = ExerciseType.parse(exerciseType);
	}

	/**
	 * @return the strName
	 */
	public String getStrName() {
		return strName;
	}

	/**
	 * @param strName
	 *            the strName to set
	 */
	public void setStrName(String strName) {
		this.strName = strName;
	}

	/**
	 * @return the hoursWorked
	 */
	public double getHoursWorked() {
		return hoursWorked;
	}

	/**
	 * @param hoursWorked
	 *            the hoursWorked to set
	 */
	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	/**
	 * @return the caloriesBurned
	 */
	public int getCaloriesBurned() {
		return caloriesBurned;
	}

	/**
	 * @param caloriesBurned
	 *            the caloriesBurned to set
	 */
	public void setCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}

	/**
	 * @return the numSets
	 */
	public int getNumSets() {
		return numSets;
	}

	/**
	 * @param numSets
	 *            the numSets to set
	 */
	public void setNumSets(int numSets) {
		this.numSets = numSets;
	}

	/**
	 * @return the numReps
	 */
	public int getNumReps() {
		return numReps;
	}

	/**
	 * @param numReps
	 *            the numReps to set
	 */
	public void setNumReps(int numReps) {
		this.numReps = numReps;
	}

	/**
	 * The patient that performed this exercise
	 * 
	 * @return patient ID that performed this exercise
	 */
	public long getPatientID() {
		return patientID;
	}

	/**
	 * Patient that performed this exercise
	 * 
	 * @param patientID
	 *            patient id of who performed this exercise
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