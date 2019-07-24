package edu.ncsu.csc.itrust.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCBmiStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.CDCWeightStatsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.NormalDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewHealthRecordsHistoryAction is an action class used for viewing a patient's health records.
 * Allows a patient or hcp to view a patient's health records dependent on the age. Contains methods 
 * that will sort health records by age during the office visit, calculating z scores for health metrics,
 * and calculating percentiles for those z scores. 
 */
public class ViewHealthRecordsHistoryAction {
	
	/** The number of months in a year */
	public static final int MONTHS_IN_YEAR = 12;
	/** The integer for representing a Male patient */
	public static final int MALE = 1;
	/** The integer for representing a Female patient */
	public static final int FEMALE = 2;
	/** Pounds in a kilogram */
	public static final double POUNDS_IN_KG = 2.2046;
	/** Inches in a centimeter */
	public static final double INCHES_IN_CM = 0.3937;
	/** The max z score value in the normal distribution table */
	public static final double MAX_Z = 5.09;
	/** The min z score value in the normal distribution table */
	public static final double MIN_Z = -4;
	
	private PatientDAO patientDAO;
	private HealthRecordsDAO hrDAO;
	private OfficeVisitDAO ovDAO;
	private AuthDAO authDAO;
	private CDCWeightStatsDAO weightStatsDAO;
	private CDCHeightStatsDAO heightStatsDAO;
	private CDCHeadCircStatsDAO headCircStatsDAO;
	private CDCBmiStatsDAO bmiStatsDAO;
	private NormalDAO normalDAO;
	private long patientID = 0;
	
	
	private Role user;
	
	private long loggedInMID;
	
	private EventLoggingAction loggingAction;
	
	/**
	 * Constructor for ViewHealthRecordsHistoryAction. Initializes all DAO field objects with the DAOFactory that is passed
	 * in. Also sets the pid of the patient whose records are to be viewed and saves the logged in user's mid for logging.
	 * @param factory the DAOFactory to have database interactions with
	 * @param pidString the String representing the patient's mid
	 * @param loggedInMID long for the logged in user's mid
	 * @throws ITrustException
	 */
	public ViewHealthRecordsHistoryAction(DAOFactory factory, String pidString, long loggedInMID) throws ITrustException{
		init(factory);
		weightStatsDAO = new CDCWeightStatsDAO(factory);
		heightStatsDAO = new CDCHeightStatsDAO(factory);
		headCircStatsDAO = new CDCHeadCircStatsDAO(factory);
		bmiStatsDAO = new CDCBmiStatsDAO(factory);
		normalDAO = new NormalDAO(factory);
		this.patientID = Long.parseLong(pidString);
		this.user = authDAO.getUserRole(loggedInMID);
		this.loggedInMID = loggedInMID;
			
	}
	
	/**
	 * Initializes the DAO fields by getting them from the DAOFactory passed in from the constructor
	 * @param factory DAOFactory to initialize the DAO fields with
	 */
	private void init(DAOFactory factory){
		authDAO = factory.getAuthDAO();
		hrDAO = factory.getHealthRecordsDAO();
		ovDAO = factory.getOfficeVisitDAO();
		patientDAO = factory.getPatientDAO();
		
		loggingAction = new EventLoggingAction(factory);		
	}
	
	/**
	 * Gets the patient's MID as a long
	 * @return the patient's mid
	 */
	public long getPatientID(){
		return patientID;
	}
	
	/**
	 * Get the patient's name as a String
	 * @return the patient's name
	 * @throws ITrustException
	 */
	public String getPatientName() throws ITrustException{
		return patientDAO.getName(patientID);
	}
	
	/**
	 * Gets all of a patient's health records. Returns an ordered list starting from when the patient is youngest 
	 * to oldest at the time the health record was taken.
	 * @return list of health records ordered by oldest to most recent
	 * @throws ITrustException
	 */
	public List<HealthRecord> getAllPatientHealthRecords() throws ITrustException{
		
		if(user.getUserRolesString().equals("patient")){
			//Log for a patient
			loggingAction.logEvent(TransactionType.PATIENT_VIEW_BASIC_HEALTH_METRICS, loggedInMID, patientID, "");
		}
		if(user.getUserRolesString().equals("hcp")){
			//Log for an HCP
			loggingAction.logEvent(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, loggedInMID, patientID, "");
			
		}
		//Get the patient's date of birth
		Date dateOfBirth = patientDAO.getPatient(patientID).getDateOfBirth();
		
		//Age 3 date
		Calendar ovDateLower = Calendar.getInstance();
		ovDateLower.setTime(dateOfBirth);
		ovDateLower.add(Calendar.YEAR, 3);
		
		//Age 12 date
		Calendar ovDateUpper = Calendar.getInstance();
		ovDateUpper.setTime(dateOfBirth);
		ovDateUpper.add(Calendar.YEAR, 12);		
		
		List<HealthRecord> adultList = hrDAO.getAllRecordsAfterOVDate(patientID, ovDateUpper.getTime());
		List<HealthRecord> youthList = hrDAO.getAllRecordsBetweenOVDates(patientID, ovDateLower.getTime(), ovDateUpper.getTime());
		List<HealthRecord> babyList = hrDAO.getAllRecordsBeforeOVDate(patientID, ovDateLower.getTime());
		
		youthList.addAll(adultList);
		babyList.addAll(youthList);
		
		List<HealthRecord> allList = babyList;
		
		return allList;		
	}
	
