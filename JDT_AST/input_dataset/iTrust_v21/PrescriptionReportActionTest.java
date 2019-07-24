package edu.ncsu.csc.itrust.unit.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.PrescriptionReportAction;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PrescriptionReportBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

@SuppressWarnings("unchecked")
public class PrescriptionReportActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private PrescriptionReportAction action;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.ndCodes();
		gen.hcp0();
		gen.patient2();		
		gen.officeVisit1();
		gen.additionalOfficeVisits();
	}

	public void testEmptyParamMap() throws Exception {
		action = new PrescriptionReportAction(factory, 9000000000L, "2");
		List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
		Map<String, String> params = new HashMap<String, String>();
		assertEquals("", action.getQueryString(params, officeVisits));
	}

	public void testQueryStringNotChecked() throws Exception {
		action = new PrescriptionReportAction(factory, 9000000000L, "2");
		List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
		@SuppressWarnings("rawtypes")
		Map params = new HashMap();
		params.put("ov0", new String[]{"unchecked"});
		params.put("ov2", new String[]{"unchecked"});
		params.put("ov3", new String[]{"unchecked"});
		params.put("ov4", new String[]{"unchecked"});
		params.put("ov5", new String[]{"unchecked"});
		params.put("ov6", new String[]{"unchecked"});
		params.put("ov7", new String[]{"unchecked"});
		params.put("ov8", new String[]{"unchecked"});
		assertEquals("", action.getQueryString(params, officeVisits));
	}

	public void testQueryString3Checked() throws Exception {
		action = new PrescriptionReportAction(factory, 9000000000L, "2");
		List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
		@SuppressWarnings("rawtypes")
		Map params = new HashMap();
		params.put("ov0", new String[]{"on"});
		params.put("ov1", new String[]{"on"});
		params.put("ov2", new String[]{"unchecked"});
		params.put("ov3", new String[]{"unchecked"});
		params.put("ov4", new String[]{"on"});
		params.put("ov5", new String[]{"unchecked"});
		params.put("ov6", new String[]{"unchecked"});
		params.put("ov7", new String[]{"unchecked"});
		params.put("ov8", new String[]{"unchecked"});
		assertEquals("&n=3&ovOff0=0&ovOff1=1&ovOff2=4", action.getQueryString(params, officeVisits));
	}

	public void testNotFull() throws Exception {
		action = new PrescriptionReportAction(factory, 9000000000L, "2");
		List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
		@SuppressWarnings("rawtypes")
		Map params = new HashMap();
		params.put("ov0", new String[]{"on"});
		params.put("ov1", new String[]{"on"});
		params.put("ov2", new String[]{"unchecked"});
		params.put("ov3", new String[]{"unchecked"});
		params.put("ov4", new String[]{"on"});
		assertEquals("&n=3&ovOff0=0&ovOff1=1&ovOff2=4", action.getQueryString(params, officeVisits));
	}

	public void testGetPrescriptionReports() throws Exception {
		action = new PrescriptionReportAction(factory, 9000000000L, "2");
		List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
		@SuppressWarnings("rawtypes")
		Map params = new HashMap();
		params.put("ovOff0", new String[]{"0"});
		params.put("ovOff1", new String[]{"1"});
		params.put("ovOff2", new String[]{"4"});
		List<PrescriptionReportBean> prescriptionReports = action.getPrescriptionReports(params, officeVisits);
		assertEquals(3, prescriptionReports.size());
		// use the office visit DAO test to verify that all of the correct info is taken
		assertEquals("Take twice daily", prescriptionReports.get(0).getPrescription().getInstructions());
		assertEquals("Take twice daily", prescriptionReports.get(1).getPrescription().getInstructions());
		assertEquals("Take twice daily", prescriptionReports.get(2).getPrescription().getInstructions());
	}	
}
