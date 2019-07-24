package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
/**
 * Class for LabProcLT.jsp.  Handles lab procedures for LTs
 */
public class LabProcLTAction {
	private LabProcedureDAO lpDAO;
	private OfficeVisitDAO ovDAO;
	private PersonnelDAO personDAO;
	/**
 * Setup 
 * @param factory The DAOFactory used to create the DAOs used in this action.
 */
	public LabProcLTAction(DAOFactory factory) {
		ovDAO = factory.getOfficeVisitDAO();
		lpDAO = factory.getLabProcedureDAO();
		personDAO = factory.getPersonnelDAO();
	}
	
	public List<LabProcedureBean> viewInTransitProcedures(long id) throws DBException{
		return lpDAO.getLabProceduresInTransitForLabTech(id);
	}
	
	public List<LabProcedureBean> viewReceivedProcedures(long id) throws DBException{
		return lpDAO.getLabProceduresReceivedForLabTech(id);
	}
	
	public List<LabProcedureBean> viewTestingProcedures(long id) throws DBException{
		return lpDAO.getLabProceduresTestingForLabTech(id);
	}
	
	public LabProcedureBean getLabProcedure(long id) throws DBException{
		return lpDAO.getLabProcedure(id);
	}
	
	public String getHCPName (long ovid) throws ITrustException {
		OfficeVisitBean b = ovDAO.getOfficeVisit(ovid);
		return personDAO.getName(b.getHcpID());
	}
	
	public Boolean submitResults(String id, String numericalResults, String numericalResultsUnit, String upperBound, String lowerBound) throws FormValidationException {
			try {
				long procedureID = Long.parseLong(id);
				LabProcedureBean lp = lpDAO.getLabProcedure(procedureID);
				lp.setNumericalResult(numericalResults);
				lp.setNumericalResultUnit(numericalResultsUnit);
				lp.setUpperBound(upperBound);
				lp.setLowerBound(lowerBound);
				lpDAO.submitTestResults(Long.parseLong(id), numericalResults, numericalResultsUnit, upperBound, lowerBound);
			} catch (NumberFormatException e) {
				
				return false;
			} catch (DBException e) {
				
				return false;
			}
			return true;
	}
	
	public Boolean submitReceived(String id) throws DBException{
		try {
			lpDAO.submitReceivedLP(Long.parseLong(id));
		} catch (NumberFormatException e) {
			
			return false;
		}
		return true;
	}
	
	public Boolean setToTesting(long id) throws DBException {
		lpDAO.setLPToTesting(id);
		return true;
	}
}
