package getrelation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ClassNodeId {

	private String inputFileDirectory;
	private String outputFile;
	private String outputId;
	
	public ClassNodeId() {
		this.outputId = ClassUtil.outputId;
		this.inputFileDirectory = "E:/Eclipse/AST/AST_Exercise/src/input/eTOUR/";
		this.outputFile = "E:/Eclipse/AST/AST_Exercise/src/output/eTOUR/mergeNodeId"+this.outputId+".txt";
	}
	
	//将所有NodeId文件合并到一个文件
	public void mergeNodeIdFile(List<String> fileList) {
		try {
			File outputFile = new File(this.outputFile);
//			FileWriter fileWriter = new FileWriter(outputFile, true);
			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			File inputfile;
			FileReader fileReader;
			BufferedReader bufferedReader;
//			StringBuffer stringBuffer = new StringBuffer();
			String line = "";
			for(String file : fileList) {
				//每读一个文件
				inputfile = new File(file);
				fileReader = new FileReader(inputfile);
				bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
//					stringBuffer.append(line);
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
				//每写一个文件
//				bufferedWriter.write(stringBuffer.toString());
//				bufferedWriter.write("----------");
//				bufferedWriter.newLine();
				
//				bufferedWriter.write("----------");
//				bufferedWriter.newLine();
				
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ClassNodeId classNodeId = new ClassNodeId();
		List<String> inputFileList = ClassUtil.getFileList(classNodeId.inputFileDirectory);
		
		ClassUtil.mergeNodeIdFile(inputFileList, classNodeId.outputFile);
		
	}
}
