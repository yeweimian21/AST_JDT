/**
 * Tests AddDrugListAction for importing NDC information.
 */

package edu.ncsu.csc.itrust.unit.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AddDrugListAction.SkipDuplicateDrugStrategy;
import edu.ncsu.csc.itrust.action.AddDrugListAction;
import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.action.AddDrugListAction.OverwriteDuplicateDrugStrategy;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddDrugListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AddDrugListAction action;
	private TestDataGenerator gen;
	/*
	 * Four lines selected from the text download of the fall 2011 version of the FDA NDC database
	 */
	private String inputDrugs1 = 
			"0573-0150	HUMAN OTC DRUG	ADVIL		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
			"50458-513	HUMAN PRESCRIPTION DRUG	TYLENOL with Codeine		ACETAMINOPHEN AND CODEINE PHOSPHATE	TABLET	ORAL	19770817		ANDA	ANDA085055	Janssen Pharmaceuticals, Inc.	ACETAMINOPHEN; CODEINE PHOSPHATE	300; 30	mg/1; mg/1		CIII\n" +
			"10544-591	HUMAN PRESCRIPTION DRUG	OxyContin		OXYCODONE HYDROCHLORIDE	TABLET, FILM COATED, EXTENDED RELEASE	ORAL	20100126		NDA	NDA020553	Blenheim Pharmacal, Inc.	OXYCODONE HYDROCHLORIDE	10	mg/1	Opioid Agonist [EPC], Full Opioid Agonists [MoA]	CII\n" +
			"11523-7197	HUMAN OTC DRUG	Claritin		LORATADINE	SOLUTION	ORAL	20110301		NDA	NDA020641	Schering Plough Healthcare Products Inc.	LORATADINE	5	mg/5mL		\n";
	
	private String inputDrugs2 = 
			"0573-0150	HUMAN OTC DRUG	New Advil		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
			"0574-0230	HUMAN OTC DRUG	New Drug		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
			"11523-7197	HUMAN OTC DRUG	Totally Legal Drug		LORATADINE	SOLUTION	ORAL	20110301		NDA	NDA020641	Schering Plough Healthcare Products Inc.	LORATADINE	5	mg/5mL		\n";

	
	
	public AddDrugListActionTest() {
		gen = new TestDataGenerator();
	}
	
	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	public void testLoadFile() throws Exception {
		InputStream is = new ByteArrayInputStream(inputDrugs1.getBytes());
		action = new AddDrugListAction(new SkipDuplicateDrugStrategy(), factory, new EventLoggingAction(factory), 9000000001L);
		action.loadFile(is);
		
		NDCodesDAO ndCodesDAO = factory.getNDCodesDAO();
		assertEquals(4, ndCodesDAO.getAllNDCodes().size());
		
		
		assertNotNull(ndCodesDAO.getNDCode("05730150"));
		assertEquals("ADVIL", ndCodesDAO.getNDCode("05730150").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("50458513"));
		assertEquals("TYLENOL with Codeine", ndCodesDAO.getNDCode("50458513").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("10544591"));
		assertEquals("OxyContin", ndCodesDAO.getNDCode("10544591").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("115237197"));
		assertEquals("Claritin", ndCodesDAO.getNDCode("115237197").getDescription());
	}
	
	public void testRenameDrugs() throws Exception {
		InputStream is = new ByteArrayInputStream(inputDrugs1.getBytes());
		action = new AddDrugListAction(new SkipDuplicateDrugStrategy(), factory, new EventLoggingAction(factory), 9000000001L);
		action.loadFile(is);
		
		NDCodesDAO ndCodesDAO = factory.getNDCodesDAO();
		assertEquals(4, ndCodesDAO.getAllNDCodes().size());
		
		
		assertNotNull(ndCodesDAO.getNDCode("05730150"));
		assertEquals("ADVIL", ndCodesDAO.getNDCode("05730150").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("50458513"));
		assertEquals("TYLENOL with Codeine", ndCodesDAO.getNDCode("50458513").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("10544591"));
		assertEquals("OxyContin", ndCodesDAO.getNDCode("10544591").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("115237197"));
		assertEquals("Claritin", ndCodesDAO.getNDCode("115237197").getDescription());
		
		
		is = new ByteArrayInputStream(inputDrugs2.getBytes());
		action = new AddDrugListAction(new OverwriteDuplicateDrugStrategy(), factory, new EventLoggingAction(factory), 9000000001L);
		action.loadFile(is);
		
		assertEquals(5, ndCodesDAO.getAllNDCodes().size());
		
		assertNotNull(ndCodesDAO.getNDCode("05730150"));
		assertEquals("New Advil", ndCodesDAO.getNDCode("05730150").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("50458513"));
		assertEquals("TYLENOL with Codeine", ndCodesDAO.getNDCode("50458513").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("10544591"));
		assertEquals("OxyContin", ndCodesDAO.getNDCode("10544591").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("115237197"));
		assertEquals("Totally Legal Drug", ndCodesDAO.getNDCode("115237197").getDescription());
		assertNotNull(ndCodesDAO.getNDCode("05740230"));
		assertEquals("New Drug", ndCodesDAO.getNDCode("05740230").getDescription());
		}
}