	/**
	 * Calculates the percentile based on a z score. Turns the z score into a string that has significant figures to
	 * two decimal places. Uses the z score value up to the first decimal digit to get a normal bean from the database.
	 * After getting the normal bean, the percentile is obtained from the bean based on the last digit (value from the 
	 * second decimal place). 
	 * @param zScore the z-score to get the percentile for as a double. Z score should be an up to 2 decimal digit value.
	 * @return The percentile obtained from the z-score value
	 * -1 is returned if there is an error finding the z score in the database.
	 * @throws DBException 
	 */
	public double getPercentile(double zScore) throws DBException {
		if (zScore < MIN_Z) {
			return 0.0;
		} else if (zScore > MAX_Z) {
			return 100.0;
		}
		
		//Convert the z score into a string rounded to two decimal places
		String zString = String.format("%.2f", zScore);
		//Get the normal bean for the z score based on the z score value up to the first decimal digit
		NormalBean normal = normalDAO.getNormal(Double.parseDouble(zString.substring(0, zString.length() - 1)));
		//If the z score cannot be found in the database, return -1
		if (normal == null)
			return -1;
		
		//Double value for holding the percentile
		double percentile;
		
		//Get the percentile based on the last decimal digit value 
		switch (Integer.parseInt(zString.substring(zString.length() - 1))) {
			case 1:
				percentile = normal.get_01();
				break;
			case 2:
				percentile = normal.get_02();
				break;
			case 3:
				percentile = normal.get_03();
				break;
			case 4:
				percentile = normal.get_04();
				break;
			case 5:
				percentile = normal.get_05();
				break;
			case 6:
				percentile = normal.get_06();
				break;
			case 7:
				percentile = normal.get_07();
				break;
			case 8:
				percentile = normal.get_08();
				break;
			case 9:
				percentile = normal.get_09();
				break;
			default:
				//If the value is not 1-9 then it is 0
				percentile = normal.get_00();
				break;
		}
		
		//Multiply the percentile value by 100 to actually get a percent value
		percentile = percentile * 100;
		//Format the percent to two decimal places
		percentile = Double.parseDouble(String.format("%.2f", percentile));
		
		//Return the percentile
		return percentile;
	}
	
	/**
	 * Calculates the z score for a patient's weight depending on his or her age and sex. References
	 * values from the cdc weight statistics table. Takes the L (Box-Cox transformation), M (median), and
	 * S (generalized coefficient of variation) and calculates the z-score using the following equation:
	 * Z = ((X / M) ^ L - 1) / (L * S), if L != 0
	 * and
	 * Z = ln(X / M) / S, if L == 0
	 * where X is the patient's weight in kilograms. 
	 * After the z score is calculated, it is rounded to two decimal places before it is returned.
	 * @param record the HealthRecord to get the patient's weight z score for
	 * @return the z score of the patient's weight at the office visit's rounded to two decimal places.
	 * -999 is also returned if there is no matching CDC stats entry in the database.
	 * @throws DBException
	 */
	public double getWeightZScore(HealthRecord record) throws DBException {
		//Get the CDCStatsBean for a patient's weight at his or her age
		CDCStatsBean statsBean = getCDCStatsBean(record, weightStatsDAO);
		//If there is no matching entry in the CDC Stats table then return -999
		if (statsBean == null)
			return -999;
		
		//Get the weight in kilograms
		double weight = record.getWeight() / POUNDS_IN_KG;
		
		//Get the z score for the patient's weight
		double zScore = getZScore(statsBean, weight);
		
		return zScore;
	}
	
