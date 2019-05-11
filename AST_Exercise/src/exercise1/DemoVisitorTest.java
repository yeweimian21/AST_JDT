package exercise1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import getrelation.ClassUtil;
import xmlutil.ClassEntity;
import xmlutil.JaxbUtil;
import xmlutil.ListEntity;
 
public class DemoVisitorTest {
	//private static String directoryPath = "E:/Eclipse/AST/AST_Exercise/src/dataset/iTrust";
	
	//能够处理的源代码文件类型
	//private static String fileSuffix = ".java";
	
	private String dataSetDirectory;
	private String projectName;
	private String projectPath;
	private String codeFileSuffix;
	private String outputId;
	private String outputXmlFile;
	
	public DemoVisitorTest() {
		this.dataSetDirectory = "E:/Eclipse/AST/AST_Exercise/src/dataset/";
		this.projectName = "eTOUR";
		this.projectPath = this.dataSetDirectory+this.projectName+"/CC/";
		this.codeFileSuffix = ".txt";
		this.outputId = ClassUtil.outputId;
		this.outputXmlFile = "E:/Eclipse/AST/AST_Exercise/src/output/"+this.projectName+"/XMLObject"+this.outputId+".xml";
	}
	
	//调用编译工具
//	public DemoVisitorTest(String path) {
//		CompilationUnit comp = JdtAstUtil.getCompilationUnit(path);
//		
//		DemoVisitor visitor = new DemoVisitor();
//		comp.accept(visitor);
//	}
	
	//读取project下的所有class文件(递归方法)
	public List<String> getFileList(String strPath) {
		ArrayList<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles(); 
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // 判断是文件还是文件夹
                if (files[i].isDirectory()) { 
                	// 获取文件绝对路径
                    filelist.addAll(getFileList(files[i].getAbsolutePath())); 
                } 
                // 判断文件名是否以.java结尾
                else if (fileName.endsWith(this.codeFileSuffix)) { 
                    String strFileName = files[i].getAbsolutePath();
//                    System.out.println("---" + strFileName);
                    filelist.add(strFileName);
                } 
                else {
                    continue;
                }
                
            }

        }
        return filelist;
    }
	
	//将文件数组中的所有文件转换为AST
	public static void main(String[] args) {
		DemoVisitorTest demoVisitorTest = new DemoVisitorTest();
		
		List<String> filelist = demoVisitorTest.getFileList(demoVisitorTest.projectPath);
		
		ListEntity listEntity = new ListEntity();
		
		ArrayList<ClassEntity> classList = new ArrayList<>();
		
		long classId = 0;
//		Iterator<String> iterator = filelist.iterator();
//		while(iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}
		
//		String fileOutputPath = "E:/Eclipse/AST/AST_Exercise/src/output/result10.xml";
		File outputFile = new File(demoVisitorTest.outputXmlFile);
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);	
			for(String filePath : filelist) {
				
//				DemoVisitorTest demoVisitorTest = new DemoVisitorTest(filePath);
				CompilationUnit comp = JdtAstUtil.getCompilationUnit(filePath);
				
				DemoVisitor visitor = new DemoVisitor();
				comp.accept(visitor);
				
				visitor.getClassEntity().setId(classId);
				visitor.getClassEntity().setField(visitor.getFieldList());
				visitor.getClassEntity().setMethod(visitor.getMethodList());
				classList.add(visitor.getClassEntity());
				
				classId++;
				
//				DemoVisitor.classEntity.setField(DemoVisitor.fieldList);
//				DemoVisitor.classEntity.setMethod(DemoVisitor.methodList);
//				classList.add(DemoVisitor.classEntity);
//				
//				DemoVisitor.classEntity = null;
//				DemoVisitor.fieldList = null;
//				DemoVisitor.methodList = null;
				
//				bufferedWriter.write("####################");
//				bufferedWriter.newLine();
//				bufferedWriter.flush();
			}
//			bufferedWriter.close();
			listEntity.setClassList(classList);
			
			String xml = JaxbUtil.beanToXml(listEntity);
			//System.out.println(xml);
			
			bufferedWriter.write(xml);
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
}
