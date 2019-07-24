package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import edu.ncsu.csc.itrust.beans.GroupReportBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.report.DemographicReportFilter;
import edu.ncsu.csc.itrust.report.DemographicReportFilter.DemographicReportFilterType;
import edu.ncsu.csc.itrust.report.MedicalReportFilter;
import edu.ncsu.csc.itrust.report.MedicalReportFilter.MedicalReportFilterType;
import edu.ncsu.csc.itrust.report.PersonnelReportFilter;
import edu.ncsu.csc.itrust.report.PersonnelReportFilter.PersonnelReportFilterType;
import edu.ncsu.csc.itrust.report.ReportFilter;

/**
 * Generates group reports, producing ArrayLists containing
 * the headers, data, and filters.  
 */
public class GroupReportGeneratorAction {
	
	/**
	 * DAOFactory for database operations
	 */
	DAOFactory factory = null;
	
	/**
	 * GroupReportAction for generating the individual records
	 */
	GroupReportAction action;
	
	/**
	 * List of ReportFilters to be used in the report
	 */
	List<ReportFilter> filters = null;
	
	/**
	 * List of report filter types that were used in the report
	 */
	ArrayList<String> reportFilterTypes = new ArrayList<String>();
	
	/**
	 * List of report filter values that were used in the report
	 */
	ArrayList<String> reportFilterValues = new ArrayList<String>();
	
	/**
	 * List of headers for the report
	 */
	ArrayList<String> reportHeaders = new ArrayList<String>();
	
	/**
	 * List of lists (each of the sub-lists is a record) in the report
	 */
	ArrayList<ArrayList<String>> reportData = new ArrayList<ArrayList<String>>();

	/**
	 * Initializes the group report generator with a list of filters
	 * 
	 * @param filters List of filters to be used in the report
	 */
	public GroupReportGeneratorAction(DAOFactory factory, List<ReportFilter> filters){
		this.factory=factory;
		this.filters=filters;
		action = new GroupReportAction(factory);
	}
	
	/**
	 * Initializes the group report generator with a list of filters after parsing them
	 * 
	 * @param filters HttpServletRequest to grab and parse parameters from
	 */
	public GroupReportGeneratorAction(DAOFactory factory, HttpServletRequest request){
		this.factory=factory;
		this.filters = new ArrayList<ReportFilter>();
		parseFilters(request);
		action = new GroupReportAction(factory);
	}
	
	/**
	 * Gets a list of the filters used in the report.
	 * 
	 * @return List of FilterTypes used in the report
	 */
	public ArrayList<String> getReportFilterTypes(){
		return reportFilterTypes;
	}
	
	/**
	 * Gets a list of the filter values used in the report.
	 * 
	 * @return List of filter values used in the report
	 */
	public ArrayList<String> getReportFilterValues(){
		return reportFilterValues;
	}
	
	/**
	 * Gets a list of the report headers.
	 * 
	 * @return List of report headers
	 */
	public ArrayList<String> getReportHeaders(){
		return reportHeaders;
	}
	
	/**
	 * Gets a list of lists of report records (patients)
	 * 
	 * @return List of lists of report records
	 */
	public ArrayList<ArrayList<String>> getReportData(){
		return reportData;
	}
	
	/**
	 * Generates the ArrayLists for the report based on the filters passed
	 * in the constructor.
	 */
	public void generateReport(){
		
		//Initialize the GroupReportBean
		GroupReportBean report = action.generateReport(filters);
		
		//Populate the filter lists
		for (ReportFilter filter : filters) {
			reportFilterTypes.add(filter.getFilterTypeString());
			reportFilterValues.add(filter.getFilterValue());
		}
		
		//Populate the header list with the DemographicReportFilters
		for (DemographicReportFilterType type : DemographicReportFilterType.values()) {
			if (type != DemographicReportFilterType.LOWER_AGE_LIMIT && type != DemographicReportFilterType.UPPER_AGE_LIMIT){
				reportHeaders.add(type.toString());
			}
		}
		
		//Populate the header list with the MedicalReportFilters
		for (MedicalReportFilterType type : MedicalReportFilterType.values()) {
			if (type != MedicalReportFilterType.LOWER_OFFICE_VISIT_DATE && type != MedicalReportFilterType.UPPER_OFFICE_VISIT_DATE){
				reportHeaders.add(type.toString());
			}
			if (type == MedicalReportFilterType.LOWER_OFFICE_VISIT_DATE){
				reportHeaders.add("OFFICE VISIT DATE");
			}
		}
		
		//Populate the header list with the PersonnelReportFilters
		for (PersonnelReportFilterType type : PersonnelReportFilterType.values()) {
			reportHeaders.add(type.toString());
		}
		
		//Loop through all the patients in the report
		for (PatientBean patient : report.getPatients()) {
			
			//Create a temporary ArrayList for the current patient's data
			ArrayList<String> currentPatientData = new ArrayList<String>();
			
			//Populate the current record with Demographic data
			for (DemographicReportFilterType type : DemographicReportFilterType.values()) {
				if (type != DemographicReportFilterType.LOWER_AGE_LIMIT && type != DemographicReportFilterType.UPPER_AGE_LIMIT) {
					String val = action.getComprehensiveDemographicInfo(patient, type);
					if (val != null) {
						currentPatientData.add(val);
					} else {
						currentPatientData.add("");
					}
				}
			}
			
			//Populate the current record with Medical data
			for (MedicalReportFilterType type : MedicalReportFilterType.values()) {
				if (type != MedicalReportFilterType.UPPER_OFFICE_VISIT_DATE) {
					String val = action.getComprehensiveMedicalInfo(patient, type);
					if (val != null) {
						currentPatientData.add(val);
					} else {
						currentPatientData.add("");
					}
				}
			}
			
			//Populate the current record with Personnel data
			for (PersonnelReportFilterType type : PersonnelReportFilterType.values()) {
				String val = action.getComprehensivePersonnelInfo(patient, type);
				if (val != null) {
					currentPatientData.add(val);
				} else {
					currentPatientData.add("");
				}
			}
			
			//Add the current record to the list of records
			reportData.add(currentPatientData);
		}
		
		//Remove MID from report
		int midIndex = reportHeaders.indexOf("MID");
		reportHeaders.remove(midIndex);
		for(ArrayList<String> patients : reportData){
			patients.remove(midIndex);
		}
	}
	
