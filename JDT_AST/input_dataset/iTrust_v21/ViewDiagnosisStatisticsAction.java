package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for the View Diagnosis Statistics page. Can return a list of all Diagnoses
 * and get diagnosis statistics for a specified Zip code, Diagnosis code, and date range.
 */
public class ViewDiagnosisStatisticsAction {
	/** Database access methods for ICD codes (diagnoses) */
	private ICDCodesDAO icdDAO;
	/** Database access methods for diagnosis information */
	private DiagnosesDAO diagnosesDAO;
	/** ICD Code for malaria */
	private static final String ICD_MALARIA = "84.50";
	/** ICD Code for Influenza */
	private static final String ICD_INFLUENZA = "487.00";
	
	/**
	 * Constructor for the action. Initializes DAO fields
	 * @param factory The session's factory for DAOs
	 */
	public ViewDiagnosisStatisticsAction(DAOFactory factory) {
		this.icdDAO = factory.getICDCodesDAO();
		this.diagnosesDAO = factory.getDiagnosesDAO();
	}
	
	/**
	 * Gets all the diagnosis codes in iTrust and returns them in a list of beans.
	 * 
	 * @return List of DiagnosisBeans correlating to all ICDCodes
	 * @throws ITrustException
	 */
	public List<DiagnosisBean> getDiagnosisCodes() throws ITrustException  {
		return icdDAO.getAllICDCodes();
	}
	
	/**
	 * Gets the counts of local and regional diagnoses for the specified input
	 * 
	 * @param lowerDate The beginning date for the time range
	 * @param upperDate The ending date for the time range
	 * @param icdCode The diagnosis code to examine
	 * @param zip The zip code to examine
	 * @return A bean containing the local and regional counts
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	public DiagnosisStatisticsBean getDiagnosisStatistics(String lowerDate, String upperDate, String icdCode, String zip) throws FormValidationException, ITrustException {
		DiagnosisStatisticsBean dsBean;
		try {
			
			if (lowerDate == null || upperDate == null || icdCode == null)
				return null;
			
			Date lower = new SimpleDateFormat("MM/dd/yyyy").parse(lowerDate);
			Date upper = new SimpleDateFormat("MM/dd/yyyy").parse(upperDate);

			if (lower.after(upper))
				throw new FormValidationException("Start date must be before end date!");
			
			if (!zip.matches("([0-9]{5})|([0-9]{5}-[0-9]{4})"))
				throw new FormValidationException("Zip Code must be 5 digits!");

			boolean validCode = false;
			for(DiagnosisBean diag : getDiagnosisCodes()) {
					if (diag.getICDCode().equals(icdCode))
						validCode = true;
			}
			if (validCode == false) {
				throw new FormValidationException("ICDCode must be valid diagnosis!");
			}

			dsBean = diagnosesDAO.getDiagnosisCounts(icdCode, zip, lower, upper);
			
		} catch (ParseException e) {
			throw new FormValidationException("Enter dates in MM/dd/yyyy");
		} 
		
		
		return dsBean;
	}
	
	/**
	 * Gets the local and regional counts for the specified week and calculates the prior average.
	 * 
	 * @param startDate a date in the week to analyze
	 * @param icdCode the diagnosis to analyze
	 * @param zip the area to analyze
	 * @param threshold threshold
	 * @return statistics for the week and previous averages
	 * @throws FormValidationException
	 * @throws DBException
	 */
	public ArrayList<DiagnosisStatisticsBean> getEpidemicStatistics(String startDate, String icdCode, String zip, String threshold) throws FormValidationException, DBException {
		
		if (startDate == null || icdCode == null)
			return null;
		
		if (!(icdCode.equals("84.50") || icdCode.equals("487.00")) ) {
			throw new FormValidationException("Exception");
		}
		if(ICD_MALARIA.equals(icdCode)){
			try{
				Integer.parseInt(threshold);
			}catch(NumberFormatException e){
				throw new FormValidationException("Threshold must be an integer.");
			}
		}
		Date lower;  //lower, which is parsed to startDate
		try {
			lower = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
		} catch (ParseException e) {
			throw new FormValidationException("Enter dates in MM/dd/yyyy");
		}
		if (!zip.matches("([0-9]{5})|([0-9]{5}-[0-9]{4})"))
			throw new FormValidationException("Zip Code must be 5 digits!");
		
		DiagnosisStatisticsBean dbWeek = diagnosesDAO.getCountForWeekOf(icdCode, zip, lower);
		DiagnosisStatisticsBean dbAvg = new DiagnosisStatisticsBean(zip, 0, 0, lower, lower);
		
		Calendar cal = Calendar.getInstance();
		
		Date start = diagnosesDAO.findEarliestIncident(icdCode); //start, which is set to earliest incident
		Calendar startCal = Calendar.getInstance();
		if(start != null)
			startCal.setTime(start);
		
		ArrayList<DiagnosisStatisticsBean> ret = new ArrayList<DiagnosisStatisticsBean>();
		if (start == null) {
			ret.add(dbWeek);
			ret.add(dbAvg);
			return ret;
		}
		cal.setTime(lower); //cal, which is set to lower
		Calendar lowerCal = Calendar.getInstance();
		lowerCal.setTime(lower);
		int weekOfYr = cal.get(Calendar.WEEK_OF_YEAR);
		
		cal.set(Calendar.YEAR, startCal.get(Calendar.YEAR));  //cal's year then gets set to start's year
		ArrayList<DiagnosisStatisticsBean> dbList = new ArrayList<DiagnosisStatisticsBean>();
		
		while( cal.getTime().before(lower) && cal.get(Calendar.YEAR) != lowerCal.get(Calendar.YEAR)) {
			dbList.add( diagnosesDAO.getCountForWeekOf(icdCode, zip, cal.getTime()) );
			cal.add(Calendar.YEAR, 1);
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYr);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		
		long avg = 0;
		long avgRegion = 0;
		if (dbList.size() > 0) {
			for (DiagnosisStatisticsBean d : dbList) {
				avg += d.getZipStats();
				avgRegion += d.getRegionStats();
			}
			avg /= dbList.size();
			avgRegion /= dbList.size();
		}
		
		dbAvg.setRegionStats(avgRegion);
		dbAvg.setZipStats(avg);
		
		ret.add(dbWeek);
		ret.add(dbAvg);
		return ret;
	}
	
