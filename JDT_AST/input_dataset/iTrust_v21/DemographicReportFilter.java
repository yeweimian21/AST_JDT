package edu.ncsu.csc.itrust.report;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;

/**
 * 
 *
 */
public class DemographicReportFilter extends ReportFilter {

	/**
	 * 
	 *
	 */
	public enum DemographicReportFilterType {
		MID("MID"),
		GENDER("GENDER"),
		LAST_NAME("LAST NAME"),
		FIRST_NAME("FIRST NAME"),
		CONTACT_EMAIL("CONTACT EMAIL"),
		STREET_ADDR("STREET ADDRESS"),
		CITY("CITY"),
		STATE("STATE"),
		ZIP("ZIPCODE"),
		PHONE("PHONE #"),
		EMER_CONTACT_NAME("EMERGENCY CONTACT NAME"),
		EMER_CONTACT_PHONE("EMERGENCY CONTACT PHONE #"),
		INSURE_NAME("INSURANCE COMPANY NAME"),
		INSURE_ADDR("INSURANCE COMPANY ADDRESS"),
		INSURE_CITY("INSURANCE COMPANY CITY"),
		INSURE_STATE("INSURANCE COMPANY STATE"),
		INSURE_ZIP("INSURANCE COMPANY ZIPCODE"),
		INSURE_PHONE("INSURANCE COMPANY PHONE #"),
		INSURE_ID("INSURANCE COMPANY ID"),
		PARENT_FIRST_NAME("PARENT'S FIRST NAME"),
		PARENT_LAST_NAME("PARENT'S LAST NAME"),
		CHILD_FIRST_NAME("CHILD'S FIRST NAME"),
		CHILD_LAST_NAME("CHILD'S LAST NAME"),
		SIBLING_FIRST_NAME("SIBLING'S FIRST NAME"),
		SIBLING_LAST_NAME("SIBLING'S LAST NAME"),
		LOWER_AGE_LIMIT("LOWER AGE LIMIT"),
		UPPER_AGE_LIMIT("UPPER AGE LIMIT"),
		DEACTIVATED("DEACTIVATED");

		private final String name;

