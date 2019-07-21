package exercise1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//临时测试
public class MethodVisitor {
	public  static String fileOutputPath = "E:/Eclipse/AST/AST_Exercise/src/output/result1.txt";
	public  static File outputFile = new File(fileOutputPath);
	public static FileWriter fileWriter = null;
	public static BufferedWriter bufferedWriter = null;
	
	public static void main(String[] args) {
		for(int i = 0; i<10; i++) {
			try {
				fileWriter = new FileWriter(outputFile, true);
				bufferedWriter = new BufferedWriter(fileWriter);
				String methodStr = "Method:\t";
				System.out.println(methodStr);
				bufferedWriter.write(methodStr);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				bufferedWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
