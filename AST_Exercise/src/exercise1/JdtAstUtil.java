package exercise1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
 
//读取Java源代码，将源代码转换为AST
public class JdtAstUtil {
    /**
    * get compilation unit of source code
    * @param javaFilePath 
    * @return CompilationUnit
    */
	
    public static CompilationUnit getCompilationUnit(String javaFilePath){
//        byte[] input = null;
//		try {
//		    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
//		    input = new byte[bufferedInputStream.available()];
//	            bufferedInputStream.read(input);
//	            bufferedInputStream.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    	
//    	byte[] input = null;
//		try {
//		    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
//		    input = new byte[bufferedInputStream.available()];
//	            bufferedInputStream.read(input);
//	            bufferedInputStream.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		String input = "";
		String line = "";
		StringBuffer stringBuffer = new StringBuffer();
		try {
			File file = new File(javaFilePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			input = stringBuffer.toString();
			
			bufferedReader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
        astParser.setSource(new String(input).toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
 
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
        
        return result;
    }
}
