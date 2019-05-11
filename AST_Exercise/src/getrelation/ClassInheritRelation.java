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

public class ClassInheritRelation {

	private String outputId;
	private String outputDirectory;
	private String outputInheritXmlFile;
	private String outputImplementsXmlFile;
	private String outputInheritTxtFile;
	private String outputImplementsTxtFile;
	
	public ClassInheritRelation() {
		this.outputId = ClassUtil.outputId;
		this.outputDirectory = "E:/Eclipse/AST/AST_Exercise/src/output/eTOUR/";
//		this.outputInheritXmlFile = this.outputDirectory + "InheritRelation/xml/classInherit"+this.outputId+".xml";
//		this.outputInheritTxtFile = this.outputDirectory + "InheritRelation/txt/nodeInherit"+ this.outputId + ".txt";
//		this.outputImplementsXmlFile = this.outputDirectory + "ImplementsRelation/xml/classImplements"+this.outputId+".xml";
//		this.outputImplementsTxtFile = this.outputDirectory + "ImplementsRelation/txt/nodeImplements" + this.outputId + ".txt";
		this.outputInheritXmlFile = this.outputDirectory + "classInherit"+this.outputId+".xml";
		this.outputInheritTxtFile = this.outputDirectory + "nodeInherit"+ this.outputId + ".txt";
		this.outputImplementsXmlFile = this.outputDirectory + "classImplements"+this.outputId+".xml";
		this.outputImplementsTxtFile = this.outputDirectory + "nodeImplements" + this.outputId + ".txt";
	
		
		
	}

	// 获取class之间的继承父类关系，并输出到文件
	public ClassRelationListEntity getClassRelationInherit(List<ClassEntity> classListParam) {
		ClassRelationListEntity classRelationListEntity = new ClassRelationListEntity();
		List<ClassRelationEntity> classRelationList = new ArrayList<>();
		for (ClassEntity classEntity : classListParam) {
			String className = classEntity.getClassName();
			String superClass = classEntity.getSuperClassName();
			if (superClass == null || superClass.equals("")) {
				continue;
			}
			ClassRelationEntity classRelationEntity = new ClassRelationEntity();
			classRelationEntity.setClass1(className);
			classRelationEntity.setClass2(superClass);
			classRelationEntity.setRelationType("Inherit");
			classRelationList.add(classRelationEntity);
		}
		classRelationListEntity.setRelationList(classRelationList);

		// 输出xml到文件
		String xml = JaxbUtil.beanToXml(classRelationListEntity);
		String outputFile = this.outputInheritXmlFile;
		ClassUtil.writeXmlToFile(xml, outputFile);

		return classRelationListEntity;
	}

	// 获取class之间的实现接口关系，并输出到文件
	public ClassRelationListEntity getClassImplRelation(List<ClassEntity> classListParam) {
		ClassRelationListEntity classRelationListEntity = new ClassRelationListEntity();
		List<ClassRelationEntity> classRelationList = new ArrayList<>();
		for (ClassEntity classEntity : classListParam) {
			String class1Name = classEntity.getClassName();
			List<String> superInterfaceList = classEntity.getSuperInterface();

			if (superInterfaceList == null || superInterfaceList.size() == 0) {
				continue;
			}
			for (String superInterface : superInterfaceList) {
				if (superInterface == null || superInterface.equals("")) {
					continue;
				}
				ClassRelationEntity classRelationEntity = new ClassRelationEntity();
				classRelationEntity.setClass1(class1Name);
				classRelationEntity.setClass2(superInterface);
				classRelationEntity.setRelationType("Implements");
				classRelationList.add(classRelationEntity);
			}

		}
		classRelationListEntity.setRelationList(classRelationList);

		// 输出xml到文件
		String xml = JaxbUtil.beanToXml(classRelationListEntity);
		String outputFile = this.outputImplementsXmlFile;
		ClassUtil.writeXmlToFile(xml, outputFile);

		return classRelationListEntity;
	}

