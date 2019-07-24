package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LOINCDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Handles lab procedures
 * add lab procedure
 * Edit lab procedure
 * Remove lab procedure
 */
public class EditLabProceduresAction extends EditOfficeVisitBaseAction {
	
	private LabProcedureDAO labProcedureDAO;
	private PersonnelDAO personnelDAO;
	private LOINCDAO loincDAO;
	
	/**
	 * EditLabProceduresAction
	 * @param factory factory
	 * @param hcpid hcpid
	 * @param pidString pidString
	 * @param ovIDString ovIDString
	 * @throws ITrustException
	 */
	public EditLabProceduresAction(DAOFactory factory, long hcpid, 
			   	String pidString, String ovIDString) 
		throws ITrustException {
		super(factory, hcpid, pidString, ovIDString);
		labProcedureDAO = factory.getLabProcedureDAO();
		personnelDAO = factory.getPersonnelDAO();
		loincDAO = factory.getLOINCDAO();
	}
	
	/**
	 * EditLabProceduresAction
	 * @param factory factory
	 * @param hcpid hcpid
	 * @param pidString pidString
	 * @throws ITrustException
	 */
	public EditLabProceduresAction(DAOFactory factory, long hcpid, 
				String pidString) 
		throws ITrustException {
		super(factory, hcpid, pidString);
		labProcedureDAO = factory.getLabProcedureDAO();
		personnelDAO = factory.getPersonnelDAO();
		loincDAO = factory.getLOINCDAO();
		
	}
	
	/**
	 * getLabProcedures
	 * @return list
	 * @throws DBException
	 */
	public List<LabProcedureBean> getLabProcedures() throws DBException {
		if (isUnsaved()) {
			return new ArrayList<LabProcedureBean>();
		} else {
			return labProcedureDAO.getAllLabProceduresForDocOV(getOvID());
		}
	}
	
	/**
	 * getLabProcedure
	 * @param id id
	 * @return lab procedure
	 * @throws ITrustException
	 */
	public LabProcedureBean getLabProcedure(long id) throws ITrustException {
		verifySaved();
		return labProcedureDAO.getLabProcedure(id);
	}
	
	/**
	 * addLabProcedure
	 * @param bean bean
	 * @throws ITrustException
	 */
	public void addLabProcedure(LabProcedureBean bean) throws ITrustException {
		verifySaved();
		//choose lab tech if not assigned in bean
		if ("".equals(bean.getStatus())) {
			bean.setStatus(LabProcedureBean.In_Transit);
		}
		labProcedureDAO.addLabProcedure(bean);
	}
	
	/**
	 * editLabProcedure
	 * @param bean bean
	 * @throws ITrustException
	 */
	public void editLabProcedure(LabProcedureBean bean) throws ITrustException {
		verifySaved();
		labProcedureDAO.updateLabProcedure(bean);
	}
	
	/**
	 * deleteLabProcedure
	 * @param bean bean
	 * @throws ITrustException
	 */
	public void deleteLabProcedure(LabProcedureBean bean) throws ITrustException {
		verifySaved();
		labProcedureDAO.removeLabProcedure(bean.getProcedureID());
	}
	
	/**
	 * getLabTechs
	 * @return lab techs
	 * @throws ITrustException
	 */
	public List<PersonnelBean> getLabTechs() throws ITrustException {
		return personnelDAO.getLabTechs();
	}
	
	/**
	 * getLabTechName
	 * @param mid mid
	 * @return ""
	 * @throws ITrustException
	 */
	public String getLabTechName(long mid) throws ITrustException {
		try {
			return personnelDAO.getName(mid);
		} catch (ITrustException e) {
			return "";
		}
	}
	
	/**
	 * getLabTechQueueSize
	 * @param mid mid
	 * @return lab tech queue size
	 * @throws ITrustException
	 */
	public int getLabTechQueueSize(long mid) throws ITrustException {
		return labProcedureDAO.getLabTechQueueSize(mid);
	}
	
	/**
	 * getLabTechQueueSizeByPriority
	 * @param mid mid
	 * @return lab tech queue size by priority
	 * @throws ITrustException
	 */
	public int[] getLabTechQueueSizeByPriority(long mid) throws ITrustException {
		return labProcedureDAO.getLabTechQueueSizeByPriority(mid);
	}
	
	/**
	 * getLabProcedureCodes
	 * @return get all loinc
	 * @throws DBException
	 */
	public List<LOINCbean> getLabProcedureCodes() throws DBException {
		return loincDAO.getAllLOINC();
	}
	
}
