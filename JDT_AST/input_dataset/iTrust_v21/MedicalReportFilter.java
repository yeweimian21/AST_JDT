package edu.ncsu.csc.itrust.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;

/**
 * 
 *
 */
public class MedicalReportFilter extends ReportFilter {

	/**
	 * 
	 * 
	 */
	public enum MedicalReportFilterType {
		PROCEDURE("PROCEDURE"),
		ALLERGY("ALLERGY"),
		CURRENT_PRESCRIPTIONS("CURRENT PRESCRIPTIONS"),
		PASTCURRENT_PRESCRIPTIONS("PAST AND CURRENT PRESCRIPTIONS"),
		DIAGNOSIS_ICD_CODE("DIAGNOSIS"),
		MISSING_DIAGNOSIS_ICD_CODE("MISSING DIAGNOSIS"),
		LOWER_OFFICE_VISIT_DATE("LOWER OFFICE VISIT DATE LIMIT"),
		UPPER_OFFICE_VISIT_DATE("UPPER OFFICE VISIT DATE LIMIT");

		private final String name;

		/**
		 * 
		 * @param name
		 */
		private MedicalReportFilterType(String name) {
			this.name = name;
		}

		/**
		 * 
		 */
		@Override
		public String toString() {
			return this.name;
		}
	}

	private MedicalReportFilterType filterType;
	private String filterValue;
	private PatientDAO pDAO;
	private AllergyDAO aDAO;
	private OfficeVisitDAO oDAO;

	/**
	 * 
	 * @param filterType
	 * @param filterValue
	 */
	public MedicalReportFilter(MedicalReportFilterType filterType, String filterValue, DAOFactory factory) {
		this.filterType = filterType;
		this.filterValue = filterValue;
		pDAO = factory.getPatientDAO();
		aDAO = factory.getAllergyDAO();
		oDAO = factory.getOfficeVisitDAO();
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static MedicalReportFilterType filterTypeFromString(String name) {
		for(MedicalReportFilterType type : MedicalReportFilterType.values()) {
			if(type.name().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<PatientBean> filter(List<PatientBean> patients) {
		List<PatientBean> prunedList = new ArrayList<PatientBean>();
		boolean add = filterValue != null && !filterValue.isEmpty();
		if (add) {
			for (PatientBean patient : patients) {
				add = false;
				switch (filterType) {
				case ALLERGY:
					try {
						List<AllergyBean> allergies = aDAO.getAllergies(patient.getMID());
						for (AllergyBean allergy : allergies) {
							if (filterValue.equalsIgnoreCase(allergy.getNDCode())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case CURRENT_PRESCRIPTIONS:
					try {
						List<PrescriptionBean> prescriptions = pDAO.getCurrentPrescriptions(patient.getMID());
						for (PrescriptionBean prescription : prescriptions) {
							if (filterValue.equalsIgnoreCase(prescription.getMedication().getNDCode())
									|| filterValue.equalsIgnoreCase(prescription.getMedication()
											.getNDCodeFormatted())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case DIAGNOSIS_ICD_CODE:
					try {
						List<DiagnosisBean> diagnoses = pDAO.getDiagnoses(patient.getMID());
						for (DiagnosisBean diagnosis : diagnoses) {
							if (filterValue.equalsIgnoreCase(diagnosis.getICDCode())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case PASTCURRENT_PRESCRIPTIONS:
					try {
						List<PrescriptionBean> prescriptions = pDAO.getPrescriptions(patient.getMID());
						for (PrescriptionBean prescription : prescriptions) {
							if (filterValue.equalsIgnoreCase(prescription.getMedication().getNDCode())
									|| filterValue.equalsIgnoreCase(prescription.getMedication()
											.getNDCodeFormatted())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case PROCEDURE:
					try {
						List<ProcedureBean> procedures = pDAO.getProcedures(patient.getMID());
						for (ProcedureBean procedure : procedures) {
							if (filterValue.equalsIgnoreCase(procedure.getCPTCode())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case MISSING_DIAGNOSIS_ICD_CODE:
					try {
						List<DiagnosisBean> diagnoses = pDAO.getDiagnoses(patient.getMID());
						add = true;
						for (DiagnosisBean diagnosis : diagnoses) {
							if (filterValue.equalsIgnoreCase(diagnosis.getICDCode())) {
								add = false;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case LOWER_OFFICE_VISIT_DATE:
					try {
						SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy");
						Date d = frmt.parse(filterValue);
						List<OfficeVisitBean> visits = oDAO.getAllOfficeVisits(patient.getMID());
						for (OfficeVisitBean visit : visits) {
							if (d.compareTo(visit.getVisitDate()) <= 0) {
								add = true;
								break;
							}
						}
					}catch(RuntimeException e){
						break;
					}catch (Exception e) {
						break;
					}
					break;
				case UPPER_OFFICE_VISIT_DATE:
					try {
						SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy");
						Date d = frmt.parse(filterValue);
						List<OfficeVisitBean> visits = oDAO.getAllOfficeVisits(patient.getMID());
						for (OfficeVisitBean visit : visits) {
							if (d.compareTo(visit.getVisitDate()) >= 0) {
								add = true;
								break;
							}
						}
					}catch (RuntimeException e) {
						break;
					} catch (Exception e) {
						break;
					}
					break;
				default:
					break;
				}
				if(add)
					prunedList.add(patient);
			}
		}
		return prunedList;
	}

	/**
	 * 
	 * @return
	 */
	public MedicalReportFilterType getFilterType() {
		return filterType;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFilterTypeString() {
		return filterType.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getFilterValue() {
		return filterValue;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		String out = "Filter by " + filterType.toString() + " with value " + filterValue;
		return out;
	}

}
