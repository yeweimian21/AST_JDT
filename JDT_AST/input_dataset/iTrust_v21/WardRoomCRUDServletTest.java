package edu.ncsu.csc.itrust.unit.serverutils;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createControl;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.TestCase;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;
import edu.ncsu.csc.itrust.server.WardRoomCRUDServlet;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * 
 * 
 */
public class WardRoomCRUDServletTest extends TestCase {

	private IMocksControl ctrl;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	@Override
	protected void setUp() throws Exception {
		ctrl = createControl();
		req = ctrl.createMock(HttpServletRequest.class);
		resp = ctrl.createMock(HttpServletResponse.class);
	}

	public void testWardRoomCRUDServletPost() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("inWard")).andReturn("1").once();
		expect(req.getParameter("roomName")).andReturn("").once();

		expect(req.getParameter("status")).andReturn("clean").once();
		expect(req.getParameter("occupiedBy")).andReturn("1").once();


		resp.sendRedirect("");
		expectLastCall();
		
		ctrl.replay();

		servlet.doPost(req, resp);
	}
	
	public void testWardRoomCRUDServletPut() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("inWard")).andReturn("1").once();
		expect(req.getParameter("roomName")).andReturn("").once();
		expect(req.getParameter("status")).andReturn("clean").once();
		expect(req.getParameter("occupiedBy")).andReturn("1").once();


		resp.sendRedirect("");
		expectLastCall();
		
		ctrl.replay();

		servlet.doPut(req, resp);
	}
	
	public void testWardRoomCRUDServletPutFail() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("inWard")).andReturn("").once();
		expect(req.getParameter("roomName")).andReturn("").once();
		expect(req.getParameter("status")).andReturn("clean").once();
		expect(req.getParameter("occupiedBy")).andReturn("1").once();


		resp.sendRedirect("");
		expectLastCall();
		
		ctrl.replay();

		servlet.doPut(req, resp);
	}
	
	public void testWardRoomCRUDServletDelete() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("roomID")).andReturn("1").once();
		resp.sendRedirect("");
		expectLastCall();
		
		ctrl.replay();

		servlet.doDelete(req, resp);
	}
	
	public void testWardRoomCRUDServletDeleteFail() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("roomID")).andReturn("").once();
		resp.sendRedirect("");
		expectLastCall();
		
		ctrl.replay();

		servlet.doDelete(req, resp);
	}
	
	public void testWardRoomCRUDServletFail() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setUp();
		expect(req.getParameter("inWard")).andReturn("").once();
		expect(req.getParameter("roomName")).andReturn("").once();
		resp.sendRedirect("");
		expectLastCall();
		
		ctrl.replay();

		servlet.doPost(req, resp);
	}
	
	private class LittleDelegatorServlet extends WardRoomCRUDServlet {
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
