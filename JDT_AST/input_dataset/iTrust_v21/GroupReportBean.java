package edu.ncsu.csc.itrust.beans;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.report.ReportFilter;

/**
 * 
 *
 */
public class GroupReportBean {
	private List<PatientBean> patients;
	private List<ReportFilter> filters;
	
	/**
	 * 
	 * @param patients
	 * @param filters
	 */
	public GroupReportBean(List<PatientBean> patients, List<ReportFilter> filters) {
		this.patients = patients;
		this.filters = filters;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PatientBean> getPatients() {
		return patients;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ReportFilter> getFilters() {
		return filters;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getFilterStrings() {
		List<String> out = new ArrayList<String>();
		for(ReportFilter filter : filters) {
			out.add(filter.toString());
		}
		return out;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String>	getPatientNames() {
		List<String> out = new ArrayList<String>();
		for(PatientBean patient : patients) {
			out.add(patient.getFullName());
		}
		return out;
	}
}
