package edu.ncsu.csc.itrust.unit.report;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.report.DemographicReportFilter;
import edu.ncsu.csc.itrust.report.DemographicReportFilter.DemographicReportFilterType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class DemographicReportFilterTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private PatientDAO pDAO = factory.getPatientDAO();
	private List<PatientBean> allPatients;
	private DemographicReportFilter filter;
	private TestDataGenerator gen = new TestDataGenerator();

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		allPatients = pDAO.getAllPatients();
	}

	public void testFilterByLastName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.LAST_NAME, "Programmer", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(2, res.size());
		assertTrue(res.get(0).getMID() == 2L); // Andy Programmer
		assertTrue(res.get(1).getMID() == 5L); // Baby Programmer
	}

	public void testFilterByLastNameNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.LAST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByFirstName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.FIRST_NAME, "Baby", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(4, res.size());
		assertTrue(res.get(0).getMID() == 5L); // Baby Programmer
		assertTrue(res.get(1).getMID() == 6L); // Baby A
		assertTrue(res.get(2).getMID() == 7L); // Baby B
		assertTrue(res.get(3).getMID() == 8L); // Baby C
	}

	public void testFilterByFirstNameNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.FIRST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByContactEmail() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CONTACT_EMAIL, "fake@email.com", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(6, res.size());
		assertTrue(res.get(0).getMID() == 3L); // Care Needs
		assertTrue(res.get(1).getMID() == 4L); // NoRecords Has
		assertTrue(res.get(2).getMID() == 5L); // Baby Programmer
		assertTrue(res.get(3).getMID() == 6L); // Baby A
		assertTrue(res.get(4).getMID() == 7L); // Baby B
		assertTrue(res.get(5).getMID() == 8L); // Baby C
	}

	public void testFilterByContactEmailNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.CONTACT_EMAIL, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByStreetAddr1() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.STREET_ADDR, "1247 Noname Dr", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(13, res.size());
		assertTrue(res.get(0).getMID() == 1L); // random person
		assertTrue(res.get(1).getMID() == 3L); // care needs
		assertTrue(res.get(2).getMID() == 4L); // norecords has
		assertTrue(res.get(3).getMID() == 5L); // baby programmer
		assertTrue(res.get(4).getMID() == 6L); // baby a
		assertTrue(res.get(5).getMID() == 7L); // baby b
		assertTrue(res.get(6).getMID() == 8L); // baby c
		assertTrue(res.get(7).getMID() == 42L); // bad horse
	}

	public void testFilterByStreetAddr2() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.STREET_ADDR, "Suite 106", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(16, res.size());
		assertTrue(res.get(0).getMID() == 1L); // random person
		assertTrue(res.get(1).getMID() == 3L); // care needs
		assertTrue(res.get(2).getMID() == 4L); // norecords has
		assertTrue(res.get(3).getMID() == 5L); // baby programmer
		assertTrue(res.get(4).getMID() == 6L); // baby a
		assertTrue(res.get(5).getMID() == 7L); // baby b
		assertTrue(res.get(6).getMID() == 8L); // baby c
		assertTrue(res.get(7).getMID() == 42L); // bad horse
	}

	public void testFilterByStreetAddr3() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.STREET_ADDR,
				"1247 Noname Dr Suite 106", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(13, res.size());
		assertTrue(res.get(0).getMID() == 1L); // random person
		assertTrue(res.get(1).getMID() == 3L); // care needs
		assertTrue(res.get(2).getMID() == 4L); // norecords has
		assertTrue(res.get(3).getMID() == 5L); // baby programmer
		assertTrue(res.get(4).getMID() == 6L); // baby a
		assertTrue(res.get(5).getMID() == 7L); // baby b
		assertTrue(res.get(6).getMID() == 8L); // baby c
		assertTrue(res.get(7).getMID() == 42L); // bad horse
	}

	public void testFilterByStreetAddrNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.STREET_ADDR, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByCity1() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CITY, "", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(0, res.size());
	}

	public void testFilterByCity2() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CITY, "New YORK", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(3, res.size());
		assertTrue(res.get(0).getMID() == 22L); // Fozzie Bear
		assertTrue(res.get(1).getMID() == 23L); // Dare Devil
		assertTrue(res.get(2).getMID() == 24L); // Devils Advocate
	}

	public void testFilterByCityNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.CITY, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByState() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.STATE, "NY", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(4, res.size());
		assertTrue(res.get(0).getMID() == 22L); // Fozzie Bear
		assertTrue(res.get(1).getMID() == 23L); // Dare Devil
		assertTrue(res.get(2).getMID() == 24L); // Devils Advocate
		assertTrue(res.get(3).getMID() == 103L); // Fulton Gray
	}

	public void testFilterByStateNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.STATE, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByZip() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.ZIP, "10001", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(3, res.size());
		assertTrue(res.get(0).getMID() == 22L); // Fozzie Bear
		assertTrue(res.get(1).getMID() == 23L); // Dare Devil
		assertTrue(res.get(2).getMID() == 24L); // Devils Advocate
	}

	public void testFilterByZip2() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.ZIP, "27606-1234", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(17, res.size());
		assertTrue(res.get(0).getMID() == 1L); // random person
		assertTrue(res.get(1).getMID() == 3L); // care needs
		assertTrue(res.get(2).getMID() == 4L); // norecords has
		assertTrue(res.get(3).getMID() == 5L); // baby programmer
		assertTrue(res.get(4).getMID() == 6L); // baby a
		assertTrue(res.get(5).getMID() == 7L); // baby b
		assertTrue(res.get(6).getMID() == 8L); // baby c
		assertTrue(res.get(7).getMID() == 42L); // bad horse
	}

	public void testFilterByZipNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.ZIP, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}

	public void testFilterByPhone() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PHONE, "555-555-5554", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
		assertTrue(res.get(0).getMID() == 25L); // Trend Setter
	}

	public void testFilterByPhoneNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.PHONE, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByEmerContactName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.EMER_CONTACT_NAME, "Mum", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(6, res.size());
		assertTrue(res.get(0).getMID() == 3L);
		assertTrue(res.get(1).getMID() == 4L);
		assertTrue(res.get(2).getMID() == 5L);
		assertTrue(res.get(3).getMID() == 6L);
		assertTrue(res.get(4).getMID() == 7L);
		assertTrue(res.get(5).getMID() == 8L);
	}

	public void testFilterByEmerContactNameNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.EMER_CONTACT_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByContactPhoneNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.EMER_CONTACT_PHONE, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByGender() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.GENDER, "Female", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(24, res.size());
		assertTrue(res.get(0).getMID() == 1L);
		assertTrue(res.get(1).getMID() == 5L);
		assertTrue(res.get(2).getMID() == 6L);
		assertTrue(res.get(3).getMID() == 21L);
		assertTrue(res.get(4).getMID() == 101L);
		assertTrue(res.get(5).getMID() == 104L);
	}

	public void testFilterByGenderNoResult() {
		filter = new DemographicReportFilter(DemographicReportFilterType.GENDER, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByParentFirstName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_FIRST_NAME, "Random", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(5, res.size());
		assertTrue(res.get(0).getMID() == 2L);
		assertTrue(res.get(1).getMID() == 3L);
		assertTrue(res.get(2).getMID() == 4L);
		assertTrue(res.get(3).getMID() == 20L);
		assertTrue(res.get(4).getMID() == 21L);
	}
	
	public void testFilterByParentFirstName2() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_FIRST_NAME, "Andy", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(4, res.size());
		assertTrue(res.get(0).getMID() == 5L);
		assertTrue(res.get(1).getMID() == 6L);
		assertTrue(res.get(2).getMID() == 7L);
		assertTrue(res.get(3).getMID() == 8L);
	}

	public void testFilterByParentFirstNameNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_FIRST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByParentLastName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_LAST_NAME, "Person", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(5, res.size());
		assertTrue(res.get(0).getMID() == 2L);
		assertTrue(res.get(1).getMID() == 3L);
		assertTrue(res.get(2).getMID() == 4L);
		assertTrue(res.get(3).getMID() == 20L);
		assertTrue(res.get(4).getMID() == 21L);
	}
	
	public void testFilterByParentLastName2() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_LAST_NAME, "Programmer", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(4, res.size());
		assertTrue(res.get(0).getMID() == 5L);
		assertTrue(res.get(1).getMID() == 6L);
		assertTrue(res.get(2).getMID() == 7L);
		assertTrue(res.get(3).getMID() == 8L);
	}

	public void testFilterByParentLastNameNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_LAST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByChildFirstName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CHILD_FIRST_NAME, "Care", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
		assertTrue(res.get(0).getMID() == 1L);
	}

	public void testFilterByChildFirstNameNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CHILD_FIRST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByChildLastName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CHILD_LAST_NAME, "A", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(1, res.size());
		assertTrue(res.get(0).getMID() == 2L);
	}

	public void testFilterByChildLastNameNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.CHILD_LAST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterBySiblingFirstName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.SIBLING_FIRST_NAME, "Baby", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(4, res.size());
		assertTrue(res.get(0).getMID() == 5L);
		assertTrue(res.get(1).getMID() == 6L);
		assertTrue(res.get(2).getMID() == 7L);
		assertTrue(res.get(3).getMID() == 8L);

	}

	public void testFilterBySiblingFirstNameNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.SIBLING_FIRST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterBySiblingLastName() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.SIBLING_LAST_NAME, "A", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(3, res.size());
		assertTrue(res.get(0).getMID() == 5L);
		assertTrue(res.get(1).getMID() == 7L);
		assertTrue(res.get(2).getMID() == 8L);

	}

	public void testFilterBySiblingLastNameNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.SIBLING_LAST_NAME, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByInsuranceZip() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ZIP, "19003-2715", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(5, res.size());
		assertTrue(res.get(0).getMID() == 2L);
		assertTrue(res.get(1).getMID() == 22L);
		assertTrue(res.get(2).getMID() == 25L);
		assertTrue(res.get(3).getMID() == 23L);
		assertTrue(res.get(4).getMID() == 24L);
	}

	public void testFilterByInsuranceZipNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ZIP, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testFilterByLowerAge() throws Exception { 
		filter = new DemographicReportFilter(DemographicReportFilterType.LOWER_AGE_LIMIT, "60", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(9, res.size());
		assertTrue(res.get(0).getMID() == 1L);
		assertTrue(res.get(1).getMID() == 3L);
		assertTrue(res.get(2).getMID() == 4L);
		assertTrue(res.get(3).getMID() == 42L);
	}

	public void testFilterByLowerAgeNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.LOWER_AGE_LIMIT, "Dalpe", factory);
		try{
			@SuppressWarnings("unused")
			List<PatientBean> res = filter.filter(allPatients);
		}catch(NumberFormatException e){
			//exception is good.
			return;
		}
		assertTrue(false);
	}

	public void testFilterByUpperAgeNoResult() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.UPPER_AGE_LIMIT, "Dalpe", factory);
		try{
			@SuppressWarnings("unused")
			List<PatientBean> res = filter.filter(allPatients);
		}catch(NumberFormatException e){
			//exception is good.
			return;
		}
		assertTrue(false);
	}
	
	public void testToString() {
		filter = new DemographicReportFilter(DemographicReportFilterType.LAST_NAME, "val", factory);
		String expected = "Filter by LAST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.CHILD_FIRST_NAME, "val", factory);
		expected = "Filter by CHILD'S FIRST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.CHILD_LAST_NAME, "val", factory);
		expected = "Filter by CHILD'S LAST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.CITY, "val", factory);
		expected = "Filter by CITY with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.CONTACT_EMAIL, "val", factory);
		expected = "Filter by CONTACT EMAIL with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.EMER_CONTACT_NAME, "val", factory);
		expected = "Filter by EMERGENCY CONTACT NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.EMER_CONTACT_PHONE, "val", factory);
		expected = "Filter by EMERGENCY CONTACT PHONE # with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.FIRST_NAME, "val", factory);
		expected = "Filter by FIRST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ADDR, "val", factory);
		expected = "Filter by INSURANCE COMPANY ADDRESS with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_CITY, "val", factory);
		expected = "Filter by INSURANCE COMPANY CITY with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ID, "val", factory);
		expected = "Filter by INSURANCE COMPANY ID with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_NAME, "val", factory);
		expected = "Filter by INSURANCE COMPANY NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_PHONE, "val", factory);
		expected = "Filter by INSURANCE COMPANY PHONE # with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_STATE, "val", factory);
		expected = "Filter by INSURANCE COMPANY STATE with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ZIP, "val", factory);
		expected = "Filter by INSURANCE COMPANY ZIPCODE with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_FIRST_NAME, "val", factory);
		expected = "Filter by PARENT'S FIRST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.PARENT_LAST_NAME, "val", factory);
		expected = "Filter by PARENT'S LAST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.PHONE, "val", factory);
		expected = "Filter by PHONE # with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.SIBLING_FIRST_NAME, "val", factory);
		expected = "Filter by SIBLING'S FIRST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.SIBLING_LAST_NAME, "val", factory);
		expected = "Filter by SIBLING'S LAST NAME with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.STATE, "val", factory);
		expected = "Filter by STATE with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.STREET_ADDR, "val", factory);
		expected = "Filter by STREET ADDRESS with value val";
		assertEquals(expected, filter.toString());

		filter = new DemographicReportFilter(DemographicReportFilterType.ZIP, "val", factory);
		expected = "Filter by ZIPCODE with value val";
		assertEquals(expected, filter.toString());
		
		filter = new DemographicReportFilter(DemographicReportFilterType.GENDER, "val", factory);
		expected = "Filter by GENDER with value val";
		assertEquals(expected, filter.toString());
		
		filter = new DemographicReportFilter(DemographicReportFilterType.LOWER_AGE_LIMIT, "val", factory);
		expected = "Filter by LOWER AGE LIMIT with value val";
		assertEquals(expected, filter.toString());
		
		filter = new DemographicReportFilter(DemographicReportFilterType.UPPER_AGE_LIMIT, "val", factory);
		expected = "Filter by UPPER AGE LIMIT with value val";
		assertEquals(expected, filter.toString());
		
		filter = new DemographicReportFilter(DemographicReportFilterType.MID, "val", factory);
		expected = "Filter by MID with value val";
		assertEquals(expected, filter.toString());
	}

	public void testFilterTypeFromString() {
		DemographicReportFilterType expected = DemographicReportFilterType.EMER_CONTACT_NAME;
		DemographicReportFilterType actual = DemographicReportFilter
				.filterTypeFromString("emer_contACT_naME");
		assertEquals(expected, actual);
	}

	public void testGetFilterType() {
		filter = new DemographicReportFilter(DemographicReportFilterType.CITY, "city!", factory);
		DemographicReportFilterType expected = DemographicReportFilterType.CITY;
		assertEquals(expected, filter.getFilterType());
	}

	public void testGetFilterValue() {
		filter = new DemographicReportFilter(DemographicReportFilterType.CITY, "city!", factory);
		String expected = "city!";
		assertEquals(expected, filter.getFilterValue());
	}
	
	public void testFilterByInvalidLowerAge() throws Exception { 
		filter = new DemographicReportFilter(DemographicReportFilterType.LOWER_AGE_LIMIT, "-1", factory);
		try{
			@SuppressWarnings("unused")
			List<PatientBean> res = filter.filter(allPatients);
		}catch(NumberFormatException e){
			//exception is good
			return;
		}
		assertTrue(false);
	}
	
	public void testFilterByInvalidUpperAge() throws Exception { 
		filter = new DemographicReportFilter(DemographicReportFilterType.UPPER_AGE_LIMIT, "-1", factory);
		try{
			@SuppressWarnings("unused")
			List<PatientBean> res = filter.filter(allPatients);
		}catch(NumberFormatException e){
			//exception is good
			return;
		}
		assertTrue(false);
	}
	
	public void testFilterByMID() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.MID, "101", factory);
		List<PatientBean> filteredList = filter.filter(allPatients);
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(101L, patient.getMID());
	}
	
	public void testFilterByICName() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcName("FilterTest");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_NAME, "FilterTest", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICID() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcID("FilterTest");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ID, "FilterTest", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICCity() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcCity("FilterTest");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_CITY, "FilterTest", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICState() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcState("MO");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_STATE, "MO", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		int originalFromMOSize = 0;
		for (PatientBean p : allPatients) {
			if (p.getIcState().equals("MO"))
				originalFromMOSize++;
		}
		
		assertEquals(originalFromMOSize + 1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICPhone() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcPhone("FilterTest");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_PHONE, "FilterTest", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICAddress1() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcAddress1("FilterTest");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ADDR, "FilterTest", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICAddress2() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcAddress2("FilterTest");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ADDR, "FilterTest", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByICAddress3() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setIcAddress1("Filter");
		testPat.setIcAddress2("Test");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.INSURE_ADDR, "Filter Test", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		assertEquals(1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testFilterByExcludeDeactivated() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setDateOfDeactivationStr("09/25/2013");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.DEACTIVATED, "exclude", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		int originalNotDeactivatedSize = 0;
		for (PatientBean p : allPatients) {
			if (p.getDateOfDeactivationStr().equals(""))
				originalNotDeactivatedSize++;
		}
		assertEquals(originalNotDeactivatedSize - 1, filteredList.size());
	}
	
	public void testFilterByOnlyDeactivated() throws Exception {
		PatientBean testPat = pDAO.getPatient(1L);
		testPat.setDateOfDeactivationStr("09/25/2013");
		pDAO.editPatient(testPat, 9000000000L);
		filter = new DemographicReportFilter(DemographicReportFilterType.DEACTIVATED, "only", factory);
		List<PatientBean> filteredList = filter.filter(pDAO.getAllPatients());
		
		int originalDeactivatedSize = 0;
		for (PatientBean p : allPatients) {
			if (!p.getDateOfDeactivationStr().equals(""))
				originalDeactivatedSize++;
		}
		assertEquals(originalDeactivatedSize + 1, filteredList.size());
		PatientBean patient = filteredList.get(0);
		assertEquals(1L, patient.getMID());
	}
	
	public void testGetFilterTypeString() throws Exception {
		filter = new DemographicReportFilter(DemographicReportFilterType.DEACTIVATED, "only", factory);
		assertEquals("DEACTIVATED", filter.getFilterTypeString());
		
		filter = new DemographicReportFilter(DemographicReportFilterType.STATE, "only", factory);
		assertEquals("STATE", filter.getFilterTypeString());
	}
}
