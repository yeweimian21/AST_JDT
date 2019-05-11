package getrelation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xmlutil.ClassEntity;
import xmlutil.JaxbUtil;
import xmlutil.ListEntity;

public class ClassUtil {
	
	public static String outputId = "4";
	public static String filePath = "src/output/eTOUR/XMLObject3.xml";

	//从Xml文件中读取String类型的Xml
	public static String getXmlString(String filePath){
		StringBuffer stringBuffer = new StringBuffer();
		String line = "";
		String xml = "";
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			xml = stringBuffer.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	//将String类型的Xml转换为Java对象，并返回ClassList
	public static List<ClassEntity> getClassList(String xmlString){
		List<ClassEntity> classList = new ArrayList<>();
		ListEntity listEntity = JaxbUtil.xmlToBean(xmlString, ListEntity.class);
		classList = listEntity.getClassList();
		return classList;
	}
	
	//将xml写入文件
	public static void writeXmlToFile(String xml, String outputFilePath) {
		File outputFile = new File(outputFilePath);
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
	
	//根据className获得classId
	public static long getClassId(List<ClassEntity> classListParam, String className) {
		for(ClassEntity classEntity : classListParam) {
			if(classEntity.getClassName() == null) {
				continue;
			}
			if(classEntity.getClassName().equals(className)) {
				return classEntity.getId();
			}
		}
		return -1;
	}
	
	//读取文件夹下的所有文件(递归方法)
	public static List<String> getFileList(String strPath) {
		ArrayList<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles(); 
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // 判断是文件还是文件夹
                if (files[i].isDirectory()) { 
                	// 获取文件绝对路径
                    filelist.addAll(getFileList(files[i].getAbsolutePath())); 
                } 
                // 判断文件名是否以.txt结尾
                else if (fileName.endsWith("txt")) { 
                    String strFileName = files[i].getAbsolutePath();
//                    System.out.println("---" + strFileName);
                    filelist.add(strFileName);
                } 
                else {
                    continue;
                }
                
            }

        }
        return filelist;
    }
	
	//将所有NodeId文件合并到一个文件
	public static void mergeNodeIdFile(List<String> inputFileList, String outputFilePath) {
		try {
			File outputFile = new File(outputFilePath);
//			FileWriter fileWriter = new FileWriter(outputFile, true);
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			File inputfile;
			FileReader fileReader;
			BufferedReader bufferedReader;
//			StringBuffer stringBuffer = new StringBuffer();
			String line = "";
			for(String file : inputFileList) {
				//每读一个文件
				inputfile = new File(file);
				fileReader = new FileReader(inputfile);
				bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
//					stringBuffer.append(line);
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
				//每写一个文件
//				bufferedWriter.write(stringBuffer.toString());
//				bufferedWriter.write("----------");
//				bufferedWriter.newLine();
				
//				bufferedWriter.write("----------");
//				bufferedWriter.newLine();
				
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//获得类的数量
	public static int getClassAmount() {
		String xmlString = ClassUtil.getXmlString(ClassUtil.filePath);
		List<ClassEntity> classList = ClassUtil.getClassList(xmlString);
		int classAmount = classList.size();
		return classAmount;
	}
	
}
