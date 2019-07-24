package edu.ncsu.csc.itrust.unit.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.enums.BloodType;

public class PatientBeanTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testAgeZero() throws Exception {
		PatientBean baby = new PatientBean();
		baby.setDateOfBirthStr(new SimpleDateFormat("MM/dd/yyyy").format(today));
		assertEquals(0, baby.getAge());
	}

	public void testAge10() throws Exception {
		PatientBean kid = new PatientBean();
		kid.setDateOfBirthStr(DateUtil.yearsAgo(10));
		assertEquals(10, kid.getAge());
	}

	public void testBean() {
		PatientBean p = new PatientBean();
		p.setBloodType(BloodType.ABNeg);
		p.setDateOfBirthStr("bad date");
		p.setCity("Raleigh");
		p.setState("NC");
		p.setZip("27613-1234");
		p.setIcCity("Raleigh");
		p.setIcState("NC");
		p.setIcZip("27613-1234");
		p.setSecurityQuestion("Question");
		p.setSecurityAnswer("Answer");
		p.setPassword("password");
		p.setConfirmPassword("confirm");
		assertEquals(BloodType.ABNeg, p.getBloodType());
		assertNull(p.getDateOfBirth());
		assertEquals(-1, p.getAge());
		assertEquals("Raleigh, NC 27613-1234", p.getIcAddress3());
		assertEquals("Raleigh, NC 27613-1234", p.getStreetAddress3());
		assertEquals("Question", p.getSecurityQuestion());
		assertEquals("Answer", p.getSecurityAnswer());
		assertEquals("password", p.getPassword());
		assertEquals("confirm", p.getConfirmPassword());
	}
	
	
}
