package edu.ncsu.csc.itrust.unit.dao.CDCMetrics;


import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.CDCStatsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CDCHeadCircStatsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

@SuppressWarnings("unused")
public class CDCHeadCircStatsDAOTest  extends TestCase{
		private DAOFactory factory = TestDAOFactory.getTestInstance();
		
		private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
		
		
		private CDCHeadCircStatsDAO evilDAO = new CDCHeadCircStatsDAO(evilFactory);
		
		
		private CDCHeadCircStatsDAO cdcDAO = new CDCHeadCircStatsDAO(factory);
		
		private CDCStatsBean cdcBean = new CDCStatsBean();

		@Override
		protected void setUp() throws Exception {
			TestDataGenerator gen = new TestDataGenerator();
			gen.clearAllTables();
			gen.cdcStatistics();
			
		}
		
		public void testStoreAndGetStats() throws DBException {
			cdcBean.setSex(1);
			cdcBean.setAge(0);
			cdcBean.setL(3.123);
			cdcBean.setM(4.123);
			cdcBean.setS(1.123);
			
			//if these fail then the gets do not work, and neither do the sets! :D
			assertEquals(cdcBean.getSex(), 1);
			assertEquals(cdcBean.getAge(), 0.0, .01);
			assertEquals(cdcBean.getL(), 3.123);
			assertEquals(cdcBean.getM(), 4.123);
			assertEquals(cdcBean.getS(), 1.123);
			
			try{
				cdcDAO.storeStats(cdcBean);
			}catch(Exception e){
				fail("Exception thrown when storeStats called in CDCHeadCircStatsDAOTEST: line 48.");
			}
			
			try{
				cdcDAO.getCDCStats(cdcBean.getSex(), cdcBean.getAge());
			}catch(Exception e){
				fail("Exception thrown when getCDCStats called in CDCHeadCircStatsDAOTEST: line 54.");
			}
			
		}
		
		public void testDBException() throws DBException {
			cdcBean.setSex(1);
			cdcBean.setAge(0);
			cdcBean.setL(3.123);
			cdcBean.setM(4.123);
			cdcBean.setS(1.123);
			
			
			try{
				evilDAO.storeStats(cdcBean);
				fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
			}catch(DBException e){
				//Success!
			}
			
			try{
				evilDAO.getCDCStats(cdcBean.getSex(), cdcBean.getAge());
				fail("WHAT THE HELL THIS EVIL FACTORY DOESN'T WORK");
			}catch(DBException e){
				//SUCCESS!
			}
			
		}
	}