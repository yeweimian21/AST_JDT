package edu.ncsu.csc.itrust.unit.bean;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.GroupReportBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.report.DemographicReportFilter;
import edu.ncsu.csc.itrust.report.DemographicReportFilter.DemographicReportFilterType;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.report.ReportFilter;
import junit.framework.TestCase;

public class GroupReportBeanTest extends TestCase {

	private GroupReportBean bean;
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private PatientDAO pDAO = factory.getPatientDAO();

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		List<PatientBean> p = new ArrayList<PatientBean>();
		p.add(pDAO.getPatient(1L));
		List<ReportFilter> f = new ArrayList<ReportFilter>();
		f.add(new DemographicReportFilter(DemographicReportFilterType.LAST_NAME, "Person", factory));
		bean = new GroupReportBean(p, f);
	}

	public void testGetPatients() {
		List<PatientBean> p = bean.getPatients();
		assertTrue(p.size() == 1);
		assertTrue(p.get(0).getMID() == 1L);
	}

	public void testGetFilters() {
		List<ReportFilter> f = bean.getFilters();
		assertTrue(f.size() == 1);
		assertEquals(DemographicReportFilterType.LAST_NAME,
				((DemographicReportFilter) f.get(0)).getFilterType());
	}

	public void testGetFilterStrings() {
		List<String> f = bean.getFilterStrings();
		assertTrue(f.size() == 1);
		assertEquals("Filter by LAST NAME with value Person", f.get(0));
	}

	public void testGetPatientNames() {
		List<String> p = bean.getPatientNames();
		assertTrue(p.size() == 1);
		assertEquals("Random Person", p.get(0));
	}

}
