package edu.ncsu.csc.itrust.unit.serverutils;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createControl;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;
import edu.ncsu.csc.itrust.server.WardCRUDServlet;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class WardCRUDServletTest extends TestCase {
	
	protected WardDAO wardDAO = new WardDAO(DAOFactory.getProductionInstance());
	
	private IMocksControl ctrl;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	@Override
	protected void setUp() throws Exception {
		ctrl = createControl();
		req = ctrl.createMock(HttpServletRequest.class);
		resp = ctrl.createMock(HttpServletResponse.class);
	}

	public void testWardCRUDServletPost() throws Exception, RuntimeException {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("requiredSpecialty")).andReturn("").once();
		expect(req.getParameter("inHospital")).andReturn("1").once();
		resp.sendRedirect("");                                         
		expectLastCall();
		
		ctrl.replay();

		servlet.doPost(req, resp);
	}
	
	public void testWardCRUDServletPostFail() throws Exception, RuntimeException {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("requiredSpecialty")).andReturn("1").once();
		expect(req.getParameter("inHospital")).andReturn("").once();
		resp.sendRedirect("");                                         
		expectLastCall();
		
		ctrl.replay();

		servlet.doPost(req, resp);
	}
	
	public void testWardCRUDServletPut() throws Exception, RuntimeException {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("wardID")).andReturn("1").once();
		expect(req.getParameter("requiredSpecialty")).andReturn("").once();
		expect(req.getParameter("inHospital")).andReturn("1").once();
		resp.sendRedirect("");                                         
		expectLastCall();
		
		ctrl.replay();

		servlet.doPut(req, resp);
	}
	
	public void testWardCRUDServletPutFail() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("wardID")).andReturn("").once();
		expect(req.getParameter("requiredSpecialty")).andReturn("").once();
		expect(req.getParameter("inHospital")).andReturn("1").once();
		resp.sendRedirect("");                                         
		expectLastCall();
		
		ctrl.replay();

		servlet.doPut(req, resp);
	}
	
	public void testWardCRUDServletDelete() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("wardID")).andReturn("1").once();
		resp.sendRedirect("");                                         
		expectLastCall();
		
		ctrl.replay();

		servlet.doDelete(req, resp);
	}
	
	public void testWardCRUDServletDeleteFail() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("wardID")).andReturn("").once();
		resp.sendRedirect("");                                         
		expectLastCall();
		
		ctrl.replay();

		servlet.doDelete(req, resp);
	}
	
	
	private class LittleDelegatorServlet extends WardCRUDServlet {
		private static final long serialVersionUID = -1256537436505857390L;

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			super.doPost(req, resp);
		}
		
		@Override
		protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			super.doPut(req, resp);
		}
		
		@Override
		protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			super.doDelete(req, resp);
		}
		
		public void setUp(){
			wardDAO = new WardDAO(TestDAOFactory.getTestInstance());
		}
	}
}
