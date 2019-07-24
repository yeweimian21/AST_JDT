package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A bean for storing health record data.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class HealthRecord {
	private long patientID = 0;
	private long officeVisitID = 0;
	private String officeVisitDate = "0000-00-00";
	private double headCircumference = 0;
	private double height = 0;
	private double weight = 0;
	private boolean isSmoker = false;
	private String smokingStatusDesc = "Unknown if ever smoked";
	private int smokingStatus = 9;
	private String householdSmokingStatusDesc = "Non-smoking household";
	private int householdSmokingStatus = 1;
	private int bloodPressureN = 0;
	private int bloodPressureD = 0;
	private int cholesterolHDL = 0;
	private int cholesterolLDL = 0;
	private int cholesterolTri = 0;
	private long personnelID = 0;
	private Timestamp dateRecorded = new Timestamp(new Date().getTime());
	private double bmi = -1;

	public HealthRecord() {
	}
	
	public long getOfficeVisitID(){
		return officeVisitID;
	}
	
	public void setOfficeVisitID(long officeVisitID){
		this.officeVisitID = officeVisitID;
	}
	
	public void setOfficeVisitDateStr(String officeVisitDate){
		this.officeVisitDate = officeVisitDate;
	}
	
	public Date getVisitDate() {
		Date d = null; 
		try {
			d = new SimpleDateFormat("MM/dd/yyyy").parse(officeVisitDate);
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		return d;
	}
	
	public String getVisitDateStr() {
		return officeVisitDate;
	}
	
	public double getHeadCircumference(){
		return headCircumference;
	}
	
	public void setHeadCircumference(double headCircumference){
		this.headCircumference = headCircumference;
	}

	public int getBloodPressureD() {
		return bloodPressureD;
	}

	public void setBloodPressureD(int bloodPressureD) {
		this.bloodPressureD = bloodPressureD;
	}

	public void setBloodPressureSystolic(int bloodPressure) {
		this.bloodPressureN = bloodPressure;
	}

	public void setBloodPressureDiastolic(int bloodPressure) {
		this.bloodPressureD = bloodPressure;
	}

	public int getBloodPressureN() {
		return bloodPressureN;
	}

	public int getBloodPressureSystolic() {
		return bloodPressureN;
	}

	public int getBloodPressureDiastolic() {
		return bloodPressureD;
	}

	public void setBloodPressureN(int bloodPressureN) {
		this.bloodPressureN = bloodPressureN;
	}

	public String getBloodPressure() {
		return getBloodPressureN() + "/" + getBloodPressureD();
	}

	public int getCholesterolHDL() {
		return cholesterolHDL;
	}

	public void setCholesterolHDL(int cholesterolHDL) {
		this.cholesterolHDL = cholesterolHDL;
	}

	public int getCholesterolLDL() {
		return cholesterolLDL;
	}

	public void setCholesterolLDL(int cholesterolLDL) {
		this.cholesterolLDL = cholesterolLDL;
	}

	public int getCholesterolTri() {
		return cholesterolTri;
	}

	public void setCholesterolTri(int cholesterolTri) {
		this.cholesterolTri = cholesterolTri;
	}

	/**
	 * Note that this is a simplistic view. See the Wikipedia article on cholesterol.
	 * 
	 * @return
	 */
	public int getTotalCholesterol() {
		return getCholesterolHDL() + getCholesterolLDL() + getCholesterolTri();
	}

	public Date getDateRecorded() {
		return (Date) dateRecorded.clone();
	}

	public void setDateRecorded(Timestamp dateRecorded) {
		this.dateRecorded = (Timestamp) dateRecorded.clone();
	}

	// Rounds the height off here because MySQL won't return the *exact* value you put in it
	public double getHeight() {
		return Math.round(height * 10000) / 10000D;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public long getPersonnelID() {
		return personnelID;
	}

	public void setPersonnelID(long personnelID) {
		this.personnelID = personnelID;
	}

	public boolean isSmoker() {
		return isSmoker;
	}
	
	public int getSmokingStatus() {
		return smokingStatus;
	}
	
	public String getSmokingStatusDesc() {
		return smokingStatusDesc;
	}
	
	public String getHouseholdSmokingStatusDesc(){
		return householdSmokingStatusDesc;
	}
	
	public int getHouseholdSmokingStatus(){
		return householdSmokingStatus;
	}
	
	public void setHouseholdSmokingStatus(int householdSmokingStatus){
		this.householdSmokingStatus = householdSmokingStatus;
		switch(householdSmokingStatus){
		case 1: this.householdSmokingStatus = 1; 
			this.householdSmokingStatusDesc = "Non-smoking household";
			break;
		case 2: 
			this.householdSmokingStatus = 2;
			this.householdSmokingStatusDesc = "Outdoor smokers";
			break;
		case 3: 
			this.householdSmokingStatus = 3;
			this.householdSmokingStatusDesc = "Indoor smokers";
			break;
		default:
			break;
		}
	}
	
	public void setSmoker(int smoker) {
		this.smokingStatus = smoker;
		switch (smoker) {
			case 1: this.isSmoker = true;
			this.smokingStatusDesc = "Current every day smoker"; break;
			case 2: this.isSmoker = true;
			this.smokingStatusDesc = "Current some day smoker"; break;
			case 3: this.isSmoker = false;
			this.smokingStatusDesc = "Former smoker"; break;
			case 4: this.isSmoker = false;
			this.smokingStatusDesc = "Never smoker"; break;
			case 5: this.isSmoker = true;
			this.smokingStatusDesc = "Smoker, current status unknown"; break;
			case 9: this.isSmoker = false;
			this.smokingStatusDesc = "Unknown if ever smoked"; break;
			default:
				break;
		}
	}

	public double getWeight() {
		return Math.round(weight * 10000) / 10000D;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Returns the BMI of the health record.
	 * @return BMI
	 */
	public double getBodyMassIndex() {
		setBodyMassIndex();
		return bmi;
	}
	
	/**
	 * Computes the BMI for a height/weight pair
	 * @param height The patient height
	 * @param weight The patient weight
	 * @return The patient's BMI or -1 on error
	 */
	public static double calculateBMI(double height, double weight) {
		double bmi = -1;
		if(weight > 0 && height > 0) {
			DecimalFormat df = new DecimalFormat("##.##");
			bmi = (weight * 703) / (height * height);
			bmi = Double.valueOf(df.format(bmi));
		}
		return bmi;
	}
	
	/**
	 * Calculates the BMI based on current values for height and weight.
	 */
	public void setBodyMassIndex() {
		this.bmi = calculateBMI(this.height, this.weight);
	}
}
