package edu.ncsu.csc.itrust.report;

import java.util.List;
import edu.ncsu.csc.itrust.beans.PatientBean;

/**
 * 
 *
 */
public abstract class ReportFilter {
	/**
	 * 
	 * @param patients
	 * @return
	 */
	public abstract List<PatientBean> filter(List<PatientBean> patients);
	/**
	 * 
	 */
	public abstract String toString();
	public abstract String getFilterTypeString();
	public abstract String getFilterValue();
}
