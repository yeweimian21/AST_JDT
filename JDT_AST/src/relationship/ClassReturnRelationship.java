package relationship;

import util.relationship.ClassRelationshipUtil;
import util.common.ClassUtil;
import util.xml.JaxbUtil;
import xmlentity.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassReturnRelationship {

    private String returnXmlDirectory;
    private String returnNameDirectory;
    private String returnIdDirectory;
    private String relationshipType;

    public ClassReturnRelationship() {
        returnXmlDirectory = ClassUtil.outputResultPath + "/class_relationship/return/xml/";
        returnNameDirectory = ClassUtil.outputResultPath + "/class_relationship/return/class_name/";
        returnIdDirectory = ClassUtil.outputResultPath + "/class_relationship/return/class_id/";
        relationshipType = "return";

        // create the directory
        ClassUtil.createDirectory(returnXmlDirectory);
        ClassUtil.createDirectory(returnNameDirectory);
        ClassUtil.createDirectory(returnIdDirectory);
    }

    //	get the return relationship between class, output to file
    public void writeClassReturnRelationshipToXmlFile() {
        ArrayList<File> fileList = ClassUtil.getFileList(ClassUtil.classXmlDirectory, "xml");
        for(File classXmlFile : fileList) {
            ClassRelationListEntity classRelationshipListEntity = new ClassRelationListEntity();
            List<ClassRelationEntity> classRelationshipList = new ArrayList<>();
            String classXml = ClassUtil.getXmlString(classXmlFile);
            ClassEntity classEntity = JaxbUtil.xmlToBean(classXml, ClassEntity.class);
            String className1 = classEntity.getClassName();
            List<MethodEntity> methodList = classEntity.getMethod();
//          output the file when the class has methods
            if(methodList != null && methodList.size()>0 ) {
                for(MethodEntity method : methodList) {
                    String returnType = method.getReturnType();
//					if the method has return type
                    if(returnType != null && !returnType.equals("")){
                        String className2 = method.getReturnType();
                        ClassRelationEntity classRelationEntity = new ClassRelationEntity();
                        classRelationEntity.setClass1(className1);
                        classRelationEntity.setClass2(className2);
                        classRelationEntity.setRelationType(relationshipType);
                        classRelationshipList.add(classRelationEntity);
                    }
                }
//				if all methods in the class at least contain one return type
                if (classRelationshipList.size() > 0){
                    classRelationshipListEntity.setRelationList(classRelationshipList);
                    String classReturnXml = JaxbUtil.beanToXml(classRelationshipListEntity);
                    File returnXmlFile = new File(returnXmlDirectory + classXmlFile.getName());
                    ClassUtil.writeXmlToFile(classReturnXml, returnXmlFile);
                }
            }
        }
    }

    //	get the return relationship between class(class name), output it to txt file
    public void getReturnRelationshipClassName() {
        ClassRelationshipUtil.getRelationshipClassName(returnXmlDirectory, returnNameDirectory,
                relationshipType);
    }

    //	get the return relationship between class(class id), output it to txt file
    public void getReturnRelationshipClassId() {
        ClassRelationshipUtil.getRelationshipClassId(returnXmlDirectory, returnIdDirectory,
                relationshipType);
    }

}
