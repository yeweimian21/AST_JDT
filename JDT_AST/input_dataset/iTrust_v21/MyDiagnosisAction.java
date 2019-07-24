package edu.ncsu.csc.itrust.action;

import java.io.Serializable;
import java.util.*;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.HCPDiagnosisBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.beans.SurveyBean;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionsDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.SurveyDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Edits the privacy levels of diagnoses, used by myDiagnoses.jsp
 * 
 * 
 */
public class MyDiagnosisAction {
	
	private OfficeVisitDAO officeVisitDAO;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private SurveyDAO surveyDAO;
	private LabProcedureDAO labprocDAO;
	private PrescriptionsDAO prescriptionsDAO;
	private long loggedInMID;

	/**
	 * Set up for defaults
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the user who is looking at their diagnoses.
	 * @throws ITrustException
	 */
	public MyDiagnosisAction(DAOFactory factory, long loggedInMID) throws ITrustException {
		this.loggedInMID = loggedInMID;
		this.patientDAO = factory.getPatientDAO();
		this.officeVisitDAO = factory.getOfficeVisitDAO();
		this.personnelDAO = factory.getPersonnelDAO();
		this.surveyDAO = factory.getSurveyDAO();
		this.labprocDAO = factory.getLabProcedureDAO();
		this.prescriptionsDAO = factory.getPrescriptionsDAO();
	}
	
	/**
	 * Returns a list of DiagnosisBeans for the patient
	 * 
	 * @return the list of DiagnosisBeans
	 * @throws DBException
	 */
	public List<DiagnosisBean> getDiagnoses() throws DBException {
		return patientDAO.getDiagnoses(loggedInMID);
	}
	
	/**
	 * Returns a list of all the HCPs who have a particular diagnosis
	 * 
	 * @param icdcode the diagnosis of interest
	 * @return the list of HCPs
	 * @throws DBException
	 */
	public List<HCPDiagnosisBean> getHCPByDiagnosis(String icdcode) throws DBException {
		
		int medMatch = 0;
		HashMap<Long, HCPDiagnosisBean> hcpHash = new HashMap<Long, HCPDiagnosisBean>();
		HashMap<Long, Long> patientHash = new HashMap<Long, Long>();
	
		HCPDiagnosisBean diag = null;
		List<OfficeVisitBean> beans = officeVisitDAO.getAllOfficeVisitsForDiagnosis(icdcode);
		
		for (OfficeVisitBean bean: beans) {
			
			// check for HCP-Patient locality based on first 3 digits in ZIP
			if (!patientDAO.getPatient(loggedInMID).getZip().substring(0, 2).equals(personnelDAO.getPersonnel(bean.getHcpID()).getZip().substring(0, 2)))
				continue;
			
			// Check to see if we already have a bean for the HCP associated with this visit
			if (hcpHash.containsKey(bean.getHcpID())) {
				diag = hcpHash.get(bean.getHcpID());
				// get all prescriptions associated with an office visit
				List<PrescriptionBean> prescriptions = prescriptionsDAO.getList(bean.getID());
				for (PrescriptionBean p: prescriptions) {
					List<MedicationBean> mlist = diag.getMedList();
					
					for (MedicationBean b: mlist) {
						if (p.getMedication().getDescription().equals(b.getDescription()))
							medMatch++;
					}
					if (medMatch == 0) {
						mlist.add(p.getMedication());
						diag.setMedList(mlist);
					}
					else {
						medMatch = 0;
					}
				}
				
				// Get Lab Procedures
				List<LabProcedureBean> labprocs = diag.getLabList(); 
				List<LabProcedureBean> lpbeans = labprocDAO.getAllLabProceduresForDocOV(bean.getVisitID());
				for (LabProcedureBean p: lpbeans) {
					labprocs.add(p);
				}
				diag.setLabList(labprocs);
				
				
				if (surveyDAO.isSurveyCompleted(bean.getVisitID())) {
					SurveyBean survey = surveyDAO.getSurveyData(bean.getVisitID());
					diag.setVisitSat(survey.getVisitSatisfaction());
					diag.setTreatmentSat(survey.getTreatmentSatisfaction());
				}
				
				// Check if this patient has been seen multiple times for this diagnosis
				if (!patientHash.containsKey(bean.getPatientID())) {
					patientHash.put(bean.getPatientID(), bean.getHcpID());
					diag.incNumPatients();
				}
			}
			else {
				diag = new HCPDiagnosisBean();
				List<MedicationBean> mlist = new ArrayList<MedicationBean>();
				diag.setHCP(bean.getHcpID());
				try {
					diag.setHCPName(personnelDAO.getName(bean.getHcpID()));
				} catch (ITrustException e) {
					diag.setHCPName("null");
				}
				diag.incNumPatients();
				List<PrescriptionBean> prescriptions = prescriptionsDAO.getList(bean.getID());
				for (PrescriptionBean p: prescriptions) {
					mlist.add(p.getMedication());
				}
				diag.setMedList(mlist);
				diag.setLabList(labprocDAO.getAllLabProceduresForDocOV(bean.getVisitID()));
				
				if (surveyDAO.isSurveyCompleted(bean.getVisitID())) {
					SurveyBean survey = surveyDAO.getSurveyData(bean.getVisitID());
					diag.setVisitSat(survey.getVisitSatisfaction());
					diag.setTreatmentSat(survey.getTreatmentSatisfaction());
				}
				
				patientHash.put(bean.getPatientID(), bean.getHcpID());
				hcpHash.put(bean.getHcpID(), diag);
			}
		}
		List<HCPDiagnosisBean> list = new ArrayList<HCPDiagnosisBean>(hcpHash.values());
		Collections.sort(list, new HCPDiagnosisBeanComparator() );
		return list;
	}
	
	/**
	 * Looks up all the prescriptions given by a certain HCP with the same ICD code.
	 * @param hcpid The MID of the HCP
	 * @param icdcode The ICD code of the prescription we are looking up.
	 * @return A java.util.List of PrescriptionBeans made by this HCP of this ICD code.
	 * @throws DBException
	 */
	public List<PrescriptionBean> getPrescriptionsByHCPAndICD(long hcpid, String icdcode) throws DBException {
		List<PrescriptionBean> list = new ArrayList<PrescriptionBean>();
		
		List<OfficeVisitBean> ovs = officeVisitDAO.getAllOfficeVisitsForDiagnosis(icdcode);
		for (int i = 0; i < ovs.size(); i++) {
			if (ovs.get(i).getHcpID() == hcpid) {
				long ovid = ovs.get(i).getID();
				List<PrescriptionBean> prescriptions = prescriptionsDAO.getList(ovid);
				list.addAll(prescriptions);
			}
		}
		
		return list;
		
		
	}
	
	
	/**
	 * Checks to see what HCP has had the most experience with a diagnosis
	 *
	 */
	public static class HCPDiagnosisBeanComparator implements Comparator<HCPDiagnosisBean>, Serializable {
		
		private static final long serialVersionUID = -6328390386684022934L;

		/**
		 * Compares one HCP with another
		 * 
		 * @param a the first HCP
		 * @param b the second HCP
		 * @return -1 if a has had more patients, 1 if b has had more patients; otherwise 0
		 */
		public int compare(HCPDiagnosisBean a, HCPDiagnosisBean b) {
			int ret = 0;
			
			if (a.getNumPatients() > b.getNumPatients())
				ret = -1;
			else if (a.getNumPatients() < b.getNumPatients())
				ret = 1;
			
			return ret;
		}
	}
	
}
