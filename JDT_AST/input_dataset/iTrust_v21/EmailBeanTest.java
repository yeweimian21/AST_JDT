package edu.ncsu.csc.itrust.unit.bean;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.Email;

public class EmailBeanTest extends TestCase {
	
	private final Date date = new Date(100000L);
	
	public void testMakeStr() throws Exception {
		Email email = new Email();
		email.setToList(Arrays.asList("first", "second"));
		assertEquals("first,second", email.getToListStr());
	}

	public void testEquals() throws Exception {
		Email email1 = getTestEmail();
		Email email2 = getTestEmail();
		assertEquals(email1, email2);
		assertTrue(email1.equals(email2));
		email1.setToList(Arrays.asList("first", "second", "thrd"));
		assertFalse(email1.equals(email2));
		email1.setToList(Arrays.asList("first", "second"));
		assertFalse(email1.equals(email2));
		email2.setSubject("");
		assertFalse(email1.equals(email2));
	}
	
	public void testToList() throws Exception {
		Email email = getTestEmail();
		assertEquals(3, email.getToList().size());
	}
	
	public void testAttributes() throws Exception {
		Email email = getTestEmail();
		assertEquals("this is the body", email.getBody());
		assertEquals("from address", email.getFrom());
		assertEquals("this is the subject", email.getSubject());
		assertEquals(100000L, email.getTimeAdded().getTime());
	}
	
	public void testGetHashCode() throws Exception {
		Email email = getTestEmail();
		assertEquals(42, email.hashCode());
	}

	private Email getTestEmail() {
		Email email = new Email();
		email.setBody("this is the body");
		email.setFrom("from address");
		email.setSubject("this is the subject");
		email.setToList(Arrays.asList("first", "second", "third"));
		email.setTimeAdded(new Timestamp(date.getTime()));
		return email;
	}	
	
}
