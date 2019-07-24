package relationship;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.relationship.ClassRelationshipUtil;
import util.common.ClassUtil;
import xmlentity.ClassEntity;
import xmlentity.ClassRelationEntity;
import xmlentity.ClassRelationListEntity;
import util.xml.JaxbUtil;

public class ClassInheritRelation {

	private String inheritXmlDirectory;
	private String inheritNameDirectory;
	private String inheritIdDirectory;
	private String relationshipType;

	public ClassInheritRelation() {
		inheritXmlDirectory = ClassUtil.outputResultPath + "/class_relationship/inherit/xml/";
		inheritNameDirectory = ClassUtil.outputResultPath + "/class_relationship/inherit/class_name/";
		inheritIdDirectory = ClassUtil.outputResultPath + "/class_relationship/inherit/class_id/";
		relationshipType = "inherit";

		// create the directory
		ClassUtil.createDirectory(inheritXmlDirectory);
		ClassUtil.createDirectory(inheritNameDirectory);
		ClassUtil.createDirectory(inheritIdDirectory);
	}

//	get the Inherit relationship between class, output to file
	public void writeClassInheritRelationshipToXmlFile() {
		ArrayList<File> fileList = ClassUtil.getFileList(ClassUtil.classXmlDirectory, "xml");
		for(File classXmlFile : fileList) {
			ClassRelationListEntity classRelationshipListEntity = new ClassRelationListEntity();
			List<ClassRelationEntity> classRelationshipList = new ArrayList<>();
			String classXml = ClassUtil.getXmlString(classXmlFile);
			ClassEntity classEntity = JaxbUtil.xmlToBean(classXml, ClassEntity.class);
			String className1 = classEntity.getClassName();
			String superClass = classEntity.getSuperClassName();
			// if superclass is not null
			if (superClass != null && !superClass.equals("")) {
				ClassRelationEntity classRelationEntity = new ClassRelationEntity();
				classRelationEntity.setClass1(className1);
				classRelationEntity.setClass2(superClass);
				classRelationEntity.setRelationType(relationshipType);

				classRelationshipList.add(classRelationEntity);
				classRelationshipListEntity.setRelationList(classRelationshipList);
				String classInheritXml = JaxbUtil.beanToXml(classRelationshipListEntity);
				File InheritXmlFile = new File(inheritXmlDirectory + classXmlFile.getName());
				ClassUtil.writeXmlToFile(classInheritXml, InheritXmlFile);
			}
		}
	}

//	get the inherit relationship between class(class name), output it to txt file
	public void getInheritRelationshipClassName() {
		ClassRelationshipUtil.getRelationshipClassName(inheritXmlDirectory, inheritNameDirectory,
				relationshipType);
	}

//	get the inherit relationship between class(class id), output it to txt file
	public void getInheritRelationshipClassId() {
		ClassRelationshipUtil.getRelationshipClassId(inheritXmlDirectory, inheritIdDirectory,
				relationshipType);
	}

}
