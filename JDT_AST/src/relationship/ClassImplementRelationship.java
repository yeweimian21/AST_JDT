package relationship;

import util.relationship.ClassRelationshipUtil;
import util.common.ClassUtil;
import xmlentity.ClassEntity;
import xmlentity.ClassRelationEntity;
import xmlentity.ClassRelationListEntity;
import util.xml.JaxbUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassImplementRelationship {

    private String implementXmlDirectory;
    private String implementNameDirectory;
    private String implementIdDirectory;
    private String relationshipType;

    public ClassImplementRelationship() {
        implementXmlDirectory = ClassUtil.outputResultPath + "/class_relationship/implement/xml/";
        implementNameDirectory = ClassUtil.outputResultPath + "/class_relationship/implement/class_name/";
        implementIdDirectory = ClassUtil.outputResultPath + "/class_relationship/implement/class_id/";
        relationshipType = "implement";

        // create the directory
        ClassUtil.createDirectory(implementXmlDirectory);
        ClassUtil.createDirectory(implementNameDirectory);
        ClassUtil.createDirectory(implementIdDirectory);
    }

    //	get the Implement relationship between class, output to file
    public void writeClassImplementRelationshipToXmlFile() {
        ArrayList<File> fileList = ClassUtil.getFileList(ClassUtil.classXmlDirectory, "xml");
        for(File classXmlFile : fileList) {
            ClassRelationListEntity classRelationshipListEntity = new ClassRelationListEntity();
            List<ClassRelationEntity> classRelationshipList = new ArrayList<>();
            String classXml = ClassUtil.getXmlString(classXmlFile);
            ClassEntity classEntity = JaxbUtil.xmlToBean(classXml, ClassEntity.class);
            String className1 = classEntity.getClassName();
            List<String> interfaceList = classEntity.getSuperInterface();
//          output the file when the class implement interface
            if(interfaceList != null && interfaceList.size()>0 ) {
                for(String interfaceName : interfaceList) {
                    // if interface is not null
                    if (interfaceName != null && !interfaceName.equals("")) {
                        String className2 = interfaceName;
                        ClassRelationEntity classRelationEntity = new ClassRelationEntity();
                        classRelationEntity.setClass1(className1);
                        classRelationEntity.setClass2(className2);
                        classRelationEntity.setRelationType(relationshipType);
                        classRelationshipList.add(classRelationEntity);
                    }
                }
                if (classRelationshipList.size() > 0){
                    classRelationshipListEntity.setRelationList(classRelationshipList);
                    String classImplementXml = JaxbUtil.beanToXml(classRelationshipListEntity);
                    File implementXmlFile = new File(implementXmlDirectory + classXmlFile.getName());
                    ClassUtil.writeXmlToFile(classImplementXml, implementXmlFile);
                }
            }
        }
    }

    //	get the Implement relationship between class(class name), output it to txt file
    public void getImplementRelationshipClassName() {
        ClassRelationshipUtil.getRelationshipClassName(implementXmlDirectory, implementNameDirectory,
                relationshipType);
    }

    //	get the Implement relationship between class(class id), output it to txt file
    public void getImplementRelationshipClassId() {
        ClassRelationshipUtil.getRelationshipClassId(implementXmlDirectory, implementIdDirectory,
                relationshipType);
    }

}
