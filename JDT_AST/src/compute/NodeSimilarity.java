package compute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import getrelation.ClassUtil;

public class NodeSimilarity {
	
	private String outputId;
	private String inputFile;
	private String inputLinkFile;
	private String outputLinkFile;

	public NodeSimilarity() {
		this.outputId = "1";
		this.inputLinkFile = "src/input/eTOUR/mergeNodeId1.txt";
		this.outputLinkFile = "src/output/eTOUR/linkCount"+this.outputId+".txt";
		this.inputFile = "src/input/mergeNodeId/embeddings3.txt";
	}
	
	public void getNodeEuclideanDistance(String inputFile) {
		
//		try {
//			File file = new File(inputFile);
//			FileReader fileReader = new FileReader(file);
//			BufferedReader bufferedReader = new BufferedReader(fileReader);
//			bufferedReader.readLine();
//			String vector1 = bufferedReader.readLine();
//			String vector2 = bufferedReader.readLine();
//			double distance = NodeUtil.getNodeEuclideanDistance(vector1, vector2);
//			System.out.println("------------------");
//			System.out.println("distance="+distance);
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			File file = new File(inputFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bufferedReader.readLine();
			String vector1 = bufferedReader.readLine();
			String vector2 = bufferedReader.readLine();
			System.out.println(vector1);
			System.out.println(vector2);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		
		NodeSimilarity nodeSimilarity = new NodeSimilarity();
//		nodeSimilarity.getNodeEuclideanDistance(nodeSimilarity.inputFile);
		
		//获得有多少节点有边
//		int nodeNum = NodeUtil.countNode(nodeSimilarity.inputLinkFile);
//		System.out.println("nodeNum="+nodeNum);
		
		NodeUtil.mergeLinkCount(nodeSimilarity.inputLinkFile, 
				nodeSimilarity.outputLinkFile);
		
		
//		nodeSimilarity.getEachLinkCount();
		
//		int classAmount = ClassUtil.getClassAmount();
//		System.out.println("classAmount="+classAmount);
		
	}
}
