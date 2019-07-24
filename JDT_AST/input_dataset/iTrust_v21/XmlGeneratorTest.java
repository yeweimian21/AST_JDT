package edu.ncsu.csc.itrust.unit;

import edu.ncsu.csc.itrust.XmlGenerator;
import junit.framework.TestCase;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XmlGeneratorTest
 */
public class XmlGeneratorTest extends TestCase {
	
	/**
	 * testXmlEmpty
	 */
	public void testXmlEmpty() {
		ArrayList<ArrayList<String>> testdata = new ArrayList<ArrayList<String>>();
		ArrayList<String> headers = new ArrayList<String>();
		
		Document emptyDoc = XmlGenerator.generateXml(headers, testdata);
		
		NodeList list = emptyDoc.getChildNodes();
		assertEquals(list.getLength(), 1);
		assertEquals(list.item(0).getNodeName(), "PatientReport");
	}
	
	/**
	 * testtwoPatient
	 */
	public void testtwoPatient(){
		ArrayList<ArrayList<String>> testdata = new ArrayList<ArrayList<String>>();
		ArrayList<String> headers = new ArrayList<String>();
		//set up test headers
		headers.add("Name");
		headers.add("Age");
		headers.add("sex");
		
		
		// set up test patient
		ArrayList<String> patient1 = new ArrayList<String>();		
		patient1.add("bob joe"); // test space to _
		patient1.add("23");
		patient1.add("Male");
		testdata.add(patient1);
		
		
		ArrayList<String> patient2 = new ArrayList<String>();		
		patient2.add("william"); // test space to _
		patient2.add("21");
		patient2.add("Male");
		testdata.add(patient2);
		Document two = XmlGenerator.generateXml(headers, testdata);
		
		Node head = two.getFirstChild(); // document
		System.out.println(head.getNodeName());
		NodeList list = head.getChildNodes();
		System.out.println(list.getLength() + " " + list.item(0).getNodeName() + " " );
		
		assertEquals(2, list.getLength());
		assertEquals("Patient", list.item(0).getNodeName());
		assertEquals("PatientReport", head.getNodeName());
		
	}	
	
}