	// 获取class之间的关系(nodeId)
	public void getNodeRelationInherit(List<ClassEntity> classListParam,
			ClassRelationListEntity classInheritListEntity, String type) {
		long id1;
		long id2;
		List<ClassRelationEntity> classRelationList = classInheritListEntity.getRelationList();

		String outputFile = "";
		if(type.equals("Inherit")) {
			outputFile = this.outputInheritTxtFile;
		}
		if(type.equals("Implements")) {
			outputFile = this.outputImplementsTxtFile;
		}
		
		File file = new File(outputFile);
		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (ClassRelationEntity classRelationEntity : classRelationList) {
				
				//如果在project中没有找到相应的类，就不用记录
				String class2 = classRelationEntity.getClass2();
				id2 = ClassUtil.getClassId(classListParam, class2);
				if(id2 == -1) {
					continue;
				}
				
				String class1 = classRelationEntity.getClass1();
				id1 = ClassUtil.getClassId(classListParam, class1);

				bufferedWriter.write(String.valueOf(id1) + " " + String.valueOf(id2));
				bufferedWriter.newLine();

			}
			bufferedWriter.flush();
			bufferedWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 获得有边的节点(类)及其类型,只选择具有边的节点，并输出到文件
	public void getHaveEdgeNodeAndType(List<ClassEntity> classList) {
		int[] nodeList = new int[classList.size()];
		for (int i = 0; i < nodeList.length; i++) {
			nodeList[i] = -1;
		}
		try {
			String inputFilePath = "E:/Eclipse/AST/AST_Exercise/src/output/FieldRelation/nodeField1.txt";
			File inputFile = new File(inputFilePath);
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				String[] pair = line.split(" ");
				int class1Id = Integer.parseInt(pair[0]);
				int class2Id = Integer.parseInt(pair[1]);
				if (nodeList[class1Id] == -1) {
					nodeList[class1Id] = 1;
				}
				if (nodeList[class2Id] == -1) {
					nodeList[class2Id] = 1;
				}
			}
			bufferedReader.close();

			String outputFile = "E:/Eclipse/AST/AST_Exercise/src/output/haveEdgeNodeType"+this.outputId+".txt";
			File file = new File(outputFile);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i = 0; i<nodeList.length; i++) {
				if(nodeList[i] == 1) {
					bufferedWriter.write(i+" "+"1");
					bufferedWriter.newLine();
				}
			}
			

			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 获得所有的节点(类)及其类型,不考虑该节点是否存在边，并输出到文件
	public void getNodeAndType(List<ClassEntity> classList) {
		long classId;
		String outputFile = "E:/Eclipse/AST/AST_Exercise/src/output/nodeType"+this.outputId+".txt";
		File file = new File(outputFile);
		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (ClassEntity classEntity : classList) {
				classId = classEntity.getId();
				bufferedWriter.write(String.valueOf(classId) + " " + "1");
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	public static void main(String[] args) {

		ClassInheritRelation classRelation = new ClassInheritRelation();
		String xmlString = ClassUtil.getXmlString(ClassUtil.filePath);
		List<ClassEntity> classList = ClassUtil.getClassList(xmlString);
		// 输出到xml文件并返回对象
		//继承父类
		 ClassRelationListEntity classRelationListEntity1 =
		 classRelation.getClassRelationInherit(classList);

		//实现接口
		 ClassRelationListEntity classRelationListEntity2 =
		 classRelation.getClassImplRelation(classList);

		// 输出到classId文件
		 classRelation.getNodeRelationInherit(classList, classRelationListEntity1, "Inherit");
		 classRelation.getNodeRelationInherit(classList, classRelationListEntity2, "Implements");

		// 获得节点和类型，并输出到文件
		//classRelation.getNodeAndType(classList);
		//classRelation.getHaveEdgeNodeAndType(classList);

		// System.out.println("classInheritCount:"+classInheritListEntity.getInheritList().size());

		/*
		 * String xml = JaxbUtil.beanToXml(classInheritListEntity);
		 * System.out.println("####"); System.out.println(xml);
		 * System.out.println("####");
		 * 
		 * System.out.println("classCount:"+classList.size());
		 * System.out.println("classList:"+classList);
		 */

	}

}