	/**
	 * Method that parses the request parameters to create the filter list in order to run report.
	 * 
	 * @param request with form parameters to create the filter list
	 */
	private void parseFilters(HttpServletRequest request){
		boolean hasDeactivatedFilter = false;
		if (request.getParameter("demoparams") != null && !request.getParameter("demoparams").isEmpty()) {
			String demoparams = request.getParameter("demoparams");
			String demoFilters[] = demoparams.split(" ");
			for (String filter : demoFilters) {
				if (request.getParameter(filter) != null && !request.getParameter(filter).isEmpty()) {
					DemographicReportFilterType filterType = DemographicReportFilter.filterTypeFromString(filter);
					if(filterType.toString().equals("DEACTIVATED")){
						hasDeactivatedFilter=true;
					}
					DemographicReportFilter fil = new DemographicReportFilter(filterType, request.getParameter(filter), factory);
					filters.add(fil);
				}
			}
		}
		if(!hasDeactivatedFilter){
			filters.add(new DemographicReportFilter(DemographicReportFilter.filterTypeFromString("DEACTIVATED"), "exclude", factory));
		}
		if (request.getParameter("medparams") != null && !request.getParameter("medparams").isEmpty()) {
			String medparams = request.getParameter("medparams");
			String medFilters[] = medparams.split(" ");
			for (String filter : medFilters) {
				if (request.getParameter(filter) != null && !request.getParameter(filter).isEmpty()) {
					MedicalReportFilterType filterType = MedicalReportFilter.filterTypeFromString(filter);
					if (filterType == MedicalReportFilterType.DIAGNOSIS_ICD_CODE
							|| filterType == MedicalReportFilterType.MISSING_DIAGNOSIS_ICD_CODE
							|| filterType == MedicalReportFilterType.ALLERGY
							|| filterType == MedicalReportFilterType.CURRENT_PRESCRIPTIONS
							|| filterType == MedicalReportFilterType.PASTCURRENT_PRESCRIPTIONS
							|| filterType == MedicalReportFilterType.PROCEDURE) {
						String[] vals = request.getParameterValues(filter);
						for (String val : vals) {
							MedicalReportFilter fil = new MedicalReportFilter(filterType, val, factory);
							filters.add(fil);
						}
					} else {
						MedicalReportFilter fil = new MedicalReportFilter(filterType, request.getParameter(filter), factory);
						filters.add(fil);
					}
				}
			}
		}
		if (request.getParameter("persparams") != null && !request.getParameter("persparams").isEmpty()) {
			String persparams = request.getParameter("persparams");
			String personnelFilters[] = persparams.split(" ");
			for (String filter : personnelFilters) {
				if (request.getParameter(filter) != null && !request.getParameter(filter).isEmpty()) {
					PersonnelReportFilterType filterType = PersonnelReportFilter.filterTypeFromString(filter);
					if (filterType == PersonnelReportFilterType.DLHCP) {
						String[] vals = request.getParameterValues(filter);
						for (String val : vals) {
							PersonnelReportFilter fil = new PersonnelReportFilter(filterType, val, factory);
							filters.add(fil);
						}
					} else {
						PersonnelReportFilter fil = new PersonnelReportFilter(filterType, request.getParameter(filter), factory);
						filters.add(fil);
					}
				}
			}
		}
	}
}
