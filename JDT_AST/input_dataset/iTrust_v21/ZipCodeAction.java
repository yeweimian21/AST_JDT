package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.ZipCodeBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ZipCodeDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Action class which handles zip code related functionality;
 */
public class ZipCodeAction 
{
//	private DAOFactory factory;
//	private long loggedInMID;
	private ZipCodeDAO zipCodeDAO;
//	private PersonnelDAO personnelDAO;
	private HospitalsDAO hospitalDAO;
	private FindExpertAction expertAction;
	
	/**
	 * Constructor for ZipCodeAction
	 * @param factory
	 * @param loggedInMID
	 */
	public ZipCodeAction(DAOFactory factory, long loggedInMID)
	{
		this.zipCodeDAO = factory.getZipCodeDAO();
		this.hospitalDAO = factory.getHospitalsDAO();
		this.expertAction = new FindExpertAction(factory);
	}
	
	/**
	 * Calculates the distance between two ZipCodes
	 * @param zipCode1
	 * @param zipCode2
	 * @return
	 * @throws DBException
	 */
	public int calcDistance(String zipCode1, String zipCode2) throws DBException
	{
		ZipCodeBean bean1 = zipCodeDAO.getZipCode(zipCode1);
		ZipCodeBean bean2 = zipCodeDAO.getZipCode(zipCode2);
		if(bean1 == null || bean2 == null)
		{	
			return Integer.MAX_VALUE;
		}
		double latA = Double.valueOf(bean1.getLatitude());
		double longA = Double.valueOf(bean1.getLongitude());
		double latB = Double.valueOf(bean2.getLatitude());
		double longB = Double.valueOf(bean2.getLongitude());
		
		 double theDistance = (Math.sin(Math.toRadians(latA)) *
                 Math.sin(Math.toRadians(latB)) +
                 Math.cos(Math.toRadians(latA)) *
                 Math.cos(Math.toRadians(latB)) *
                 Math.cos(Math.toRadians(longA - longB)));

		 return (int) ((Math.toDegrees(Math.acos(theDistance))) * 69.09);
	}
	
	
	/**
	 * Returns all of the hospitals within the mileage range specified.
	 * @param specialty
	 * @param zipCode
	 * @param mileRange
	 * @return
	 * @throws DBException
	 */
	private List<HospitalBean> getHosptialsWithinCertainMileage(String specialty, String zipCode, String mileRange) throws DBException
	{
		List<HospitalBean> hospitalBeans = hospitalDAO.getAllHospitals();
		List<HospitalBean> hospitalsWithinRange = new ArrayList<HospitalBean>();
		int miles;
		for (HospitalBean hospitalBean : hospitalBeans) {
			if(mileRange.equals("All"))
				miles = Integer.MAX_VALUE;
			else	
				miles = Integer.parseInt(mileRange);		
			if(calcDistance(zipCode, hospitalBean.getHospitalZip()) <= miles)
			{
				hospitalsWithinRange.add(hospitalBean);
			}
		}
		return hospitalsWithinRange;
		
	}
	
	private List<PersonnelBean> getExpertsForHospitals(String specialty, List<HospitalBean> hospitalBeans)
	{
		return expertAction.findExpertsForLocalHospitals(hospitalBeans, specialty);
	}
	
	/**
	 * Gets all the experts within a certain range and with a certain specialty.
	 * @param specialty
	 * @param zipCode
	 * @param mileRange
	 * @return
	 * @throws DBException
	 */
	public List<PersonnelBean> getExperts(String specialty, String zipCode, String mileRange) throws DBException
	{
		List<HospitalBean> hosptials = getHosptialsWithinCertainMileage(specialty, zipCode, mileRange);
		return getExpertsForHospitals(specialty, hosptials);
		
	}
	
	
	
	
}
