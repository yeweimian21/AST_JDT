package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.ncsu.csc.itrust.ParameterUtil;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PrescriptionReportBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionReportDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.NoHealthRecordsException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Handles Prescription Reports for the given pid Used by hcp-uap/getPrescriptionReport.jsp,
 * hcp-uap/viewPrescriptionRecord.jsp, patient/getMyPrescriptionReport.jsp, &
 * patient/viewMyPrescriptionRecord.jsp
 * 
 * 
 */
public class PrescriptionReportAction extends PatientBaseAction {
	private boolean isRepresenting = false;
	private OfficeVisitDAO ovDAO;
	private PrescriptionReportDAO prDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;

	/**
	 * Super class validates pidString
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the user who is making a prescription report.
	 * @param pidString The MID of the patient in question.
	 * @throws ITrustException
	 * @throws DBException
	 * @throws NoHealthRecordsException
	 */
	public PrescriptionReportAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException, DBException, NoHealthRecordsException {
		super(factory, pidString);
		this.ovDAO = factory.getOfficeVisitDAO();
		this.patientDAO = factory.getPatientDAO();
		this.prDAO = factory.getPrescriptionReportDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Takes the patient's representee as a param and returns it as a long if the patient represents the input
	 * param
	 * 
	 * @param input
	 *            the patient's representee mid
	 * @return representee's mid as a long
	 * @throws ITrustException
	 */
	public long representPatient(String input) throws ITrustException {
		try {
			long reppeeMID = Long.valueOf(input);
			if (patientDAO.represents(loggedInMID, reppeeMID)) {
				loggedInMID = reppeeMID;
				pid = reppeeMID;
				isRepresenting = true;
				return reppeeMID;
			} else
				throw new ITrustException("You do not represent patient " + reppeeMID);
		} catch (NumberFormatException e) {
			throw new ITrustException("MID is not a number");
		}
	}

	/**
	 * Returns a list of all office visits for the pid
	 * 
	 * @return list of OfficeVisitBeans for the pid
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getAllOfficeVisits() throws DBException {
		return ovDAO.getAllOfficeVisits(pid);
	}

	/**
	 * Used by the JSP, passes a Map from the html form and a list of OfficeVisitBeans Returns a list of
	 * PrescriptionReportBeans
	 * 
	 * @param params A java.util.HashMap containing the parameter map.
	 * @param officeVisits A java.util.List of OfficeVisitBeans for the visits.
	 * @return list of PrescriptionReportBeans
	 * @throws DBException
	 */
	public List<PrescriptionReportBean> getPrescriptionReports(Map<String, String> params, List<OfficeVisitBean> officeVisits)
			throws DBException {
		HashMap<String, String> myParams = ParameterUtil.convertMap(params);
		List<Long> ovIDs = new ArrayList<Long>();
		for (int i = 0; i < officeVisits.size(); i++) {
			try {
				if (params.get("ovOff" + i) != null) {
					int offset = Integer.valueOf(myParams.get("ovOff" + i));
					ovIDs.add(officeVisits.get(offset).getVisitID());
				}
			} catch (NumberFormatException e) {
				// just skip it
			}
		}
		if (ovIDs.size() == 0)
			return new ArrayList<PrescriptionReportBean>();

		return prDAO.byOfficeVisitAndPatient(ovIDs, pid);
	}

	/**
	 * Returns a PatientBean for the pid
	 * 
	 * @return PatientBean
	 * @throws DBException
	 */
	public PatientBean getPatient() throws DBException {
		return patientDAO.getPatient(pid);
	}

	/**
	 * Used by the JSP, which passes the param map from the html form and a list of OfficeVisitBeans Returns a
	 * string that will be used to create a new url. The JSP will pull params from this url to create the
	 * prescription report.
	 * 
	 * @param paramMap A java.util.HashMap of the parameters.
	 * @param officeVisits A java.util.List of OfficeVisitBeans.
	 * @return the string that will be used in the new url
	 * @throws FormValidationException
	 * @throws DBException
	 */
	@SuppressWarnings("rawtypes")
	public String getQueryString(Map paramMap, List<OfficeVisitBean> officeVisits)
			throws FormValidationException, DBException {
		HashMap<String, String> myParams = ParameterUtil.convertMap(paramMap);
		List<Integer> ovOffsets = checkOfficeVisits(myParams, officeVisits);
		String queryString = buildQueryString(ovOffsets);
		if (isRepresenting)
			queryString += "&rep=" + pid;
		return queryString;
	}

	/**
	 * Checks office visits
	 * 
	 * @param myParams list of parameters
	 * @param officeVisits list of office visits
	 * @return Returns a java.util.ArrayList of Integers for the given office visits.
	 */
	private ArrayList<Integer> checkOfficeVisits(HashMap<String, String> myParams,
			List<OfficeVisitBean> officeVisits) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < officeVisits.size(); i++) {
			if ("on".equals(myParams.get("ov" + i)))
				list.add(i);
		}
		return list;
	}

	/**
	 * Builds a query string for office visits
	 * 
	 * @param ovOffsets offsets for the office visits
	 * @return A SQL query in a Java String.
	 */
	private String buildQueryString(List<Integer> ovOffsets) {
		int n = ovOffsets.size();
		if (n == 0)
			return "";
		String str = "&n=" + n;
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < ovOffsets.size(); i++) {
			buf.append("&ovOff" + i + "=" + ovOffsets.get(i));
		}
		str += buf.toString();
		return str;
	}
}