	/**
	 * Calculates the z score for a patient's height depending on his or her age and sex. References
	 * values from the cdc height statistics table. Takes the L (Box-Cox transformation), M (median), and
	 * S (generalized coefficient of variation) and calculates the z-score using the following equation:
	 * Z = ((X / M) ^ L - 1) / (L * S), if L != 0
	 * and
	 * Z = ln(X / M) / S, if L == 0
	 * where X is the patient's height in centimeters.
	 * After the z score is calculated, it is rounded to two decimal places before it is returned.
	 * @param record the HealthRecord to get the patient's height z score for
	 * @return the z score of the patient's height at the office visit's rounded to two decimal places.
	 * -999 is also returned if there is no matching CDC stats entry in the database.
	 * @throws DBException
	 */
	public double getHeightZScore(HealthRecord record) throws DBException {
		//Get the CDCStatsBean for a patient's height at his or her age
		CDCStatsBean statsBean = getCDCStatsBean(record, heightStatsDAO);
		//If there is no matching entry in the CDC Stats table then return -999
		if (statsBean == null)
			return -999;
		
		//Get the height in centimeters
		double height = record.getHeight() / INCHES_IN_CM;
		
		//Get the z score for the patient's height
		double zScore = getZScore(statsBean, height);
		
		return zScore;
	}
	
	/**
	 * Calculates the z score for a patient's head circumference depending on his or her age and sex. References
	 * values from the cdc head circumference statistics table. Takes the L (Box-Cox transformation), M (median), and
	 * S (generalized coefficient of variation) and calculates the z-score using the following equation:
	 * Z = ((X / M) ^ L - 1) / (L * S), if L != 0
	 * and
	 * Z = ln(X / M) / S, if L == 0
	 * where X is the patient's head circumference in centimeters.
	 * After the z score is calculated, it is rounded to two decimal places before it is returned.
	 * @param record the HealthRecord to get the patient's head circumference z score for
	 * @return the z score of the patient's head circumference at the office visit's rounded to two decimal places
	 * -999 is also returned if there is no matching CDC stats entry in the database.
	 * @throws DBException
	 */
	public double getHeadCircZScore(HealthRecord record) throws DBException {
		//Get the CDCStatsBean for a patient's head circumference at his or her age
		CDCStatsBean statsBean = getCDCStatsBean(record, headCircStatsDAO);
		//If there is no matching entry in the CDC Stats table then return -999
		if (statsBean == null)
			return -999;
		
		//Get the head cirumference in centimeters
		double headCirc = record.getHeadCircumference() / INCHES_IN_CM;
		
		//Get the z score for the patient's head circumference
		double zScore = getZScore(statsBean, headCirc);
		
		return zScore;
	}
	
	/**
	 * Calculates the z score for a patient's bmi depending on his or her age and sex. References
	 * values from the cdc bmi statistics table. Takes the L (Box-Cox transformation), M (median), and
	 * S (generalized coefficient of variation) and calculates the z-score using the following equation:
	 * Z = ((X / M) ^ L - 1) / (L * S), if L != 0
	 * and
	 * Z = ln(X / M) / S, if L == 0
	 * where X is the patient's bmi in centimeters.
	 * After the z score is calculated, it is rounded to two decimal places before it is returned.
	 * @param record the HealthRecord to get the patient's bmi z score for
	 * @return the z score of the patient's bmi at the office visit's rounded to two decimal places
	 * -999 is also returned if there is no matching CDC stats entry in the database.
	 * @throws DBException
	 */
	public double getBMIZScore(HealthRecord record) throws DBException {
		//Get the CDCStatsBean for a patient's BMI at his or her age
		CDCStatsBean statsBean = getCDCStatsBean(record, bmiStatsDAO);
		//If there is no matching entry in the CDC Stats table then return -999
		if (statsBean == null)
			return -999;
		
		//Get the bmi of the patient
		double bmi = record.getBodyMassIndex();
		
		//Get the z score for the patient's bmi
		double zScore = getZScore(statsBean, bmi);
		
		return zScore;
	}
	
	/**
	 * Gets a CDCStatsBean based on a patient's age, sex, and health metric. Reads in a CDCStatsDAO that 
	 * determines what database the bean is taken from.
	 * @param record the HealthRecord object that a patient's age is calculated from
	 * @param statsDAO the CDCStatsDAO to get a the CDCStatsBean from
	 * @return a CDCStatsBean based on the patient's age, sex, and health metric
	 * @throws DBException
	 */
	private CDCStatsBean getCDCStatsBean(HealthRecord record, CDCStatsDAO statsDAO) throws DBException {
		//Integer for holding the patient's gender
		int sex = 0;
		//Get the patient's gender
		if (patientDAO.getPatient(patientID).getGender().toString().equals("Male")) {
			sex = MALE;
		} else {
			sex = FEMALE;
		}
		
		//Get the patient's age
		float age = 0;
		if (record.getVisitDate().equals(patientDAO.getPatient(patientID).getDateOfBirth())) {
			//If the patient's birthdate equals the office visit date, 
			//the patient's age is 0 months
			age = (float) 0.0;
		} else {
			//Otherwise add 0.5 to the patient's age in months
			age = (float) (calculateAgeInMonthsAtOfficeVisit(record.getOfficeVisitID()) + 0.5);
		}
		
		//Get the CDCStatsBean for a patient's health metric at his or her age
		CDCStatsBean statsBean = statsDAO.getCDCStats(sex, age);
		
		if (statsBean == null && age != 0) {
			//If there is no match with the specified age and sex, 
			//query again with the age rounded down as a whole number
			statsBean = statsDAO.getCDCStats(sex, (float) Math.floor(age));
		}
		
		return statsBean;
	}
	
