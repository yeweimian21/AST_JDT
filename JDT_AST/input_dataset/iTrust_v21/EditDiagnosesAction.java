/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.action.base.EditOfficeVisitBaseAction;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Handle patients Diagnosis
 * Edit Diagnosis
 * Add Diagnosis
 * Remove Diagnosis
 *  
 *  
 */
public class EditDiagnosesAction extends EditOfficeVisitBaseAction {
	
	private DiagnosesDAO diagnosesDAO;
	private ICDCodesDAO icdDAO;
	
	public EditDiagnosesAction(DAOFactory factory, long hcpid, 
			   	String pidString, String ovIDString) 
		throws ITrustException {
		super(factory, hcpid, pidString, ovIDString);
		diagnosesDAO = factory.getDiagnosesDAO();
		icdDAO = factory.getICDCodesDAO();
	}
	
	public EditDiagnosesAction(DAOFactory factory, long hcpid, 
				String pidString) 
		throws ITrustException {
		super(factory, hcpid, pidString);
		diagnosesDAO = factory.getDiagnosesDAO();
		icdDAO = factory.getICDCodesDAO();
		
	}
	
	public List<DiagnosisBean> getDiagnoses() throws DBException {
		if (isUnsaved()) {
			return new ArrayList<DiagnosisBean>();
		} else {
			return diagnosesDAO.getList(getOvID());
		}
	}
	
	public void addDiagnosis(DiagnosisBean bean) throws ITrustException {
		verifySaved();
		diagnosesDAO.add(bean);
	}
	
	public void editDiagnosis(DiagnosisBean bean) throws ITrustException {
		verifySaved();
		diagnosesDAO.edit(bean);
		
	}
	
	public void deleteDiagnosis(DiagnosisBean bean) throws ITrustException {
		verifySaved();
		diagnosesDAO.remove(bean.getOvDiagnosisID());
		
	}
	
	public List<DiagnosisBean> getDiagnosisCodes() throws DBException {
		return icdDAO.getAllICDCodes();
	}
	
}
