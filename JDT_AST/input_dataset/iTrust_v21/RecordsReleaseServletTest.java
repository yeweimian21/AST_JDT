package edu.ncsu.csc.itrust.unit.serverutils;

import static org.mockito.Mockito.*;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.action.RequestRecordsReleaseAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.server.RecordsReleaseServlet;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 */
public class RecordsReleaseServletTest extends TestCase{
	
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	RequestDispatcher rdisp;
	
	DAOFactory factory = TestDAOFactory.getTestInstance();
	TestDataGenerator gen;
	LittleDelegatorServlet servlet;
	
	/**
	 * Method to establish mock objects, database tables, and establish mock settings
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		rdisp = mock(RequestDispatcher.class);
		
		servlet = new LittleDelegatorServlet();
	}	
	
	/**
	 * testRecordsReleasePost
	 * @throws Exception
	 */
	public void testRecordsReleasePost() throws Exception {
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("releaseHospital")).thenReturn("9");
		
		when(request.getParameter("recFirstName")).thenReturn("Steve");
		when(request.getParameter("recLastName")).thenReturn("Irwin");
		when(request.getParameter("recPhone")).thenReturn("919-818-1234");
		when(request.getParameter("recEmail")).thenReturn("steve.irwin@gmail.com");
		
		when(request.getParameter("recHospitalName")).thenReturn("Tester Hospital");
		when(request.getParameter("recHospitalAddress")).thenReturn("123 Fake Street");
		when(request.getParameter("releaseJustification")).thenReturn("No comment");
		when(request.getParameter("verifyForm")).thenReturn("true");
		when(request.getParameter("digitalSig")).thenReturn("Caldwell Hudson");
		when(request.getSession().getAttribute("loggedInName")).thenReturn("Caldwell Hudson");
		when(request.getSession().getAttribute("patMID")).thenReturn("102");
		when(request.getParameter("currentMID")).thenReturn("102");
		
		RequestRecordsReleaseAction requestAction = new RequestRecordsReleaseAction(factory, 102L);
		EventLoggingAction loggingAction = new EventLoggingAction(factory);
		when(request.getSession().getAttribute("releaseAction")).thenReturn(requestAction);
		when(request.getSession().getAttribute("loggingAction")).thenReturn(loggingAction);
		when(request.getParameter("isRepresentee")).thenReturn("false");
		when(request.getRequestDispatcher("requestRecordsRelease.jsp")).thenReturn(rdisp);
		when(request.getRequestDispatcher("confirmRecordsReleaseServlet.jsp")).thenReturn(rdisp);
		
		
		
		servlet.doPost(request, response);
		
		assertEquals("9", request.getParameter("releaseHospital"));
		assertEquals("Steve", request.getParameter("recFirstName"));
		assertEquals("Irwin", request.getParameter("recLastName"));
		assertEquals("919-818-1234", request.getParameter("recPhone"));
		assertEquals("steve.irwin@gmail.com", request.getParameter("recEmail"));
		
		assertEquals("Tester Hospital", request.getParameter("recHospitalName"));
		assertEquals("123 Fake Street", request.getParameter("recHospitalAddress"));
		assertEquals("No comment", request.getParameter("releaseJustification"));
		assertEquals("true", request.getParameter("verifyForm"));
		assertEquals("Caldwell Hudson", request.getParameter("digitalSig"));		
	}
	
	/**
	 * testRecordsReleasePostFail
	 */
	public void testRecordsReleasePostFail() throws Exception {
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("releaseHospital")).thenReturn("9");
		
		when(request.getParameter("recFirstName")).thenReturn("Steve");
		when(request.getParameter("recLastName")).thenReturn("Irwin");
		when(request.getParameter("recPhone")).thenReturn("919-818-12345");
		when(request.getParameter("recEmail")).thenReturn("steve.irwin@gmail.com");
		
		when(request.getParameter("recHospitalName")).thenReturn("Tester Hospital");
		when(request.getParameter("recHospitalAddress")).thenReturn("123 Fake Street");
		when(request.getParameter("releaseJustification")).thenReturn("No comment");
		when(request.getParameter("verifyForm")).thenReturn("true");
		when(request.getParameter("digitalSig")).thenReturn("Caldwell Hudson");
		
		RequestRecordsReleaseAction requestAction = new RequestRecordsReleaseAction(factory, 102L);
		when(request.getSession().getAttribute("releaseAction")).thenReturn(requestAction);
		
		when(request.getRequestDispatcher("requestRecordsRelease.jsp")).thenReturn(rdisp);

		servlet.doPost(request, response);
		
	}
	
	/**
	 * Servlet instance of larger servlet class for testing purposes
	 * 	 *
	 */
	private class LittleDelegatorServlet extends RecordsReleaseServlet {
		private static final long serialVersionUID = 1L;		

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
			super.doPost(req, resp);
		}

	}

}
