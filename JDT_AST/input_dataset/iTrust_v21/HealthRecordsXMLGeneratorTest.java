package edu.ncsu.csc.itrust.unit.serverutils;

import static org.mockito.Mockito.*;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.ncsu.csc.itrust.action.ViewRecordsReleaseAction;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.RecordsReleaseDAO;
import edu.ncsu.csc.itrust.server.HealthRecordsXMLGeneratorServlet;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class HealthRecordsXMLGeneratorTest extends TestCase{
	
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	PrintWriter out;
	
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
		out = mock(PrintWriter.class);
		
		servlet = new LittleDelegatorServlet();
	}	
	
	public void testRecordsReleaseGet() throws Exception {
		RecordsReleaseDAO rrDAO = new RecordsReleaseDAO(factory);
		List<RecordsReleaseBean> list = rrDAO.getAllRecordsReleasesByPid(102L);
		ViewRecordsReleaseAction viewAction = new ViewRecordsReleaseAction(factory, 9000000000L);
		
		when(request.getSession()).thenReturn(session);
//		when(response.getContentType()).thenReturn("text/xml");
		when(response.getWriter()).thenReturn(out);
		
		when(request.getParameter("index")).thenReturn(String.valueOf("0"));
		when(request.getSession().getAttribute("recRequests")).thenReturn(list);
		when(request.getSession().getAttribute("viewAction")).thenReturn(viewAction);
		
		servlet.doGet(request, response);
//		assertTrue(response.getContentType().equals("text/xml"));
	}
	
	/**
	 * Servlet instance of larger servlet class for testing purposes
	 */
	private class LittleDelegatorServlet extends HealthRecordsXMLGeneratorServlet {
		private static final long serialVersionUID = 1L;		

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
			super.doGet(req, resp);
		}

	}

}