		/**
		 * 
		 * @param name
		 */
		private DemographicReportFilterType(String name) {
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

	private DemographicReportFilterType filterType;
	private String filterValue;
	private FamilyDAO fDAO;

	/**
	 * 
	 * @param filterType
	 * @param filterValue
	 */
	public DemographicReportFilter(DemographicReportFilterType filterType, String filterValue,
			DAOFactory factory) {
		this.filterType = filterType;
		this.filterValue = filterValue;
		fDAO = factory.getFamilyDAO();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static DemographicReportFilterType filterTypeFromString(String name) {
		for (DemographicReportFilterType type : DemographicReportFilterType.values()) {
			if (type.name().equalsIgnoreCase(name)) {
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
				case MID:
					add = filterValue.equalsIgnoreCase(Long.toString(patient.getMID()));
					break;
				case GENDER:
					add = filterValue.equalsIgnoreCase(patient.getGender().toString());
					break;
				case LAST_NAME:
					add = patient.getLastName().equalsIgnoreCase(filterValue);
					break;
				case FIRST_NAME:
					add = patient.getFirstName().equalsIgnoreCase(filterValue);
					break;
				case CONTACT_EMAIL:
					add = patient.getEmail().equalsIgnoreCase(filterValue);
					break;
				case STREET_ADDR:
					add = patient.getStreetAddress1().equalsIgnoreCase(filterValue)
							|| patient.getStreetAddress2().equalsIgnoreCase(filterValue)
							|| (patient.getStreetAddress1() + " " + patient.getStreetAddress2())
									.equalsIgnoreCase(filterValue);
					break;
				case CITY:
					add = patient.getCity().equalsIgnoreCase(filterValue);
					break;
				case STATE:
					add = patient.getState().equalsIgnoreCase(filterValue);
					break;
				case ZIP:
					add = patient.getZip().contains(filterValue);
					break;
				case PHONE:
					add = patient.getPhone().equalsIgnoreCase(filterValue);
					break;
				case EMER_CONTACT_NAME:
					add = patient.getEmergencyName().equalsIgnoreCase(filterValue);
					break;
				case EMER_CONTACT_PHONE:
					add = patient.getEmergencyPhone().equalsIgnoreCase(filterValue);
					break;
				case INSURE_NAME:
					add = patient.getIcName().equalsIgnoreCase(filterValue);
					break;
				case INSURE_ADDR:
					add = patient.getIcAddress1().equalsIgnoreCase(filterValue)
							|| patient.getIcAddress2().equalsIgnoreCase(filterValue)
							|| (patient.getIcAddress1() + " " + patient.getIcAddress2())
									.equalsIgnoreCase(filterValue);
					break;
				case INSURE_CITY:
					add = patient.getIcCity().equalsIgnoreCase(filterValue);
					break;
				case INSURE_STATE:
					add = patient.getIcState().equalsIgnoreCase(filterValue);
					break;
				case INSURE_ZIP:
					add = patient.getIcZip().equalsIgnoreCase(filterValue);
					break;
				case INSURE_PHONE:
					add = patient.getIcPhone().equalsIgnoreCase(filterValue);
					break;
				case INSURE_ID:
					add = patient.getIcID().equalsIgnoreCase(filterValue);
					break;
				case PARENT_FIRST_NAME:
					try {
						List<FamilyMemberBean> parents = fDAO.getParents(patient.getMID());
						for (FamilyMemberBean parent : parents) {
							if (filterValue.equalsIgnoreCase(parent.getFirstName())) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case PARENT_LAST_NAME:
					try {
						List<FamilyMemberBean> parents = fDAO.getParents(patient.getMID());
						for (FamilyMemberBean parent : parents) {
							if (parent.getLastName().equals(filterValue)) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case CHILD_FIRST_NAME:
					try {
						List<FamilyMemberBean> children = fDAO.getChildren(patient.getMID());
						for (FamilyMemberBean child : children) {
							if (child.getFirstName().equals(filterValue)) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case CHILD_LAST_NAME:
					try {
						List<FamilyMemberBean> children = fDAO.getChildren(patient.getMID());
						for (FamilyMemberBean child : children) {
							if (child.getLastName().equals(filterValue)) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case SIBLING_FIRST_NAME:
					try {
						List<FamilyMemberBean> siblings = fDAO.getSiblings(patient.getMID());
						for (FamilyMemberBean sibling : siblings) {
							if (sibling.getFirstName().equals(filterValue)) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case SIBLING_LAST_NAME:
					try {
						List<FamilyMemberBean> siblings = fDAO.getSiblings(patient.getMID());
						for (FamilyMemberBean sibling : siblings) {
							if (sibling.getLastName().equals(filterValue)) {
								add = true;
								break;
							}
						}
					} catch (Exception e) {
						break;
					}
					break;
				case LOWER_AGE_LIMIT:
					int lalval = Integer.parseInt(filterValue);
					if(lalval<0){
						throw new NumberFormatException("Age must be GTE 0!");
					}
					add = lalval <= patient.getAge();
					break;
				case UPPER_AGE_LIMIT:
					int ualval = Integer.parseInt(filterValue);
					if(ualval<0){
						throw new NumberFormatException("Age must be GTE 0!");
					}
					add = patient.getAge() > 0 && ualval >= patient.getAge();
					break;
				case DEACTIVATED:
					if(filterValue.equals("exclude")){
						add = patient.getDateOfDeactivationStr().equals("");
					}else if(filterValue.equals("only")){
						add = !patient.getDateOfDeactivationStr().equals("");
					}else{
						add=true;
					}
					break;
				default:
					break;
				}

				if (add) {
					prunedList.add(patient);
				}
			}
		}
		return prunedList;
	}

	/**
	 * 
	 * @return
	 */
	public DemographicReportFilterType getFilterType() {
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
