package parse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import util.common.ClassUtil;
import org.eclipse.jdt.core.dom.CompilationUnit;

import util.jdtast.JdtAstUtil;
import xmlentity.ClassEntity;
import util.xml.JaxbUtil;

public class ParseCode {

	// convert the Java source code file to AST, output the xml file
	public static void parseSourceCode(){
		int classId = 0;
		ArrayList<File> fileList = ClassUtil.getFileList(ClassUtil.inputProjectPath,
				ClassUtil.codeFileSuffix);
		for(File file : fileList){
			CompilationUnit comp = JdtAstUtil.getCompilationUnit(file.getPath());
			CodeVisitor visitor = new CodeVisitor();
			comp.accept(visitor);
			visitor.getClassEntity().setId(classId);
			visitor.getClassEntity().setField(visitor.getFieldList());
			visitor.getClassEntity().setMethod(visitor.getMethodList());
			ClassEntity classEntity = visitor.getClassEntity();
			String xml = JaxbUtil.beanToXml(classEntity);
			classId++;

			String fileName = file.getName();
			String fileMainName = fileName.substring(0, fileName.lastIndexOf("."));
			File outputFile = new File(ClassUtil.classXmlDirectory, fileMainName + ".xml");
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
	}

}
