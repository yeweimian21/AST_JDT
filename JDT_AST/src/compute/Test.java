package compute;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class Test {
	
	private String inputFile;
	private String output;
	
	public Test() {
		this.inputFile = "E:/Eclipse/AST/AST_Exercise/src/output/mergeNodeId3.txt";
		this.output = "E:/Eclipse/AST/AST_Exercise/src/output/LinkRelation/linkRepeat1.txt";
		
	}
	
	public void getRepeat(String inputFile, String outputFile) {
		try {
			int[][] matrix = new int[2000][2];
			int lineNum = 1;
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line="";

			FileWriter fileWriter = new FileWriter(outputFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			while((line = bufferedReader.readLine()) != null) {
				String[] strId = line.split(" ");
				int id1 = Integer.parseInt(strId[0]);
				int id2 = Integer.parseInt(strId[1]);
				matrix[lineNum][0] = id1;
				matrix[lineNum][1] = id2;
				lineNum++;
				
			}
//			System.out.println("lineNum="+lineNum);
//			for(int i =0; i<lineNum; i++) {
//				System.out.println(matrix[i][0]+ " "+ matrix[i][1]);
//			}
			for(int i=1; i<lineNum; i++) {
				int id1 = matrix[i][0];
				int id2 = matrix[i][1];
				for(int j=i+1; j<lineNum; j++) {
					if(j != i) {
						int innerId1 = matrix[j][0];
						int innerId2 = matrix[j][1];
//						if((id1==innerId1 && id2 ==innerId2)||
//								(id1==innerId2 && id2 ==innerId1)) {
//							System.out.println("i="+i);
//							System.out.println("j="+j);
//							System.out.println("id1="+id1);
//							System.out.println("id2="+id2);
//							System.out.println("innerId1="+innerId1);
//							System.out.println("innerId2="+innerId2);
//						}
//						if((id1==innerId1 && id2 ==innerId2)) {
////							System.out.println("start");
//							System.out.println("i="+i);
//							System.out.println("id1="+id1+" id2="+id2);
////							System.out.println("id2="+id2);
//							System.out.println("j="+j);
//							
//							System.out.println("innerId1="+innerId1+" innerId2="+innerId2);
////							System.out.println("innerId2="+innerId2);
////							System.out.println("end");
//							System.out.println();
//						}
						if((id1==innerId1 && id2 ==innerId2)) {
							bufferedWriter.write("i="+i);
							bufferedWriter.newLine();
							bufferedWriter.write("id1="+id1+" id2="+id2);
							bufferedWriter.newLine();
							bufferedWriter.write("j="+j);
							bufferedWriter.newLine();
							bufferedWriter.write("innerId1="+innerId1+" innerId2="+innerId2);
							bufferedWriter.newLine();
							bufferedWriter.newLine();
						}
					}
				}
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void countNode(String inputFile) {
		int[] classId = new int[300];
		int count = 0;
		for(int i=0; i<classId.length; i++) {
			classId[i]=-1;
		}
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line="";
			while((line = bufferedReader.readLine()) != null) {
				String[] strId = line.split(" ");
				int id1 = Integer.parseInt(strId[0]);
				int id2 = Integer.parseInt(strId[1]);
				if(classId[id1] == -1) {
					classId[id1] = 0;
				}
				if(classId[id2] == -1) {
					classId[id2] = 0;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<classId.length; i++) {
			if(classId[i] == 0) {
				count++;
			}
		}
		System.out.println("count="+count);
	}
	
	//寻找不同的节点，207个节点与205个节点
	public void getDiffNode() {
		String inputFile1 = "E:/Eclipse/AST/AST_Exercise/src/output/mergeNodeId3.txt";
		String inputFile2 = "E:/Eclipse/AST/AST_Exercise/src/input/mergeNodeId/result1.txt";
		int[] classId1 = new int[300];
		int[] classId2 = new int[300];
		int count = 0;
		for(int i=0; i<classId1.length; i++) {
			classId1[i]=-1;
		}
		for(int i=0; i<classId2.length; i++) {
			classId2[i]=-1;
		}
		try {
			File file1 = new File(inputFile1);
			FileReader fileReader1 = new FileReader(file1);
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			String line="";
			while((line = bufferedReader1.readLine()) != null) {
				String[] strId = line.split(" ");
				int id1 = Integer.parseInt(strId[0]);
				int id2 = Integer.parseInt(strId[1]);
				if(classId1[id1] == -1) {
					classId1[id1] = 0;
				}
				if(classId1[id2] == -1) {
					classId1[id2] = 0;
				}
			}
			
			File file2 = new File(inputFile2);
			FileReader fileReader2 = new FileReader(file2);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			line = bufferedReader2.readLine();
			line="";
			while((line = bufferedReader2.readLine()) != null) {
				String[] strId = line.split(" ");
				int id1 = Integer.parseInt(strId[0]);
				if(classId2[id1] == -1) {
					classId2[id1] = 0;
				}
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<classId1.length; i++) {
			if(classId1[i] != classId2[i]) {
				System.out.println("i="+i);
			}
		}
	}
	
	public static void main(String[] args) {
		Test test = new Test();
//		test.countNode(test.inputFile);
//		test.getRepeat(test.inputFile, test.output);
		test.getDiffNode();
	}
}
