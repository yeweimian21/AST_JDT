package relationship;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.relationship.ClassRelationshipUtil;
import util.common.ClassUtil;
import util.xml.JaxbUtil;
import xmlentity.*;

public class ClassAttributeRelation {
	
	private String attributeXmlDirectory;
	private String attributeNameDirectory;
	private String attributeIdDirectory;
	private String relationshipType;
	
	public ClassAttributeRelation() {
		attributeXmlDirectory = ClassUtil.outputResultPath + "/class_relationship/attribute/xml/";
		attributeNameDirectory = ClassUtil.outputResultPath + "/class_relationship/attribute/class_name/";
		attributeIdDirectory = ClassUtil.outputResultPath + "/class_relationship/attribute/class_id/";
		relationshipType = "attribute";

		// create the directory
		ClassUtil.createDirectory(attributeXmlDirectory);
		ClassUtil.createDirectory(attributeNameDirectory);
		ClassUtil.createDirectory(attributeIdDirectory);
	}
	
//	get the attribute relationship between class, output it to xml file
	public void writeClassAttributeRelationshipToXmlFile(){
		ArrayList<File> fileList = ClassUtil.getFileList(ClassUtil.classXmlDirectory, "xml");
		for(File classXmlFile : fileList){
			ClassRelationListEntity classRelationshipListEntity = new ClassRelationListEntity();
			List<ClassRelationEntity> classRelationshipList = new ArrayList<>();
			String classXml = ClassUtil.getXmlString(classXmlFile);
			ClassEntity classEntity = JaxbUtil.xmlToBean(classXml, ClassEntity.class);
			String className1 = classEntity.getClassName();
			List<FieldEntity> fieldList = classEntity.getField();
//			output the file when the class has attributes
			if(fieldList != null && fieldList.size() > 0) {
				for(FieldEntity fieldEntity : fieldList) {
					String attributeType = fieldEntity.getFieldType();
					// if attribute type is not null
					if (attributeType != null && !attributeType.equals("")){
						String className2 = attributeType;
						ClassRelationEntity classRelationEntity = new ClassRelationEntity();
						classRelationEntity.setClass1(className1);
						classRelationEntity.setClass2(className2);
						classRelationEntity.setRelationType(relationshipType);
						classRelationshipList.add(classRelationEntity);
					}
				}
				if (classRelationshipList.size() > 0) {
					classRelationshipListEntity.setRelationList(classRelationshipList);
					String classAttributeXml = JaxbUtil.beanToXml(classRelationshipListEntity);
					File attributeXmlFile = new File(attributeXmlDirectory + classXmlFile.getName());
					ClassUtil.writeXmlToFile(classAttributeXml, attributeXmlFile);
				}
			}
		}
	}

	//	get the attribute relationship between class(class name), output it to txt file
	public void getAttributeRelationshipClassName() {
		ClassRelationshipUtil.getRelationshipClassName(attributeXmlDirectory, attributeNameDirectory,
				relationshipType);
	}

	//	get the attribute relationship between class(class id), output it to txt file
	public void getAttributeRelationshipClassId() {
		ClassRelationshipUtil.getRelationshipClassId(attributeXmlDirectory, attributeIdDirectory,
				relationshipType);
	}

}
