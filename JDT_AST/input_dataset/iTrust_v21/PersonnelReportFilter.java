package edu.ncsu.csc.itrust.report;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;

/**
 * 
 *
 */
public class PersonnelReportFilter extends ReportFilter {

	/**
	 * 
	 *
	 */
	public enum PersonnelReportFilterType {
		// MID,
		DLHCP("DECLARED HCP");

		private final String name;

		/**
		 * 
		 * @param name
		 */
		private PersonnelReportFilterType(String name) {
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

	private PersonnelReportFilterType filterType;
	private String filterValue;
	private PatientDAO pDAO;

	public PersonnelReportFilter(PersonnelReportFilterType filterType, String filterValue, DAOFactory factory) {
		this.filterType = filterType;
		this.filterValue = filterValue;
		pDAO = factory.getPatientDAO();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static PersonnelReportFilterType filterTypeFromString(String name) {
		for(PersonnelReportFilterType type : PersonnelReportFilterType.values()) {
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
				case DLHCP:
					try {
						List<PersonnelBean> dlhcps = pDAO.getDeclaredHCPs(patient.getMID());
						for (PersonnelBean dlhcp : dlhcps) {
							if (filterValue.equalsIgnoreCase(dlhcp.getFullName())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				default:
					break;
				}
				if (add)
					prunedList.add(patient);
			}
		}
		return prunedList;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		String out = "Filter by " + filterType.toString() + " with value " + filterValue;
		return out;
	}

	/**
	 * 
	 */
	@Override
	public String getFilterValue() {
		return filterValue;
	}
	
	/**
	 * 
	 * @return
	 */
	public PersonnelReportFilterType getFilterType() {
		return filterType;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFilterTypeString() {
		return filterType.toString();
	}

}
