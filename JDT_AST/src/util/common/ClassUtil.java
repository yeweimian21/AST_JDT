package util.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import util.xml.JaxbUtil;
import xmlentity.ClassEntity;

public class ClassUtil {

	public static String projectName;
	public static String inputProjectPath;
	public static String codeFileSuffix;
	public static String outputResultPath;
	public static String classXmlDirectory;

	public static String[] classIdArray;

//	setup the parameters of the tool
	public static void setup(String project){
		projectName = project;
		codeFileSuffix = "java";
		inputProjectPath = "input_dataset/" + projectName + "/";
		outputResultPath = "output_result/" + projectName + "/";
		classXmlDirectory = outputResultPath + "/class_xml/";
		createDirectory(classXmlDirectory);
	}

	// create the directory if the directory don't exist
	public static void createDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists()){
			directory.mkdirs();
		}
	}

//	get the classIdArray
	public static String[] getClassIdArray(){
		if(classIdArray == null){
			ArrayList<File> fileList = getFileList(classXmlDirectory, "xml");
			classIdArray = new String[1000];
			for(File classXmlFile : fileList){
				String classXml = ClassUtil.getXmlString(classXmlFile);
				ClassEntity classEntity = JaxbUtil.xmlToBean(classXml, ClassEntity.class);
				String className = classEntity.getClassName();
				if(className != null ){
					int classId = classEntity.getId();
					classIdArray[classId] = className;
				}
			}
			return classIdArray;
		}
		else {
			return classIdArray;
		}
	}

//	get the String xml from xml file
	public static String getXmlString(File file){
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		String xml = "";
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			xml = stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

//	write String xml to file
	public static void writeXmlToFile(String xml, File outputFile) {
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(xml);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	get the main name of the file
	public static String getMainFileName(File file){
		String fileName = file.getName();
		String mainFileName = fileName.substring(0, fileName.lastIndexOf("."));
		return mainFileName;
	}

//	get the classId by className
	public static long getClassIdByClassName(String classNameParam){
		classIdArray = getClassIdArray();
		for(int i = 0; i < classIdArray.length; i++){
			String className = classIdArray[i];
			if(className != null && className.equals(classNameParam)){
				long classId = i;
				return classId;
			}
		}
//		if not found the class
		return -1;
	}

	//	read all files with specified file type in the project (recursive method)
	public static ArrayList<File> getFileList(String strPath, String fileType) {
		ArrayList<File> fileList = new ArrayList<>();
		File dir = new File(strPath);
//		put all files of the directory into array
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
//				judge whether it is a directory
				if (files[i].isDirectory()) {
//					get the absolute path of file
					fileList.addAll(getFileList(files[i].getPath(), fileType));
				}
//				get the file of the specified type
				else if (fileName.endsWith(fileType)) {
					fileList.add(files[i]);
				}
			}
		}
		return fileList;
	}

}
