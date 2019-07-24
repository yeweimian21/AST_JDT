package relationship;

import util.relationship.ClassRelationshipUtil;
import util.common.ClassUtil;
import util.xml.JaxbUtil;
import xmlentity.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassExceptionRelationship {

    private String exceptionXmlDirectory;
    private String exceptionNameDirectory;
    private String exceptionIdDirectory;
    private String exceptionRelationshipType;

    public ClassExceptionRelationship(){
        exceptionXmlDirectory  = ClassUtil.outputResultPath + "/class_relationship/exception/xml/";
        exceptionNameDirectory = ClassUtil.outputResultPath + "/class_relationship/exception/class_name/";
        exceptionIdDirectory = ClassUtil.outputResultPath + "/class_relationship/exception/class_id/";
        exceptionRelationshipType = "exception";

        // create the directory
        ClassUtil.createDirectory(exceptionXmlDirectory);
        ClassUtil.createDirectory(exceptionNameDirectory);
        ClassUtil.createDirectory(exceptionIdDirectory);
    }

    //	get the exception relationship between class, output to file
    public void writeClassExceptionRelationshipToXmlFile() {
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
                    List<String> exceptionList = method.getThrowException();
//                  if the method has exception type
                    if (exceptionList != null && exceptionList.size() > 0){
                        for (String exceptionType : exceptionList){
//                          if the exception type is not null
                            if(exceptionType != null && !exceptionType.equals("")){
                                String className2 = exceptionType;
                                ClassRelationEntity classRelationEntity = new ClassRelationEntity();
                                classRelationEntity.setClass1(className1);
                                classRelationEntity.setClass2(className2);
                                classRelationEntity.setRelationType(exceptionRelationshipType);
                                classRelationshipList.add(classRelationEntity);
                            }
                        }
                    }
                }
//				if all methods in the class at least contain one exception type
                if (classRelationshipList.size() > 0){
                    classRelationshipListEntity.setRelationList(classRelationshipList);
                    String classExceptionXml = JaxbUtil.beanToXml(classRelationshipListEntity);
                    File exceptionXmlFile = new File(exceptionXmlDirectory + classXmlFile.getName());
                    ClassUtil.writeXmlToFile(classExceptionXml, exceptionXmlFile);
                }
            }
        }
    }

    //	get the exception relationship between class(class name), output it to txt file
    public void getExceptionRelationshipClassName() {
        ClassRelationshipUtil.getRelationshipClassName(exceptionXmlDirectory, exceptionNameDirectory,
                exceptionRelationshipType);
    }

    //	get the exception relationship between class(class id), output it to txt file
    public void getExceptionRelationshipClassId() {
        ClassRelationshipUtil.getRelationshipClassId(exceptionXmlDirectory, exceptionIdDirectory,
                exceptionRelationshipType);
    }

}
