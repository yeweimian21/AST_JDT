package edu.ncsu.csc.itrust.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientInstructionsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Allow a patient to view his patient-specific instructions.
 */
public class ViewPatientInstructionsAction extends PatientBaseAction {
	
	private PatientInstructionsDAO instructionsDAO;
	private PersonnelDAO personnelDAO;
	private OfficeVisitDAO ovDAO;
	
	/**
	 * Construct a ViewPatientInstructionsAction for a given patient.
	 * @param factory The DAO Factory.
	 * @param pidString The patient ID as a string.
	 * @throws ITrustException
	 */
	public ViewPatientInstructionsAction(DAOFactory factory, String pidString) throws ITrustException {
		super(factory, pidString);
		this.instructionsDAO = new PatientInstructionsDAO(factory);
		this.personnelDAO = new PersonnelDAO(factory);
		this.ovDAO = new OfficeVisitDAO(factory);
	}
	
	/**
	 * Get all office visits for this patient that contain instructions.
	 * @return A list of office visit beans. 
	 * @throws DBException
	 */
	public List<OfficeVisitBean> getOfficeVisitsWithInstructions() throws DBException {
		return instructionsDAO.getOfficeVisitsWithInstructions(getPid());
	}
	
	/**
	 * Get a list of patient instructions for a given office vsiit.
	 * @param ovid The office visit id to check.
	 * @return A list of patient instructions beans.
	 * @throws DBException
	 */
	public List<PatientInstructionsBean> getInstructionsForOfficeVisit(long ovid) throws DBException {
		return instructionsDAO.getList(ovid);
	}
	
	/**
	 * Get a map in which to lookup HCP names from an HCP id.
	 * @return A map which associated HCP ids with their names.
	 * @throws ITrustException
	 */
	public Map<Long, String> getHCPNameLookup() throws ITrustException {
		HashMap<Long, String> map = new HashMap<Long, String>();
		List<OfficeVisitBean> ovisits = ovDAO.getAllOfficeVisits(getPid());
		for (OfficeVisitBean ovisit: ovisits) {
			if (!map.containsKey(ovisit.getHcpID())) {
				map.put(ovisit.getHcpID(), personnelDAO.getName(ovisit.getHcpID()));
			}
		}
		return map;
	}
	
}
