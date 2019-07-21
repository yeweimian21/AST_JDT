package exercise1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/*
* Load the Java source code, convert source code to AST.
* Get compilation unit of source code.
* */

public class JdtAstUtil {
    /**
    * @param javaFilePath
    * @return CompilationUnit
    */
	
    public static CompilationUnit getCompilationUnit(String javaFilePath){
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
			e.printStackTrace();
		}
		
        
//		ASTParser astParser = ASTParser.newParser(AST.JLS8);
		ASTParser astParser = ASTParser.newParser(AST.JLS12);
        astParser.setSource(new String(input).toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
 
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
        
        return result;
    }
}
