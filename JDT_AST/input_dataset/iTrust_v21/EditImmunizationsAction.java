package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CPTCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.ProceduresDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Handle immunization for the patients
 * Edit Immunizations
 * Add Immunizations
 * Delete Immunizations
 *  
 *  
 */
public class EditImmunizationsAction extends EditOfficeVisitBaseAction {

	private ProceduresDAO proceduresDAO;
	private CPTCodesDAO cptDAO;
	
	public EditImmunizationsAction(DAOFactory factory, long hcpid, 
			   	String pidString, String ovIDString) 
		throws ITrustException {
		super(factory, hcpid, pidString, ovIDString);
		proceduresDAO = factory.getProceduresDAO();
		cptDAO = factory.getCPTCodesDAO();
	}
	
	public EditImmunizationsAction(DAOFactory factory, long hcpid, 
				String pidString) 
		throws ITrustException {
		super(factory, hcpid, pidString);
		proceduresDAO = factory.getProceduresDAO();
		cptDAO = factory.getCPTCodesDAO();
	}
	
	public List<ProcedureBean> getImmunizations() throws DBException {
		if (isUnsaved()) {
			return new ArrayList<ProcedureBean>();
		} else {
			return proceduresDAO.getImmunizationList(getOvID());
		}
	}
	
	public void addImmunization(ProcedureBean bean) throws ITrustException {
		verifySaved();
		proceduresDAO.add(bean);
	}
	
	public void editImmunization(ProcedureBean bean) throws ITrustException {
		verifySaved();
		proceduresDAO.edit(bean);
	}
	
	public void deleteImmunization(ProcedureBean bean) throws ITrustException {
		verifySaved();
		proceduresDAO.remove(bean.getOvProcedureID());
	}
	
	public List<ProcedureBean> getImmunizationCodes() throws DBException {
		return cptDAO.getImmunizationCPTCodes();
	}
	
}
