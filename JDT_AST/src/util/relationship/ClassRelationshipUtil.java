package util.relationship;

import util.common.ClassUtil;
import util.xml.JaxbUtil;
import xmlentity.ClassRelationEntity;
import xmlentity.ClassRelationListEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassRelationshipUtil {

    //	get the relationship between class(class name), output it to txt file
    public static void getRelationshipClassName(String inputXmlDirectory, String outputNameDirectory,
                                                String relationshipType) {
        ArrayList<File> fileList = ClassUtil.getFileList(inputXmlDirectory, "xml");
        for(File relationshipXmlFile : fileList){
            String relationshipListXml = ClassUtil.getXmlString(relationshipXmlFile);
            ClassRelationListEntity classRelationListEntity =
                    JaxbUtil.xmlToBean(relationshipListXml, ClassRelationListEntity.class);
            List<ClassRelationEntity> relationshipList = classRelationListEntity.getRelationList();
//          output the file when the class has the relationship
            if(relationshipList != null && relationshipList.size() > 0){
                String className = ClassUtil.getMainFileName(relationshipXmlFile);
                File nameFile = new File(outputNameDirectory + className + ".txt");
                try{
                    FileWriter fileWriter = new FileWriter(nameFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    for(ClassRelationEntity relationship : relationshipList){
                        String class1 = relationship.getClass1();
                        String class2 = relationship.getClass2();
                        String line = class1 + " " + class2 + " " + relationshipType + "\n";
                        bufferedWriter.write(line);
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //	get the relationship between class(class id), output it to txt file
    public static void getRelationshipClassId(String inputXmlDirectory, String outputIdDirectory,
                                              String relationshipType) {
        ArrayList<File> fileList = ClassUtil.getFileList(inputXmlDirectory, "xml");
        for(File relationshipXmlFile : fileList){
            String relationshipListXml = ClassUtil.getXmlString(relationshipXmlFile);
            ClassRelationListEntity classRelationListEntity =
                    JaxbUtil.xmlToBean(relationshipListXml, ClassRelationListEntity.class);
            List<ClassRelationEntity> relationshipList = classRelationListEntity.getRelationList();
//          output the file when the class has the relationship
            if(relationshipList != null && relationshipList.size() > 0){
                StringBuffer lineTemp = new StringBuffer();
                for(ClassRelationEntity relationship : relationshipList){
                    String class2 = relationship.getClass2();
                    long id2 = ClassUtil.getClassIdByClassName(class2);
//					If the class is not found, the relationship don't need to recorded。
                    if(id2 == -1) {
                        continue;
                    }
                    String class1 = relationship.getClass1();
                    long id1 = ClassUtil.getClassIdByClassName(class1);
                    lineTemp.append(id1 + " " + id2 + " " + relationshipType + "\n");
                }
//              output the file when the lineTemp has content
                if(lineTemp.length()>0){
                    try{
                        String className = ClassUtil.getMainFileName(relationshipXmlFile);
                        File nameFile = new File(outputIdDirectory + className + ".txt");
                        FileWriter fileWriter = new FileWriter(nameFile);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(lineTemp.toString());
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
