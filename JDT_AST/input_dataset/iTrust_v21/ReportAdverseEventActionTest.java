package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.ReportAdverseEventAction;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AdverseEventDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.Email;

/**
 * ReportAdverseEventActionTest
 */
public class ReportAdverseEventActionTest extends TestCase {

	private DAOFactory factory;
	private AdverseEventDAO adverseDAO;
	private ReportAdverseEventAction advAction;
	private ReportAdverseEventAction advMultiAction;
	private TestDataGenerator gen;
	private long pateientId;
	private long hcpId;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.pateientId = 2L;
		this.hcpId = 9000000000L;
		this.factory = TestDAOFactory.getTestInstance();
		this.adverseDAO = new AdverseEventDAO(this.factory);
		this.advAction = new ReportAdverseEventAction(String.valueOf(this.hcpId), this.factory, this.pateientId);
		this.advMultiAction = new ReportAdverseEventAction(this.factory, this.pateientId);
	}

	/**
	 * testSendOneMail
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void testSendOneMail()throws ITrustException, FormValidationException{
		AdverseEventBean aBean = new AdverseEventBean();
		aBean.setMID("2");
		aBean.setDrug("Prioglitazone");
		aBean.setCode("647641512");
		aBean.setDescription("IT'S KILLING ME!");
		
		Email email = advAction.sendMail(aBean);
		assertEquals(" Patient: Andy Programmer (MID 2) Has Reported the following adverse event Drug: Prioglitazone(647641512) Description: IT'S KILLING ME!", email.getBody());
	}
	
	/**
	 * testSendTwoMailh
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void testSendTwoMail() throws ITrustException, FormValidationException{
		List<AdverseEventBean> BeanList = new ArrayList<AdverseEventBean>();
		
		AdverseEventBean aBean = new AdverseEventBean();
		AdverseEventBean aBeanTwo = new AdverseEventBean();
		aBean.setMID("2");
		aBean.setDrug("Prioglitazone");
		aBean.setCode("647641512");
		aBean.setDescription("I'M DYING!");
		aBean.setPrescriber("9000000000");
		aBeanTwo.setMID("2");
		aBeanTwo.setDrug("Prioglitazone");
		aBeanTwo.setCode("647641512");
		aBeanTwo.setDescription("I'M DEAD!");
		aBeanTwo.setPrescriber("9000000000");
		BeanList.add(aBean);
		BeanList.add(aBeanTwo);
		Email email = advMultiAction.sendMails(BeanList);
		assertEquals(" Patient: Andy Programmer (MID 2) Has Reported the following adverse event(s) Drug: Prioglitazone (647641512) Description: I'M DYING! Drug: Prioglitazone (647641512) Description: I'M DEAD!", email.getBody());
	}
	
	/**
	 * testEmailAvalanche
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void testEmailAvalanche () throws ITrustException, FormValidationException{
		List<AdverseEventBean> BeanList = new ArrayList<AdverseEventBean>();
		String body = " Patient: Andy Programmer (MID 2) Has Reported the following adverse event(s)";
		
		for(int i = 0; i < 32; i++){
			AdverseEventBean bigBean = new AdverseEventBean();
			bigBean.setMID("2");
			bigBean.setDrug("Prioglitazone");
			bigBean.setCode("647641512");
			bigBean.setDescription("<dead sounds>");
			bigBean.setPrescriber("9000000000");
			body = body + " Drug: Prioglitazone (647641512) Description: <dead sounds>";
			BeanList.add(bigBean);
		}
		
		Email email = advMultiAction.sendMails(BeanList);
		assertEquals(body, email.getBody());
	}
	
	/**
	 * testBadEvent
	 */
	public void testBadEvent(){
		AdverseEventBean badBean = new AdverseEventBean();
		badBean.setMID("Two");
		badBean.setDrug("Rat Posion");
		badBean.setCode("999999999");
		badBean.setDescription("I'm not a rat!");
		badBean.setPrescriber("9000000000");
		try{
			advAction.addReport(badBean);
		}
		catch(Exception e)
		{
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		
	}
	
	/**
	 * testWorseEvent
	 * @throws ITrustException
	 * @throws FormValidationException
	 * @throws SQLException
	 */
	public void testWorseEvent() throws ITrustException, FormValidationException, SQLException{
		AdverseEventBean badBean = new AdverseEventBean();
		badBean.setMID("2");
		badBean.setDrug("Rat Posion");
		badBean.setCode("999999999");
		badBean.setDescription(">");
		badBean.setPrescriber("9000000000");
		String results = advAction.addReport(badBean);
		assertEquals("This form has not been validated correctly. The following field are not properly filled in: [comment: Up to 2000 alphanumeric characters and .-',!;:()?]", results);
		
	}
	
	/**
	 * testAddReport
	 * @throws ITrustException
	 * @throws SQLException
	 */
	public void testAddReport() throws ITrustException, SQLException {
		AdverseEventBean aBean = new AdverseEventBean();
		
		aBean.setMID("2");
		aBean.setDrug("Prioglitazone");
		aBean.setDescription("It Burns!");
		
		try {
			advAction.addReport(aBean);
		} catch (FormValidationException e) {
			//TODO
		}
		
		List<AdverseEventBean> adList = adverseDAO.getReportsFor(pateientId);
		
		assertEquals(1, adList.size());
		AdverseEventBean adBeanDB = adList.get(0);
		assertEquals("It Burns!", adBeanDB.getDescription());
	}
	
}