	/**
	 * Determines if an Influenza Epidemic is happening
	 * 
	 * @param curDateStr a date in the currently evaluated week
	 * @param zip the zip code to analyze
	 * @return whether or not there is an epidemic
	 * @throws ParseException
	 * @throws DBException
	 */
	public boolean isFluEpidemic(String curDateStr, String zip) throws ParseException, DBException {
		new SimpleDateFormat("MM/dd/yyyy").parse("01/04/1998");
		Date curDate = new SimpleDateFormat("MM/dd/yyyy").parse(curDateStr);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		
		int weekOfYr = cal.get(Calendar.WEEK_OF_YEAR);
		double threshold = calcThreshold(weekOfYr);
		double thresholdL = calcThreshold(weekOfYr-1);
		double thresholdN = calcThreshold(weekOfYr+1); 
		
		DiagnosisStatisticsBean dbNow = diagnosesDAO.getCountForWeekOf(ICD_INFLUENZA, zip, cal.getTime());
		cal.add(Calendar.HOUR, -12*7);
		DiagnosisStatisticsBean dbLast = diagnosesDAO.getCountForWeekOf(ICD_INFLUENZA, zip, cal.getTime());
		cal.add(Calendar.HOUR, 2*12*7);
		DiagnosisStatisticsBean dbNext =  diagnosesDAO.getCountForWeekOf(ICD_INFLUENZA, zip, cal.getTime());
		
		double weekNow = (double) dbNow.getRegionStats();
		double weekL = (double) dbLast.getRegionStats();
		double weekN = (double) dbNext.getRegionStats();
		
		return weekNow > threshold && (weekL > thresholdL || weekN > thresholdN);
		
	}
	
