package edu.ncsu.csc.itrust.unit.serverutils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.server.PatientSearchServlet;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PateintSearchServletTest {

	private LittleDelegatorServlet subject;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private TestDataGenerator gen = new TestDataGenerator();
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientDeactivate();
		subject = new LittleDelegatorServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
	}

	@Test
	public void testDoGetAuditHttpServletRequestHttpServletResponse() {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		when(request.getParameter("q")).thenReturn("Julia Roberts");
		when(request.getParameter("isAudit")).thenReturn("true");
		when(request.getParameter("forward")).thenReturn("hcp/auditPage.jsp");
		try {
			when(response.getWriter()).thenReturn(p);
		} catch (IOException e1) {
		}
		try {
			subject.testDoGet(request, response);
		} catch (Exception e){
			fail();
		}
		assertTrue(s.toString().contains("Julia"));
		assertTrue(s.toString().contains("Roberts"));
		assertTrue(s.toString().contains("Action"));
	}
	
	@Test
	public void testEmptyAuditSearch(){
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		when(request.getParameter("q")).thenReturn("");
		when(request.getParameter("isAudit")).thenReturn("true");
		when(request.getParameter("allowDeactivated")).thenReturn("checked");
		when(request.getParameter("forward")).thenReturn("hcp/auditPage.jsp");
		try {
			when(response.getWriter()).thenReturn(p);
		} catch (IOException e1) {
		}
		try {
			subject.testDoGet(request, response);
		} catch (Exception e){
			fail();
		}
		assertTrue(s.toString().contains("Fake"));
		assertTrue(s.toString().contains("Baby"));
		
	}
	
	@Test
	public void testNonAuditSearch(){
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		when(request.getParameter("q")).thenReturn("Julia Roberts");
		when(request.getParameter("isAudit")).thenReturn("false");
		when(request.getParameter("allowDeactivated")).thenReturn(null);
		when(request.getParameter("forward")).thenReturn("hcp-uap/editPatient.jsp");
		try {
			when(response.getWriter()).thenReturn(p);
		} catch (IOException e1) {
		}
		try {
			subject.testDoGet(request, response);
		} catch (Exception e){
			fail();
		}
		assertTrue(s.toString().contains("Julia"));
		assertTrue(s.toString().contains("Roberts"));
		assertTrue(!s.toString().contains("Action"));
		
	}
	
	private class LittleDelegatorServlet extends PatientSearchServlet{
		
		private static final long serialVersionUID = 1L;

		public LittleDelegatorServlet(){
			super(TestDAOFactory.getTestInstance());
		}
		
		public void testDoGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
			super.doGet(req, resp);
		}
	}
}
