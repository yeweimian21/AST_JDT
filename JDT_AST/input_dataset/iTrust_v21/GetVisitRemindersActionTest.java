package edu.ncsu.csc.itrust.unit.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.GetVisitRemindersAction;
import edu.ncsu.csc.itrust.action.GetVisitRemindersAction.ReminderType;
import edu.ncsu.csc.itrust.beans.VisitFlag;
import edu.ncsu.csc.itrust.beans.forms.VisitReminderReturnForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * GetVisitRemindersActionTest
 */
public class GetVisitRemindersActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private GetVisitRemindersAction action;
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	/**
	 * testNormalReturn
	 * @throws Exception
	 */
	public void testNormalReturn() throws Exception {
		//Test that no exceptions are thrown on method call
		//check valid data returns
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		
		action.getVisitReminders(GetVisitRemindersAction.ReminderType.DIAGNOSED_CARE_NEEDERS);
		action.getVisitReminders(GetVisitRemindersAction.ReminderType.FLU_SHOT_NEEDERS);
	}	
	
	/**
	 * testGetVisitReminders_AlivePatients
	 * @throws Exception
	 */
	public void testGetVisitReminders_AlivePatients() throws Exception {
		gen.hcp0();
		gen.aliveRecurringPatients();
		action = new GetVisitRemindersAction(factory, 9000000000L);

		assertEquals(1, action.getVisitReminders(ReminderType.FLU_SHOT_NEEDERS).size());
		assertEquals(1, action.getVisitReminders(ReminderType.DIAGNOSED_CARE_NEEDERS).size());
		assertEquals(1, action.getVisitReminders(ReminderType.IMMUNIZATION_NEEDERS).size());
	}
	
	/**
	 * testGetVisitReminders_DeadPatients
	 * @throws Exception
	 */
	public void testGetVisitReminders_DeadPatients() throws Exception {
		gen.hcp0();
		gen.deadRecurringPatients();
		action = new GetVisitRemindersAction(factory, 9000000000L);

		assertEquals(0, action.getVisitReminders(ReminderType.FLU_SHOT_NEEDERS).size());
		assertEquals(0, action.getVisitReminders(ReminderType.DIAGNOSED_CARE_NEEDERS).size());
		assertEquals(0, action.getVisitReminders(ReminderType.IMMUNIZATION_NEEDERS).size());
	}

	/**
	 * testGetVisitReminders_Diagnosed_OldAndRecentVisit
	 * @throws Exception
	 */
	public void testGetVisitReminders_Diagnosed_OldAndRecentVisit() throws Exception {
		gen.hcp0();
		gen.diagnosedPatient_OldAndNewVisit();
		action = new GetVisitRemindersAction(factory, 9000000000L);
		
		//Patient had a visit over a year ago. Make sure that he isn't given a reminder for this year
		//since he also had a visit 3 months ago.
		assertEquals(0, action.getVisitReminders(ReminderType.DIAGNOSED_CARE_NEEDERS).size());
	}
	
	/**
	 * testGetVisitReminders_CorrectDateOfRecentVisit
	 * @throws Exception
	 */
	public void testGetVisitReminders_CorrectDateOfRecentVisit() throws Exception {
		gen.hcp0();
		gen.aliveRecurringPatients();
		action = new GetVisitRemindersAction(factory, 9000000000L);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar twoYrsAgo = Calendar.getInstance();
		twoYrsAgo.add(Calendar.YEAR, -2);
		
		List<VisitReminderReturnForm> vReminders = action.getVisitReminders(ReminderType.DIAGNOSED_CARE_NEEDERS);
		String date = "";
		for (VisitFlag vf : vReminders.get(0).getVisitFlags()) {
			if (vf.getType().equals(VisitFlag.LAST_VISIT)) {
				date = vf.getValue();
			}
		}
		
		assertEquals(sdf.format(twoYrsAgo.getTime()), date);
		
	}
	
	/**
	 * testNoSubAction
	 * @throws Exception
	 */
	public void testNoSubAction() throws Exception {
		//Test that standardData creates an initial 3 visit reminders
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		
		//Returns reminders for NoRecords Has, Bad Horse, Care Needs, and Random Person
		assertEquals(9, action.getVisitReminders(GetVisitRemindersAction.ReminderType.FLU_SHOT_NEEDERS).size());
	}
	
	/**
	 * testGetReminderType
	 * @throws Exception
	 */
	public void testGetReminderType() throws Exception {
		//Test that Enum->String translation works properly
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		
		assertEquals(GetVisitRemindersAction.ReminderType.DIAGNOSED_CARE_NEEDERS, 
					 GetVisitRemindersAction.ReminderType.getReminderType("Diagnosed Care Needers"));
	}
	
	/**
	 * testBadReminderType
	 * @throws Exception
	 */
	public void testBadReminderType() throws Exception {
		//Test that a null ReminderType is not allowed
		//Check other failure inputs
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		try {
			action.getVisitReminders(null);
			fail("testBadReminderType: bad reminder type not caught");
		} catch(ITrustException e) {
			assertEquals("Reminder Type DNE", e.getMessage());
		}
	}

	/**
	 * testTestHPV
	 * @throws Exception
	 */
	public void testTestHPV() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -9);
		assertEquals("90649 Human Papillomavirus (9 years), ", GetVisitRemindersAction.testHPV(0, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -1);
		assertEquals("90649 Human Papillomavirus (9 years), ", GetVisitRemindersAction.testHPV(0, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -1);
		assertEquals("90649 Human Papillomavirus (9 years, 2 months), ", GetVisitRemindersAction.testHPV(1, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -4);
		assertEquals("90649 Human Papillomavirus (9 years, 6 months), ", GetVisitRemindersAction.testHPV(2, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -1);
		assertEquals("90649 Human Papillomavirus (9 years, 6 months), ", GetVisitRemindersAction.testHPV(2, cal.getTime(), 0L));
	}
	
	/**
	 * testTestHepA
	 * @throws Exception
	 */
	public void testTestHepA() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		assertEquals("90633 Hepatits A (12 months), ", GetVisitRemindersAction.testHepA(0, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -6);
		assertEquals("90633 Hepatits A (18 months), ", GetVisitRemindersAction.testHepA(1, cal.getTime(), 0L));
	}
	
	/**
	 * testTestVaricella
	 * @throws Exception
	 */
	public void testTestVaricella() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		assertEquals("90396 Varicella (12 months), ", GetVisitRemindersAction.testVaricella(0, cal.getTime(), 0L));
		cal.add(Calendar.YEAR, -3);
		assertEquals("90396 Varicella (4 years), ", GetVisitRemindersAction.testVaricella(1, cal.getTime(), 0L));
	}

	/**
	 * testTestMeasles
	 * @throws Exception
	 */
	public void testTestMeasles() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		assertEquals("90707 Measles, Mumps, Rubekka (12 months), ", GetVisitRemindersAction.testMeasles(0, cal.getTime(), 0L));
		cal.add(Calendar.YEAR, -3);
		assertEquals("90707 Measles, Mumps, Rubekka (4 years), ", GetVisitRemindersAction.testMeasles(1, cal.getTime(), 0L));
	}

	/**
	 * testTestPolio
	 * @throws Exception
	 */
	public void testTestPolio() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -6*7*24);
		assertEquals("90712 Poliovirus (6 weeks), ", GetVisitRemindersAction.testPolio(0, cal.getTime(), 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -4);
		assertEquals("90712 Poliovirus (4 months), ", GetVisitRemindersAction.testPolio(1, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -2);
		assertEquals("90712 Poliovirus (6 months), ", GetVisitRemindersAction.testPolio(2, cal.getTime(), 0L));
	}
	
	/**
	 * testTestPneumo
	 * @throws Exception
	 */
	public void testTestPneumo() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -6*7*24);
		assertEquals("90669 Pneumococcal (6 weeks), ", GetVisitRemindersAction.testPneumo(0, cal.getTime(), 0L, 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -4);
		assertEquals("90669 Pneumococcal (4 months), ", GetVisitRemindersAction.testPneumo(1, cal.getTime(), 0L, 0L));
		cal.add(Calendar.MONTH, -6);
		assertEquals("90669 Pneumococcal (6 months), ", GetVisitRemindersAction.testPneumo(2, cal.getTime(), 0L, 0L));
		cal.add(Calendar.MONTH, -12);
		assertEquals("90669 Pneumococcal (12 months), ", GetVisitRemindersAction.testPneumo(3, cal.getTime(), 0L, 0L));
	}
	
	/**
	 * testTestHaemoFlu
	 * @throws Exception
	 */
	public void testTestHaemoFlu() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -6*7*24);
		assertEquals("90645 Haemophilus influenzae (6 weeks), ", GetVisitRemindersAction.testHaemoFlu(0, cal.getTime(), 0L, 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -4);
		assertEquals("90645 Haemophilus influenzae (4 months), ", GetVisitRemindersAction.testHaemoFlu(1, cal.getTime(), 0L, 0L));
		cal.add(Calendar.MONTH, -2);
		assertEquals("90645 Haemophilus influenzae (6 months), ", GetVisitRemindersAction.testHaemoFlu(2, cal.getTime(), 0L, 0L));
	}
	
	/**
	 * testTestDipTet
	 * @throws Exception
	 */
	public void testTestDipTet() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -6*7*24);
		assertEquals("90696 Diphtheria, Tetanus, Pertussis (6 weeks), ", GetVisitRemindersAction.testDipTet(0, cal.getTime(), 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -4);
		assertEquals("90696 Diphtheria, Tetanus, Pertussis (4 months), ", GetVisitRemindersAction.testDipTet(1, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -2);
		assertEquals("90696 Diphtheria, Tetanus, Pertussis (6 months), ", GetVisitRemindersAction.testDipTet(2, cal.getTime(), 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -15*7*24);
		assertEquals("90696 Diphtheria, Tetanus, Pertussis (15 weeks), ", GetVisitRemindersAction.testDipTet(3, cal.getTime(), 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -4);
		assertEquals("90696 Diphtheria, Tetanus, Pertussis (4 years), ", GetVisitRemindersAction.testDipTet(4, cal.getTime(), 0L));
		cal.add(Calendar.YEAR, -7);
		assertEquals("90696 Diphtheria, Tetanus, Pertussis (11 years), ", GetVisitRemindersAction.testDipTet(5, cal.getTime(), 0L));
	}
	
	/**
	 * testTestRotaVirus
	 * @throws Exception
	 */
	public void testTestRotaVirus() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -6*7*24);
		assertEquals("90681 Rotavirus (6 weeks), ", GetVisitRemindersAction.testRotaVirus(0, cal.getTime(), 0L));
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -4);
		assertEquals("90681 Rotavirus (4 months), ", GetVisitRemindersAction.testRotaVirus(1, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -2);
		assertEquals("90681 Rotavirus (6 months), ", GetVisitRemindersAction.testRotaVirus(2, cal.getTime(), 0L));
	}
	
	/**
	 * testTestHepB
	 * @throws Exception
	 */
	public void testTestHepB() throws Exception {
		//add boundary tests
		gen.standardData();
		//Login as kelly doctor
		action = new GetVisitRemindersAction(factory, 9000000000L);
		Calendar cal = Calendar.getInstance();
		assertEquals("90371 Hepatitis B (birth), ", GetVisitRemindersAction.testHepB(0, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -1);
		assertEquals("90371 Hepatitis B (1 month), ", GetVisitRemindersAction.testHepB(1, cal.getTime(), 0L));
		cal.add(Calendar.MONTH, -5);
		assertEquals("90371 Hepatitis B (6 months), ", GetVisitRemindersAction.testHepB(2, cal.getTime(), 0L));
	}

}
