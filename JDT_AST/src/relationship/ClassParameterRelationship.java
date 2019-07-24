package relationship;

import util.relationship.ClassRelationshipUtil;
import util.common.ClassUtil;
import util.xml.JaxbUtil;
import xmlentity.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassParameterRelationship {

    private String parameterXmlDirectory;
    private String parameterNameDirectory;
    private String parameterIdDirectory;
    private String parameterRelationshipType;

    public ClassParameterRelationship() {
        parameterXmlDirectory = ClassUtil.outputResultPath + "/class_relationship/parameter/xml/";
        parameterNameDirectory = ClassUtil.outputResultPath + "/class_relationship/parameter/class_name/";
        parameterIdDirectory = ClassUtil.outputResultPath + "/class_relationship/parameter/class_id/";
        parameterRelationshipType = "parameter";

        // create the directory
        ClassUtil.createDirectory(parameterXmlDirectory);
        ClassUtil.createDirectory(parameterNameDirectory);
        ClassUtil.createDirectory(parameterIdDirectory);
    }

    //	get the parameter relationship between class, output to file
    public void writeClassParameterRelationshipToXmlFile() {
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
                    List<String> parameterList = method.getParametersList();
//                  if the method has parameter type
                    if (parameterList != null && parameterList.size() > 0){
                        for (String parameterType : parameterList){
//                          if the parameter type is not null
                            if(parameterType != null && !parameterType.equals("")){
                                String className2 = parameterType;
                                ClassRelationEntity classRelationEntity = new ClassRelationEntity();
                                classRelationEntity.setClass1(className1);
                                classRelationEntity.setClass2(className2);
                                classRelationEntity.setRelationType(parameterRelationshipType);
                                classRelationshipList.add(classRelationEntity);
                            }
                        }
                    }
                }
//				if all methods in the class at least contain one parameter type
                if (classRelationshipList.size() > 0){
                    classRelationshipListEntity.setRelationList(classRelationshipList);
                    String classParameterXml = JaxbUtil.beanToXml(classRelationshipListEntity);
                    File parameterXmlFile = new File(parameterXmlDirectory + classXmlFile.getName());
                    ClassUtil.writeXmlToFile(classParameterXml, parameterXmlFile);
                }
            }
        }
    }

    //	get the parameter relationship between class(class name), output it to txt file
    public void getParameterRelationshipClassName() {
        ClassRelationshipUtil.getRelationshipClassName(parameterXmlDirectory, parameterNameDirectory,
                parameterRelationshipType);
    }

    //	get the parameter relationship between class(class id), output it to txt file
    public void getParameterRelationshipClassId() {
        ClassRelationshipUtil.getRelationshipClassId(parameterXmlDirectory, parameterIdDirectory,
                parameterRelationshipType);
    }

}
