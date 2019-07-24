package edu.ncsu.csc.itrust.unit.dao.NormalBean;


import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.NormalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.NormalDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

@SuppressWarnings("unused")
public class NormalDAOTest extends TestCase{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	
	
	private NormalDAO evilDAO = new NormalDAO(evilFactory);
	private NormalDAO normalDAO = new NormalDAO(factory);
	private NormalBean normalBean = new NormalBean();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.cdcStatistics();
		
	}
	
	/**
	 * testNormal
	 * @throws DBException
	 */
	public void testNormal() throws DBException {
		normalBean.set_00(0.0);
		normalBean.set_01(1.0);
		normalBean.set_02(2.0);
		normalBean.set_03(3.0);
		normalBean.set_04(4.0);
		normalBean.set_05(5.0);
		normalBean.set_06(6.0);
		normalBean.set_07(7.0);
		normalBean.set_08(8.0);
		normalBean.set_09(9.0);
		normalBean.setZ(-3.9);
		
		
		//this means our getters or setters are failing if they fail, which is silly if they are.
		assertEquals(normalBean.get_00(), 0.0, .01);
		assertEquals(normalBean.get_01(), 1.0, .01);
		assertEquals(normalBean.get_02(), 2.0, .01);
		assertEquals(normalBean.get_03(), 3.0, .01);
		assertEquals(normalBean.get_04(), 4.0, .01);
		assertEquals(normalBean.get_05(), 5.0, .01);
		assertEquals(normalBean.get_06(), 6.0, .01);
		assertEquals(normalBean.get_07(), 7.0, .01);
		assertEquals(normalBean.get_08(), 8.0, .01);
		assertEquals(normalBean.get_09(), 9.0, .01);
		assertEquals(normalBean.getZ(), -3.9, .01);
		
		try{
			normalDAO.getNormal(normalBean.getZ());
		}catch(Exception e){
			fail("Exception thrown when storeStats called in NormalDAOTEST: line 61.");
		}
		
		
		
	}
	
	/**
	 * testevilNormal
	 * @throws DBException
	 */
	public void testevilNormal() throws DBException {
		normalBean.set_00(0.0);
		normalBean.set_01(1.0);
		normalBean.set_02(2.0);
		normalBean.set_03(3.0);
		normalBean.set_04(4.0);
		normalBean.set_05(5.0);
		normalBean.set_06(6.0);
		normalBean.set_07(7.0);
		normalBean.set_08(8.0);
		normalBean.setZ(-3.9);
		
		
		//this means our getters or setters are failing if they fail, which is silly if they are.
		assertEquals(normalBean.get_00(), 0.0, .01);
		assertEquals(normalBean.get_01(), 1.0, .01);
		assertEquals(normalBean.get_02(), 2.0, .01);
		assertEquals(normalBean.get_03(), 3.0, .01);
		assertEquals(normalBean.get_04(), 4.0, .01);
		assertEquals(normalBean.get_05(), 5.0, .01);
		assertEquals(normalBean.get_06(), 6.0, .01);
		assertEquals(normalBean.get_07(), 7.0, .01);
		assertEquals(normalBean.get_08(), 8.0, .01);
		assertEquals(normalBean.getZ(), -3.9, .01);
		
		try{
			evilDAO.getNormal(normalBean.getZ());
			fail("Evil Factory is broken");
		}catch(Exception e){
			//WOOOOOOOO
		}
		
		
		
	}
	

}
