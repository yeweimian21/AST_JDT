package edu.ncsu.csc.itrust.unit.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.PatientHistoryBean;
import edu.ncsu.csc.itrust.enums.BloodType;

/**
 */
public class PatientHistoryBeanTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	/**
	 * testAgeZero
	 * @throws Exception
	 */
	public void testAgeZero() throws Exception {
		PatientHistoryBean baby = new PatientHistoryBean();
		baby.setDateOfBirthStr(new SimpleDateFormat("MM/dd/yyyy").format(today));
		assertEquals(0, baby.getAge());
	}

	/**
	 * testAge10
	 * @throws Exception
	 */
	public void testAge10() throws Exception {
		PatientHistoryBean kid = new PatientHistoryBean();
		kid.setDateOfBirthStr(DateUtil.yearsAgo(10));
		assertEquals(10, kid.getAge());
	}

	/**
	 * testBean
	 */
	public void testBean() {
		PatientHistoryBean p = new PatientHistoryBean();
		p.setBloodType(BloodType.ABNeg);
		p.setFirstName("John");
		p.setLastName("Doe");
		p.setDateOfBirthStr("bad date");
		p.setDateOfDeathStr("another bad date");
		p.setCity("Raleigh");
		p.setState("NC");
		p.setZip("27613-1234");
		p.setIcCity("Raleigh");
		p.setIcState("NC");
		p.setIcZip("27613-1234");
		p.setEmergencyPhone("910-222-1212");
		p.setIcPhone("919-123-4567");
		p.setPhone("999-111-2222");
		p.setSecurityQuestion("Question");
		p.setSecurityAnswer("Answer");
		p.setPassword("password");
		p.setConfirmPassword("confirm");
		p.setCreditCardNumber("1234567812345678");
		p.setChangeDateStr("12/09/1991");
		p.setChangeMID(888L);
		assertEquals(BloodType.ABNeg, p.getBloodType());
		assertNull(p.getDateOfBirth());
		assertNull(p.getDateOfDeath());
		assertEquals("John Doe", p.getFullName());
		assertEquals("1234567812345678", p.getCreditCardNumber());
		assertEquals("919-123-4567", p.getIcPhone());
		assertEquals("919-123-4567", p.getIcPhone());
		assertEquals("999-111-2222", p.getPhone());
		assertEquals("910-222-1212", p.getEmergencyPhone());
		assertEquals(-1, p.getAge());
		assertEquals(-1, p.getAgeInDays());
		assertEquals(-1, p.getAgeInWeeks());
		assertEquals("Raleigh, NC 27613-1234", p.getIcAddress3());
		assertEquals("Raleigh, NC 27613-1234", p.getStreetAddress3());
		assertEquals("Question", p.getSecurityQuestion());
		assertEquals("Answer", p.getSecurityAnswer());
		assertEquals("password", p.getPassword());
		assertEquals("confirm", p.getConfirmPassword());
		assertEquals("12/09/1991", p.getChangeDateStr());
		assertNotNull(p.getChangeDate());
		assertEquals(888L, p.getChangeMID());
	}
	
	/**
	 * testGetChangeDate
	 */
	public void testGetChangeDate() {
		PatientHistoryBean b = new PatientHistoryBean();
		b.setChangeDateStr("06/06/2009");
		assertEquals("06/06/2009", b.getChangeDateStr());
		assertEquals("06/06/2009", new SimpleDateFormat("MM/dd/yyyy").format(b.getChangeDate()));
	}
}
