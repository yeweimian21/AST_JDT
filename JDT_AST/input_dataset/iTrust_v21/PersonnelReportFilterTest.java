package edu.ncsu.csc.itrust.unit.report;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.report.PersonnelReportFilter;
import edu.ncsu.csc.itrust.report.PersonnelReportFilter.PersonnelReportFilterType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class PersonnelReportFilterTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private PatientDAO pDAO = factory.getPatientDAO();
	private List<PatientBean> allPatients;
	private PersonnelReportFilter filter;
	private TestDataGenerator gen = new TestDataGenerator();

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		allPatients = pDAO.getAllPatients();
	}

	public void testFilterByProcedure() throws Exception {
		filter = new PersonnelReportFilter(PersonnelReportFilterType.DLHCP, "Beaker Beaker", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertEquals(2, res.size());
		assertTrue(res.get(0).getMID() == 22L); 
		assertTrue(res.get(1).getMID() == 23L);

	}

	public void testFilterByProcedureNoResult() {
		filter = new PersonnelReportFilter(PersonnelReportFilterType.DLHCP, "Dalpe", factory);
		List<PatientBean> res = filter.filter(allPatients);
		assertTrue(res.isEmpty());
	}
	
	public void testToString() {
		String expected = "";
		filter = new PersonnelReportFilter(PersonnelReportFilterType.DLHCP, "val", factory);
		expected = "Filter by DECLARED HCP with value val";
		assertEquals(expected, filter.toString());
	}

	public void testFilterTypeFromString() {
		PersonnelReportFilterType expected = PersonnelReportFilterType.DLHCP;
		PersonnelReportFilterType actual = PersonnelReportFilter
				.filterTypeFromString("dLhCP");
		assertEquals(expected, actual);
	}

	public void testGetFilterType() {
		filter = new PersonnelReportFilter(PersonnelReportFilterType.DLHCP, "city!", factory);
		PersonnelReportFilterType expected = PersonnelReportFilterType.DLHCP;
		assertEquals(expected, filter.getFilterType());
	}

	public void testGetFilterValue() {
		filter = new PersonnelReportFilter(PersonnelReportFilterType.DLHCP, "city!", factory);
		String expected = "city!";
		assertEquals(expected, filter.getFilterValue());
	}
	
}
