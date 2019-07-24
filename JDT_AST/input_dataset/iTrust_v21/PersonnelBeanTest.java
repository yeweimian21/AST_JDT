package edu.ncsu.csc.itrust.unit.bean;

import java.util.List;
import java.util.LinkedList;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;

public class PersonnelBeanTest extends TestCase {
	
	public void testPersonnelBeanSecurity() {
		PersonnelBean p = new PersonnelBean();
		PersonnelBean p1 = new PersonnelBean();
		PersonnelBean p2 = new PersonnelBean();
		
		p.setPassword("password");
		p.setConfirmPassword("confirm");
		p.setSecurityQuestion("Question");
		p.setSecurityAnswer("Answer");
		
		assertEquals("confirm", p.getConfirmPassword());
		assertEquals("password", p.getPassword());
		assertEquals("Question", p.getSecurityQuestion());
		assertEquals("Answer", p.getSecurityAnswer());
		
		p.setFirstName("John");
		p.setLastName("Doe");
		p.setEmail("email@email.com");
		p.setPhone("828-464-3333");
		p.setSpecialty("Special");
		
		assertEquals("John", p.getFirstName());
		assertEquals("Doe", p.getLastName());
		assertEquals("John Doe", p.getFullName());
		assertEquals("email@email.com", p.getEmail());
		assertEquals("828-464-3333", p.getPhone());
		assertEquals("828-464-3333", p.getPhone());
		assertEquals("Special", p.getSpecialty());

		p.setCity("City");
		p.setState("NC");
		p.setZip("28658-2760");
		
		assertEquals("City", p.getCity());
		assertEquals("NC", p.getState());
		assertEquals("28658-2760", p.getZip());
		
		p.setZip("");
		assertEquals("", p.getZip());
		
		p.setMID(000001);
		p1.setMID(000002);
		
		assertEquals(000001, p.getMID());
		
		List<PersonnelBean> list = new LinkedList<PersonnelBean>();
		
		list.add(p);
		list.add(p1);
		assertEquals(0, p.getIndexIn(list));
		assertEquals(1, p1.getIndexIn(list));
		assertEquals(-1, p2.getIndexIn(list));
	}

}
