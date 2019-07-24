package edu.ncsu.csc.itrust.unit.action;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.UpdateLOINCListAction;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LOINCDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * UpdateLabProcListActionTest
 */
public class UpdateLabProcListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private UpdateLOINCListAction action;
	private TestDataGenerator gen;
	private LOINCDAO dao;
	@SuppressWarnings("unused")
	private static long performingAdmin = 9000000001L;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		dao = factory.getLOINCDAO();
		action = new UpdateLOINCListAction(factory);
		gen.clearAllTables();
		gen.admin1();
		gen.loincs();

	}

	/**
	 * testEvilFactory
	 * @throws Exception
	 */
	public void testEvilFactory() throws Exception {
		action = new UpdateLOINCListAction(EvilDAOFactory.getEvilInstance());
		String code = "28473-7";
		String com = "Poison Ivy";
		String kop = "VOL";
		LOINCbean db = new LOINCbean();
		db.setLabProcedureCode(code);
		db.setComponent(com);
		db.setKindOfProperty(kop);

		try {
			action.add(db);
			fail("exception should have been thrown");
		} catch (ITrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}
		assertEquals("A database exception has occurred. Please see the log in the console for stacktrace",
				action.updateInformation(db));

	}

	private String getAddCodeSuccessString(LOINCbean proc) {
		return "Success: " + proc.getLabProcedureCode() + " added";
	}

	/**
	 * testAddICDCode
	 * @throws Exception
	 */
	public void testAddICDCode() throws Exception {
		String code = "78743-7";
		String com = "Poison Oak";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals(getAddCodeSuccessString(proc), action.add(proc));
		proc = factory.getLOINCDAO().getProcedures(code).get(0);
		assertEquals(com, proc.getComponent());
		assertEquals(kop, proc.getKindOfProperty());

	}

	/**
	 * testAddDuplicate
	 * @throws Exception
	 */
	public void testAddDuplicate() throws Exception {
		String code = "73823-7";
		String com = "Yellow Tooth";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals(getAddCodeSuccessString(proc), action.add(proc));
		proc.setKindOfProperty("VIL");
		try {
			action.add(proc);
			fail("FormValidationException should have been thrown");
		} catch (Exception ex) {
			assertEquals(
					"This form has not been validated correctly. The following field are not properly filled in: [Error: Code already exists.]",
					ex.getMessage());
		}
		proc = factory.getLOINCDAO().getProcedures(code).get(0);
		assertEquals(kop, proc.getKindOfProperty());
	}

	/**
	 * testUpdateICDInformation0
	 * @throws Exception
	 */
	public void testUpdateICDInformation0() throws Exception {
		String code = "98323-7";
		String com = "Malaria";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals(getAddCodeSuccessString(proc), action.add(proc));
		proc.setKindOfProperty("Per");
		assertEquals("Success: " + proc.getLabProcedureCode() + " updated", action.updateInformation(proc));
		proc = factory.getLOINCDAO().getProcedures(code).get(0);
		assertEquals("Per", proc.getKindOfProperty());
	}

	/**
	 * testUpdateNonExistent
	 * @throws Exception
	 */
	public void testUpdateNonExistent() throws Exception {
		String code = "99999-9";
		String com = "Malaria";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals("Error: Code not found.", action.updateInformation(proc));
		assertTrue(factory.getLOINCDAO().getProcedures(code).isEmpty());
	}
	
	/**
	 * testParseLOINCFile
	 * @throws Exception
	 */
	public void testParseLOINCFile() throws Exception {
		gen.clearAllTables();
		
		List<String> expected = new ArrayList<String>();
		expected.add("IGNORED LINE 1: ");
		expected.add("IGNORED LINE 2: the use of this data in direct clinical care, research and practice management.");
		expected.add("IGNORED LINE 3: <----Clip Here for Data----->");
		expected.add("IGNORED LINE 4: \"LOINC_NUM\"	\"COMPONENT\"	\"PROPERTY\"	\"TIME_ASPCT\"	\"SYSTEM\"	\"SCALE_TYP\"	\"METHOD_TYP\"	\"CLASS\"	\"SOURCE\"	\"DATE_LAST_CHANGED\"	\"CHNG_TYPE\"	\"COMMENTS\"	\"STATUS\"	\"MAP_TO\"	\"CONSUMER_NAME\"	\"MOLAR_MASS\"	\"CLASSTYPE\"	\"FORMULA\"	\"SPECIES\"	\"EXMPL_ANSWERS\"	\"ACSSYM\"	\"BASE_NAME\"	\"NAACCR_ID\"	\"CODE_TABLE\"	\"SURVEY_QUEST_TEXT\"	\"SURVEY_QUEST_SRC\"	\"UNITSREQUIRED\"	\"SUBMITTED_UNITS\"	\"RELATEDNAMES2\"	\"SHORTNAME\"	\"ORDER_OBS\"	\"CDISC_COMMON_TESTS\"	\"HL7_FIELD_SUBFIELD_ID\"	\"EXTERNAL_COPYRIGHT_NOTICE\"	\"EXAMPLE_UNITS\"	\"LONG_COMMON_NAME\"	\"HL7_V2_DATATYPE\"	\"HL7_V3_DATATYPE\"	\"CURATED_RANGE_AND_UNITS\"	\"DOCUMENT_SECTION\"	\"EXAMPLE_UCUM_UNITS\"	\"EXAMPLE_SI_UCUM_UNITS\"	\"STATUS_REASON\"	\"STATUS_TEXT\"	\"CHANGE_REASON_PUBLIC\"	\"COMMON_TEST_RANK\"	\"COMMON_ORDER_RANK\"");
		expected.add("Successfully added 29 lines of new LOINC data. Updated 0 lines of existing LOINC data.");
		String filename = "testing-files/sample_loinc/sampleLoinc.txt";
		FileInputStream stream = new FileInputStream(filename);
		List<String> actual = action.parseLOINCFile(stream , true);
		assertEquals(expected, actual);
		
		assertEquals(29, dao.getAllLOINC().size());
		assertTrue(dao.getProcedures("10053-7").get(0).getComponent().equals("S wave duration.lead V2"));
	}
	
	/**
	 * testParseLOINCFile2
	 * @throws Exception
	 */
	public void testParseLOINCFile2() throws Exception {
		gen.clearAllTables();
		
		List<String> expected = new ArrayList<String>();
		expected.add("Successfully added 3 lines of new LOINC data. Updated 0 lines of existing LOINC data.");
		String filename = "testing-files/sample_loinc/sampleLoinc2.txt";
		FileInputStream stream = new FileInputStream(filename);
		List<String> actual = action.parseLOINCFile(stream , false);
		assertEquals(expected, actual);
		
		assertEquals(3, dao.getAllLOINC().size());
		assertTrue(dao.getProcedures("10053-7").get(0).getComponent().equals("S wave duration.lead V2"));
	}
	
	/**
	 * testParseLOINCFileIgnore
	 * @throws Exception
	 */
	public void testParseLOINCFileIgnore() throws Exception {
		gen.clearAllTables();
		
		String filename1 = "testing-files/sample_loinc/sampleLoinc.txt";
		String filename2 = "testing-files/sample_loinc/sampleLoinc2.txt";
		FileInputStream stream1 = new FileInputStream(filename1);
		FileInputStream stream2 = new FileInputStream(filename2);
		action.parseLOINCFile(stream1 , true);
		assertEquals(29, dao.getAllLOINC().size());
		
		action.parseLOINCFile(stream2 , true);
		assertEquals(29, dao.getAllLOINC().size());
		
		assertTrue(dao.getProcedures("10054-5").get(0).getComponent().equals("S wave duration.lead V3"));
	}
	
	/**
	 * testParseLOINCFileReplace
	 * @throws Exception
	 */
	public void testParseLOINCFileReplace() throws Exception {
		gen.clearAllTables();
		
		String filename1 = "testing-files/sample_loinc/sampleLoinc.txt";
		String filename2 = "testing-files/sample_loinc/sampleLoinc2.txt";
		FileInputStream stream1 = new FileInputStream(filename1);
		FileInputStream stream2 = new FileInputStream(filename2);
		action.parseLOINCFile(stream1 , false);
		assertEquals(29, dao.getAllLOINC().size());
		
		action.parseLOINCFile(stream2 , false);
		assertEquals(29, dao.getAllLOINC().size());
		
		assertTrue(dao.getProcedures("10054-5").get(0).getSystem() == null);
	}

	/**
	 * testParseBadLOINCFile
	 * @throws Exception
	 */
	public void testParseBadLOINCFile() throws Exception {
		List<String> expected = new ArrayList<String>();
		expected.add("IGNORED LINE 1: This file contains no LOINC data and should fail the LOINC file verification process.");
		expected.add("File invalid. No LOINC data added.");
		String filename = "testing-files/sample_loinc/badLoincFile.txt";
		FileInputStream stream = new FileInputStream(filename);
		List<String> actual = action.parseLOINCFile(stream , false);
		assertEquals(expected, actual);
	}
	
	/**
	 * testParseInvalidLOINCFile
	 * @throws Exception
	 */
	public void testParseInvalidLOINCFile() throws Exception {
		List<String> expected = new ArrayList<String>();
		expected.add("ERROR, LINE 2: \"10054-5\"	\"I skip rest of fields\" This form has not been validated correctly. The following field are not properly filled in: [You must have a Lab Procedure Code, Component and Kind Of Property]");
		expected.add("Successfully added 2 lines of new LOINC data. Updated 0 lines of existing LOINC data.");
		String filename = "testing-files/sample_loinc/invalidLine.txt";
		FileInputStream stream = new FileInputStream(filename);
		List<String> actual = action.parseLOINCFile(stream , false);
		assertEquals(expected, actual);
	}
	
	/**
	 * testParseInvalidLOINCFile2
	 * @throws Exception
	 */
	public void testParseInvalidLOINCFile2() throws Exception {
		List<String> expected = new ArrayList<String>();
		expected.add("ERROR, LINE 2: \"10054-5\"	'oh no! bad quoting\"	\"Time\"	\"Pt\"	\"Heart\"	\"Qn\"	\"EKG\"	\"EKG.MEAS\"	\"CH\"	\"19980820\"	\"NAM\"		\"ACTIVE\" This form has not been validated correctly. The following field are not properly filled in: [Bad LOINC data line. All fields must be surrounded by quotation marks.]");
		expected.add("Successfully added 2 lines of new LOINC data. Updated 0 lines of existing LOINC data.");
		String filename = "testing-files/sample_loinc/invalidLine2.txt";
		FileInputStream stream = new FileInputStream(filename);
		List<String> actual = action.parseLOINCFile(stream , false);
		assertEquals(expected, actual);
	}
}
