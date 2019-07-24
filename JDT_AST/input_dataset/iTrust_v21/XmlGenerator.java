package edu.ncsu.csc.itrust;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;


/**
 * XmlGenerator is a class that takes headers and data and converts them into a xml compliant document.
 */
public class XmlGenerator{
	
	/**
	 * generateXml converts the headers and data into a xml file
	 * 
	 * @param headers - Column names
	 * @param Data - Data for columns
	 * @return - Xml document
	 */
	public static Document generateXml(ArrayList<String> headers, ArrayList<ArrayList<String>> Data){
		Document report;
		try{
			//sorced from http://stackoverflow.com/questions/8865099/xml-file-generator-in-java
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
	    //new document
			report = builder.newDocument();
			
	    //head element
	 		Element head = report.createElement("PatientReport");
			report.appendChild(head);
			
			//assumed has document builder and factory
			for (int x = 0; x < Data.size(); x++)//for each top level element
			{
				// makes format <Patient name="blah" age="xx" birthdate=""/> etc...
				Element patient = report.createElement("Patient");
				for (int y = 0; ((y < Data.get(x).size()) && (y < headers.size())); y++)
				{
					patient.setAttribute(parse(headers.get(y)), parse(Data.get(x).get(y)));
				}
				head.appendChild(patient);
			}
			//for each first level element, loop through second level and 
			}
			catch (ParserConfigurationException e){
				//TODO log error
				return null;
			}
		return report;
		
	}
	
	/**
	 * Parses the string for a xml compliant one
	 * @param s - The string to be fixed
	 * @return - The xml compliant string
	 */
	private static String parse(String s){
		return s.replaceAll(" ", "_").replaceAll("#", "NUMBER").replaceAll("'", "");
	}
	
}