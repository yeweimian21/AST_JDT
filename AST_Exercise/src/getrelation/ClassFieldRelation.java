package getrelation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xmlutil.ClassEntity;
import xmlutil.FieldEntity;
import xmlutil.JaxbUtil;

public class ClassFieldRelation {
	
	private String outputId;
	private String outputDirectory;
	private String outputFieldXmlFile;
	private String outputFieldTxtFile;
	
	public ClassFieldRelation() {
		this.outputId = ClassUtil.outputId;
		this.outputDirectory = "E:/Eclipse/AST/AST_Exercise/src/output/eTOUR/";
//		this.outputFieldXmlFile = this.outputDirectory + "xml/classField"+ this.outputId +".xml";
//		this.outputFieldTxtFile = this.outputDirectory + "txt/nodeField"+this.outputId+".txt";
		
		this.outputFieldXmlFile = this.outputDirectory + "classField"+ this.outputId +".xml";
		this.outputFieldTxtFile = this.outputDirectory + "nodeField"+this.outputId+".txt";
	
		
	}
	
	//获得Class之间属性的关系，并输出到文件
	public ClassRelationListEntity getClassFieldRelation(List<ClassEntity> classListParam) {
		ClassRelationListEntity classRelationListEntity = new ClassRelationListEntity();
		List<ClassRelationEntity> classRelationList = new ArrayList<>();
		for(ClassEntity classEntity : classListParam) {
			String class1Name = classEntity.getClassName();
			List<FieldEntity> fieldList = classEntity.getField();
			//System.out.println("fieldList---"+fieldList+"---count="+fieldList.size());
			
			if(fieldList != null && fieldList.size()>0 ) {
				for(FieldEntity fieldEntity : fieldList) {
					String class2Name = fieldEntity.getFieldType();
					ClassRelationEntity classRelationEntity = new ClassRelationEntity();
					classRelationEntity.setClass1(class1Name);
					classRelationEntity.setClass2(class2Name);
					classRelationEntity.setRelationType("Field");
					classRelationList.add(classRelationEntity);
				}
			}
		}
		classRelationListEntity.setRelationList(classRelationList);
		
		//输出xml到文件
		String xml = JaxbUtil.beanToXml(classRelationListEntity);
		String outputFile = this.outputFieldXmlFile;
		ClassUtil.writeXmlToFile(xml, outputFile);
		
		return classRelationListEntity;
	}
	
	//获取class之间的属性关系(Id)
	public void getNodeFieldRelation(List<ClassEntity> classListParam, ClassRelationListEntity classInheritListEntity) {
		long id1;
		long id2;
		List<ClassRelationEntity> classRelationList = classInheritListEntity.getRelationList();
		
		String outputFile = this.outputFieldTxtFile;
		File file = new File(outputFile);
		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(ClassRelationEntity classRelationEntity : classRelationList) {
				
				String class2 = classRelationEntity.getClass2();
				id2 = ClassUtil.getClassId(classListParam, class2);
				//如果没有找到该类，就不用记录
				if(id2 == -1) {
					continue;
				}
				
				String class1 = classRelationEntity.getClass1();
				id1 = ClassUtil.getClassId(classListParam, class1);
				
				bufferedWriter.write(String.valueOf(id1)+" "+
						String.valueOf(id2));
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
		ClassFieldRelation classFieldRelation = new ClassFieldRelation();
		String xmlString = ClassUtil.getXmlString(ClassUtil.filePath);
		List<ClassEntity> classList = ClassUtil.getClassList(xmlString);
		
		ClassRelationListEntity classRelationListEntity = classFieldRelation.getClassFieldRelation(classList);
		
		classFieldRelation.getNodeFieldRelation(classList, classRelationListEntity);
		
	}
}
