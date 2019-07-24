package edu.ncsu.csc.itrust.unit.charts;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jfree.data.UnknownKeyException;
import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.charts.HealthData;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 */
public class HealthDataTest extends TestCase {
	private HealthData chart;
	TestDataGenerator gen = new TestDataGenerator();
	
	@Override
	protected void setUp() throws Exception {
		chart = new HealthData();
		gen.clearAllTables();
		gen.standardData();
		gen.healthData();
	}
	
	/**
	 * testProduceDataset
	 */
	public void testProduceDataset()
	{
		String codeName = "Height";
		Calendar mytime = Calendar.getInstance();
    	int quarter = (mytime.get(Calendar.MONTH)/3)+1;
    	
		List<HealthRecord> healthRecordBeans = new LinkedList<HealthRecord>();
		HealthRecord hRecord = new HealthRecord();
		hRecord.setHeight(56);
		hRecord.setDateRecorded(new Timestamp(mytime.getTime().getTime()));
		healthRecordBeans.add(hRecord);
		
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		
		try {
			chart.initializeHealthRecords(healthRecordBeans, codeName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			assertEquals(56.0, data.getValue(codeName, "Q" + quarter + " '" + sdf.format(mytime.getTime())));
			assertTrue(chart.hasData());
		} catch (DatasetProduceException e) {
			
			fail();
		}
		
	}
	
	/**
	 * testProduceDataset2
	 */
	public void testProduceDataset2()
	{
		
		chart = new HealthData();
		String codeName = "Weight";
		Calendar mytime = Calendar.getInstance();
    	int quarter = (mytime.get(Calendar.MONTH)/3)+1;
    	
		List<HealthRecord> healthRecordBeans = new LinkedList<HealthRecord>();
		HealthRecord hRecord = new HealthRecord();
		hRecord.setWeight(0);
		hRecord.setDateRecorded(new Timestamp(mytime.getTime().getTime()));
		healthRecordBeans.add(hRecord);
		
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		
		try {
			chart.initializeHealthRecords(healthRecordBeans, codeName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			try {
				data.getValue(codeName, "Q" + quarter + " '" + sdf.format(mytime.getTime()));
			} catch (UnknownKeyException e) {
			
				assertEquals(1, 1);
			
			}
			assertTrue(chart.hasData());
		} catch (DatasetProduceException e) {
			
			fail();
		}
		
	}
	
}
