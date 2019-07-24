package edu.ncsu.csc.itrust;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

import edu.ncsu.csc.itrust.exception.CSVFormatException;
import edu.ncsu.csc.itrust.exception.ErrorList;

/**
 * Provides a generic CSV parsing framework.
 * Implemented for patient file importing.
 */
@SuppressWarnings("unused")
public class CSVParser {
	/**
	 * Holds the header fields from the top of the CSV file
	 */
	private ArrayList<String> CSVHeader=new ArrayList<String>();
	/**
	 * Holds the fields and records from the CSV file
	 */
	private ArrayList<ArrayList<String>> CSVData=new ArrayList<ArrayList<String>>();
	/**
	 * Holds a list of errors accumulated while parsing the CSV file.
	 */
	private ErrorList errors=new ErrorList();
	
	/**
	 * Constructor taking an InputStream
	 * 
	 * @param csvstream csvstream
	 * @throws CSVFormatException
	 */
	public CSVParser(InputStream csvstream) throws CSVFormatException{
		Scanner CSVScanner = null;
		try {
			//First try at UTF-8
			CSVScanner = new Scanner(new InputStreamReader(csvstream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			try {
				//Try the default
				CSVScanner = new Scanner(new InputStreamReader(csvstream, Charset.defaultCharset().displayName()));
			} catch (UnsupportedEncodingException e1) {
				throw new CSVFormatException("Encoding errors!");
			}
		}
		parseCSV(CSVScanner);
	}
	
	/**
	 * Constructor taking an already-prepared Scanner
	 * (For testing purposes)
	 * 
	 * @param CSVScanner CSVScanner
	 * @throws CSVFormatException
	 */
	public CSVParser(Scanner CSVScanner) throws CSVFormatException{
		parseCSV(CSVScanner);
	}
	
	/**
	 * Returns the ArrayList of Strings containing the CSV header fields
	 * 
	 * @return ArrayList of Strings containing CSV header fields
	 */
	public ArrayList<String> getHeader(){
		return CSVHeader;
	}
	
	/**
	 * Returns the ArrayList of ArrayLists of Strings containing the CSV data fields
	 * 
	 * @return ArrayList of ArrayLists Strings containing CSV data fields
	 */
	public ArrayList<ArrayList<String>> getData(){
		return CSVData;
	}
	
	/**
	 * Returns the ErrorList of errors accumulated while parsing CSV
	 * 
	 * @return ErrorList of errors accumulated while parsing CSV
	 */
	public ErrorList getErrors(){
		return errors;
	}
	
	/**
	 * Parses the CSV file line-by-line.
	 * 
	 * @param CSVScanner A scanner to a CSV stream.
	 * @throws CSVFormatException
	 */
	private void parseCSV(Scanner CSVScanner) throws CSVFormatException{
		String currentLine;
		ArrayList<String> parsedLine=null;
		//The number of fields (columns) in the CSV file as determined by the number of headers
		int numFields=0;
		//The current line number being processed (Used to report the line number of errors)
		int currentLineNumber=1;
		
		//Attempt to read the first line (the header) from the file
		if(CSVScanner.hasNextLine()){
			currentLine=CSVScanner.nextLine();
			CSVHeader=parseCSVLine(currentLine);
			numFields=CSVHeader.size();
		//If it does not exist (or if the file isn't a text file at all), the entire process fails
		}else{
			throw new CSVFormatException("File is not valid CSV file.");
		}
		
		//Read the file line-by-line and call the line parser for each line
		while(CSVScanner.hasNextLine()){
			currentLineNumber++;
			currentLine=CSVScanner.nextLine();
			try{
				parsedLine=parseCSVLine(currentLine);
				//If the line doesn't have the right number of fields, it is ignored
				if(parsedLine.size()==numFields){
					CSVData.add(parsedLine);
				}else{
					errors.addIfNotNull("Field number mismatch on line "+currentLineNumber);
				}
			//If the line is otherwise invalid, it is also ignored
			}catch(CSVFormatException e){
				errors.addIfNotNull(e.getMessage()+" on line "+currentLineNumber);
			}
		}
	}
	
	/**
	 * Parses the passed line character-by-character
	 * 
	 * @param line Line from the CSV file to parse
	 * @return ArrayList of Strings, each containing the data from one field
	 * @throws CSVFormatException
	 */
	private ArrayList<String> parseCSVLine(String line) throws CSVFormatException{
		//Contains the fields from each line parsed
		ArrayList<String> aLine=new ArrayList<String>();
		//Contains the data from the current field being read
		String currentField="";
		//Contains the status of whether or not the parser is inside a quoted area
		//Used to handle commas and other special characters within the field.
		boolean insideQuotes=false;
		
		//Read the line character-by-character
		for(int i=0; i<line.length(); i++){
			//Comma denotes the end of the current field unless it is quoted
			if(line.charAt(i)==',' && !insideQuotes){
				aLine.add(currentField);
				currentField="";
			//If the field is not ending
			}else{
				//If the character is a ", ignore it and flip the quote status
				if(line.charAt(i)=='"'){
					insideQuotes=!insideQuotes;
				//Otherwise, add the character to the string
				}else{
					currentField=currentField+line.substring(i, i+1);
				}
			}
		}
		//If the line parser ends while still inside a quoted section, the input line was invalid
		if(insideQuotes){
			throw new CSVFormatException("Line ended while inside quotes");
		}
		//Grab text from last field too, since the last field does not end with a comma
		aLine.add(currentField);
		
		return aLine;
	}

}
