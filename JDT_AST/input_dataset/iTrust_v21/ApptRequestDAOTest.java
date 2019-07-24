package edu.ncsu.csc.itrust.unit.dao.appointment;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.beans.ApptTypeBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptRequestDAO;
import edu.ncsu.csc.itrust.dao.mysql.ApptTypeDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ApptRequestDAOTest extends TestCase {

	private ApptRequestDAO arDAO;
	private ApptRequestDAO EvilRequestDAO;
	private ApptTypeDAO evilDAO;
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.apptRequestConflicts();
		arDAO = TestDAOFactory.getTestInstance().getApptRequestDAO();
		EvilRequestDAO = new ApptRequestDAO(evilFactory);
		evilDAO = new ApptTypeDAO(evilFactory);
	}

	public void testGetApptRequestsFor() throws Exception {
		List<ApptRequestBean> beans = arDAO.getApptRequestsFor(9000000000L);
		assertEquals(1, beans.size());
		assertEquals(2L, beans.get(0).getRequestedAppt().getPatient());
		assertTrue(beans.get(0).isPending());
		
		try{
			EvilRequestDAO.getApptRequestsFor(9000000000L);
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
	}

	public void testAddApptRequest() throws Exception {
		gen.clearAllTables();
		ApptBean bean = new ApptBean();
		bean.setApptType("General Checkup");
		bean.setHcp(9000000000L);
		bean.setPatient(2L);
		bean.setDate(new Timestamp(System.currentTimeMillis()));
		ApptRequestBean req = new ApptRequestBean();
		req.setRequestedAppt(bean);
		arDAO.addApptRequest(req);
		List<ApptRequestBean> reqs = arDAO.getApptRequestsFor(9000000000L);
		assertEquals(1, reqs.size());
		assertEquals(bean.getPatient(), reqs.get(0).getRequestedAppt().getPatient());
		assertTrue(reqs.get(0).isPending());
		assertFalse(reqs.get(0).isAccepted());
		
		try{
			EvilRequestDAO.addApptRequest(new ApptRequestBean());
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
	}

	public void testGetApptRequest() throws Exception {
		List<ApptRequestBean> beans = arDAO.getApptRequestsFor(9000000000L);
		ApptRequestBean b = beans.get(0);
		int id = b.getRequestedAppt().getApptID();
		ApptRequestBean b2 = arDAO.getApptRequest(id);
		assertEquals(b.getRequestedAppt().getApptID(), b2.getRequestedAppt().getApptID());
		
		try{
			@SuppressWarnings("unused")
			List<ApptRequestBean> test = EvilRequestDAO.getApptRequestsFor(9000000000L);
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
	}

	public void testUpdateApptRequest() throws Exception {
		List<ApptRequestBean> beans = arDAO.getApptRequestsFor(9000000000L);
		ApptRequestBean b = beans.get(0);
		int id = b.getRequestedAppt().getApptID();
		b.setPending(false);
		b.setAccepted(true);
		arDAO.updateApptRequest(b);
		ApptRequestBean b2 = arDAO.getApptRequest(id);
		assertEquals(b.getRequestedAppt().getApptID(), b2.getRequestedAppt().getApptID());
		assertFalse(b2.isPending());
		assertTrue(b2.isAccepted());
		
		try{
			EvilRequestDAO.updateApptRequest(b);
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
	}
	
	public void testTypeDBExceptions() throws Exception{
		ApptTypeBean testBean = new ApptTypeBean();
		
		try{
			@SuppressWarnings("unused")
			List<ApptTypeBean> beans = evilDAO.getApptTypes();
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
		
		try{
			@SuppressWarnings("unused")
			ApptTypeBean evilBean = evilDAO.getApptType("Physical");
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
		
		try{
			testBean.setName("Physical");
			testBean.setDuration(90);
			evilDAO.editApptType(testBean);
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
		
		try{
			testBean.setName("Uh-ohhh");
			testBean.setDuration(90);
			evilDAO.addApptType(testBean);
			fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
		}catch(DBException e){
			//Success!
		}
		
		
	};
}
