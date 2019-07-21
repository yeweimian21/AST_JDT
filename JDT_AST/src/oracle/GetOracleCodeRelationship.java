package oracle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GetOracleCodeRelationship {

	public void readTheOracleCode() {
		String filePath = "src/input/eTOUR/codelist.txt";
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String strList = bufferedReader.readLine();
			strList = strList.substring(1, strList.length()-1);
			String[] codeIdList = strList.split(", "); 
			System.out.println("codeIdList="+codeIdList[1]);
			System.out.println("codeIdList="+codeIdList[codeIdList.length-1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GetOracleCodeRelationship obj =new GetOracleCodeRelationship();
		obj.readTheOracleCode();
	}
}
