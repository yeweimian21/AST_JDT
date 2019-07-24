package edu.ncsu.csc.itrust.unit;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.BeanBuilder;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.unit.testutils.BadBean;
import edu.ncsu.csc.itrust.unit.testutils.OkayBean;

public class BeanBuilderTest extends TestCase {
	public void testEmptyPatientDateOfBirth() throws Exception {
		assertEquals("empty patient bean", new PatientBean().getDateOfBirthStr(),
				new BeanBuilder<PatientBean>().build(new HashMap<String, String>(), new PatientBean())
						.getDateOfBirthStr());
	}

	// just testing the building process - test the bean validation elsewhere!
	public void testPartialPatient() throws Exception {
		PatientBean p = new PatientBean();
		p.setFirstName("Bob");
		p.setDateOfBirthStr("10/10/1950");
		p.setPhone("85");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("firstName", new String[] { "Bob" });
		m.put("dateOfBirthStr", new String[] { "10/10/1950" });
		m.put("phone", new String[] { "85" });
		PatientBean builtBean = new BeanBuilder<PatientBean>().build(m, new PatientBean());
		assertEquals("correctly built patient bean from hashmap", p.getFirstName(), builtBean.getFirstName());
		assertEquals("correctly built patient bean from hashmap", p.getDateOfBirthStr(), builtBean
				.getDateOfBirthStr());
		assertEquals("correctly built patient bean from hashmap", p.getPhone(), builtBean.getPhone());
		assertEquals("correctly built patient bean from hashmap", p.getLastName(), builtBean.getLastName());
	}

	public void testNotOverloaded() throws Exception {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("thing", new String[] { "5" });
			new BeanBuilder<BadBean>().build(map, new BadBean());
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"edu.ncsu.csc.itrust.unit.testutils.BadBean should not have any overloaded methods, like setThing",
					e.getMessage());
		}
	}

	public void testOverloadedConstructor() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("description", new String[] { "test text" });
		DiagnosisBean diag = new BeanBuilder<DiagnosisBean>().build(map, new DiagnosisBean());
		assertEquals("test text", diag.getDescription());
	}
	
	public void testEqualsOkayToBeOverloaded() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("thing", new String[] { "test text" });
		OkayBean ok = new BeanBuilder<OkayBean>().build(map, new OkayBean());
		assertEquals("test text", ok.getThing());
	}
	
	public void testOkayBean1() throws Exception {
		OkayBean ok = new OkayBean();
		assertFalse(ok.equals(""));
		OkayBean ok2 = new OkayBean();
		assertTrue(ok.equals(ok2));
		assertEquals(42, ok.hashCode());
	}
}