	/**
	 * Calculates the z score for a patient's health metric. Takes the L (Box-Cox transformation), M (median), 
	 * and S (generalized coefficient of variation) from the passed in CDCStatsBean and calculates the z-score 
	 * using the following equation:
	 * Z = ((X / M) ^ L - 1) / (L * S), if L != 0
	 * and
	 * Z = ln(X / M) / S, if L == 0
	 * where X is the patient's health metric in metric units (Centimeters for height and head circumference. 
	 * Kilograms for weight). After the z score is calculated, it is rounded to two decimal places before it 
	 * is returned.
	 * @param statsBean The stats bean for 
	 * @param healthMetric value for the health metric to get a z score for. 
	 * (In cm for height and head circumference. In kg for weight)
	 * @return the z score for the health metric value based on the L, M, and S values from the CDCStatsBean.
	 */
	private double getZScore(CDCStatsBean statsBean, double healthMetric) {
		//Get the L, M, and S values for a patient's bmi at his or her age
		double L = statsBean.getL();
		double M = statsBean.getM();
		double S = statsBean.getS();
		
		//Double for holding the z-score
		double zScore;
		
		if (L == 0.0) {
			//If L is 0 then calculate the z score differently
			zScore = Math.log(healthMetric / M) / S;
		} else {
			zScore = (Math.pow((healthMetric / M), L) - 1) / (L * S);
		} 
		
		//Round the z score to two decimal places
		zScore = Double.parseDouble(String.format("%.2f", zScore));
		
		return zScore;
	}
	
	/**
	 * Returns the patient's gender for use in percentile charting.
	 * @return patient gender as a string: "Male" or "Female"
	 * @throws DBException
	 */
	public String getPatientGender() throws DBException{
		return patientDAO.getPatient(patientID).getGender().toString();
		
	}
	
	/**
	 * Calculates the patient's age during an office visit. Reads in an office visit ID and gets 
	 * its visit date to check against the patient's birthday. Returns the patient's age in months.
	 * @param ovID long for the office visit ID of the office visit to get the patient's age for
	 * @return the patient's age in months
	 * @throws DBException
	 */
	public int calculateAgeInMonthsAtOfficeVisit(long ovID) throws DBException{
		//Create int for patient's age in months
		int ageInMonths = 0;
		
		//Get office visit information		
		Calendar ovDate = Calendar.getInstance();
		OfficeVisitBean ov = ovDAO.getOfficeVisit(ovID);
		ovDate.setTime(ov.getVisitDate());		
		
		//Get the patient's birthdate	
		Calendar dateOfBirth = Calendar.getInstance();
		PatientBean patient = patientDAO.getPatient(patientID);
		dateOfBirth.setTime(patient.getDateOfBirth());
		
		//Split the patient's birthdate into day, month, and year
		int birthDay = dateOfBirth.get(Calendar.DAY_OF_MONTH);
		int birthMonth = dateOfBirth.get(Calendar.MONTH) + 1;
		int birthYear = dateOfBirth.get(Calendar.YEAR);
		//Split the office visit date into day month and year
		int visitDay = ovDate.get(Calendar.DAY_OF_MONTH);
		int visitMonth = ovDate.get(Calendar.MONTH) + 1;
		int visitYear = ovDate.get(Calendar.YEAR);
		
		//Calculate the year, month, and day differences
		int yearDiff = visitYear - birthYear;
		int monthDiff = visitMonth - birthMonth;
		int dayDiff = visitDay - birthDay;
		
		//Get the patient's age in months by multiplying the year difference by 12
		//and adding the month difference
		ageInMonths = yearDiff * MONTHS_IN_YEAR + monthDiff;
		
		//If the day difference is negative, subtract a month from the age
		if (dayDiff < 0) {
			ageInMonths -= 1;
		}
		
		//Return the age in months
		return ageInMonths;
		
	}	

}