	/**
	 * Calculates the threshold of an influenza epidemic
	 * 
	 * @param weekNumber the week of the year
	 * @return the epidemic threshold for flu cases
	 */
	private double calcThreshold(double weekNumber) {
		return 5.34 + 0.271 * weekNumber + 3.45 * Math.sin(2 * Math.PI * weekNumber / 52.0) + 8.41 * Math.cos(2 * Math.PI * weekNumber / 52.0);
	}
	
	/**
	 * Determines whether a Malaria epidemic is happening
	 * 
	 * @param weekDate a date in the currently evaluated week
	 * @param zip the zip code to analyze
	 * @param thresholdStr the threshold for an epidemic
	 * @return whether or not there is an epidemic
	 * @throws DBException
	 * @throws ParseException
	 */
	public boolean isMalariaEpidemic(String weekDate, String zip, String thresholdStr) throws DBException, ParseException {
		
		Date wkDate = new SimpleDateFormat("MM/dd/yyyy").parse(weekDate);
		
		ArrayList<DiagnosisStatisticsBean> dbList = new ArrayList<DiagnosisStatisticsBean>();
		ArrayList<DiagnosisStatisticsBean> dbListL = new ArrayList<DiagnosisStatisticsBean>();
		ArrayList<DiagnosisStatisticsBean> dbListN = new ArrayList<DiagnosisStatisticsBean>();
		int threshold = Integer.parseInt(thresholdStr);
		DiagnosisStatisticsBean current = diagnosesDAO.getCountForWeekOf(ICD_MALARIA, zip, wkDate);
		long weekTotal = current.getRegionStats();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(wkDate);
		cal.add(Calendar.HOUR, -7*24);
		DiagnosisStatisticsBean last = diagnosesDAO.getCountForWeekOf(ICD_MALARIA, zip, cal.getTime());
		long weekTotalL = last.getRegionStats();
		cal.add(Calendar.HOUR, 2*7*24);
		DiagnosisStatisticsBean next = diagnosesDAO.getCountForWeekOf(ICD_MALARIA, zip, cal.getTime());
		long weekTotalN = next.getRegionStats();
		
		cal.setTime(wkDate);
		int weekOfYr = cal.get(Calendar.WEEK_OF_YEAR);
		
		//Find earliest Malaria Case. Set calendar's year to that year
		Date startData = diagnosesDAO.findEarliestIncident(ICD_MALARIA);
		if (startData == null) {
			if (current.getRegionStats() > 0) {
				return true;
			}
			return false;
		}
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(startData);
		Calendar wkDateCal = Calendar.getInstance();
		wkDateCal.setTime(wkDate);
		cal.set(Calendar.YEAR, startDateCal.get(Calendar.YEAR));

		while( cal.getTime().before(wkDate) && cal.get(Calendar.YEAR) != wkDateCal.get(Calendar.YEAR)) {
			
			dbList.add( diagnosesDAO.getCountForWeekOf(ICD_MALARIA, zip, cal.getTime()) );
			cal.add(Calendar.HOUR, -7*24);
			dbListL.add( diagnosesDAO.getCountForWeekOf(ICD_MALARIA, zip, cal.getTime()) );
			cal.add(Calendar.HOUR, 2*7*24);
			dbListN.add( diagnosesDAO.getCountForWeekOf(ICD_MALARIA, zip, cal.getTime()) );
			cal.add(Calendar.YEAR, 1);
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYr);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		
		long total = 0;
		for (DiagnosisStatisticsBean d : dbList) {
			total += d.getRegionStats();
		}
		for (DiagnosisStatisticsBean d : dbListL) {
			d.getRegionStats();
		}
		for (DiagnosisStatisticsBean d : dbListN) {
			d.getRegionStats();
		}
		
		long avg = 0;
		long avgL = 0;
		long avgN = 0;
		if (dbList.size() != 0) {
			avg = total / dbList.size();
			avgL = total/ dbListL.size();
			avgN = total/ dbListN.size();
		} 
			
		return weekTotal != 0 && (weekTotal*100/threshold) > avg && 
				(	( weekTotalL != 0 && (weekTotalL*100/threshold) > avgL ) || 
					( weekTotalN != 0 && (weekTotalN*100/threshold) > avgN ) );
	}
}
