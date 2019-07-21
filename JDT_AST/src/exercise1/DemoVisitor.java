package exercise1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import xmlutil.ClassEntity;
import xmlutil.FieldEntity;
import xmlutil.MethodEntity;

//access the package, class name, attribute, method of the class node.
public class DemoVisitor extends ASTVisitor {
	
	private FileWriter fileWriter = null;
	private BufferedWriter bufferedWriter = null;
	
	private ClassEntity classEntity;
	
	private ArrayList<FieldEntity> fieldList;
	private ArrayList<MethodEntity> methodList;
	
	public DemoVisitor() {
		super();
		this.classEntity = new ClassEntity();
		this.fieldList = new ArrayList<>();
		this.methodList = new ArrayList<>();
	}
	
//	access the package
	@Override
	public boolean visit(PackageDeclaration node) {
		classEntity.setPackageName(node.getName().toString());
		return true;
	}
	
//	access the class
	@Override
	public boolean visit(TypeDeclaration node) {
//		get the class name
		classEntity.setClassName(node.getName().toString());
		
//		get the super class
		Type superClassType = node.getSuperclassType();
		if(superClassType == null) {
			classEntity.setSuperClassName("");
		}
		else {
//			if the super class is generic type
			if(superClassType.isParameterizedType()) {
				ParameterizedType parameterizedType = (ParameterizedType) superClassType;
				String parameterizedType1 = parameterizedType.getType().toString();
				classEntity.setSuperClassName(parameterizedType1);
			}
			else {
				classEntity.setSuperClassName(superClassType.toString());
			}
		}

//		get the interface of class implements
		List<String> superInterface = new ArrayList<>();
		List<Type> superInterfaceList = node.superInterfaceTypes();
		if(superInterfaceList == null || superInterfaceList.size() == 0) {
			superInterface.add("");
		}
		else {
			for(Type superInterfaceType : superInterfaceList) {
//				if the interface is generic type
				if(superInterfaceType.isParameterizedType()) {
					ParameterizedType parameterizedType = (ParameterizedType) superInterfaceType;
					String parameterizedType1 = parameterizedType.getType().toString();
					String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
					superInterface.add(parameterizedType1);
					superInterface.add(parameterizedType2);
				}
				else {
					superInterface.add(superInterfaceType.toString());
				}
			}
		}
		classEntity.setSuperInterface(superInterface);
		return true;
	}

//	access the attribute
	@Override
	public boolean visit(FieldDeclaration node) {
			for (Object obj: node.fragments()) {
				VariableDeclarationFragment v = (VariableDeclarationFragment)obj;
				Type fieldType = node.getType();
//				if attribute type is generic type
				if(fieldType.isParameterizedType()) {
					ParameterizedType parameterizedType = (ParameterizedType) fieldType;
					String parameterizedType1 = parameterizedType.getType().toString();
					String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
					FieldEntity fieldEntity1 = new FieldEntity();
					fieldEntity1.setFieldName(v.getName().toString());
					fieldEntity1.setFieldType(parameterizedType1);
					fieldList.add(fieldEntity1);
					FieldEntity fieldEntity2 = new FieldEntity();
					fieldEntity2.setFieldName(v.getName().toString());
					fieldEntity2.setFieldType(parameterizedType2);
					fieldList.add(fieldEntity2);
				}
				else {
					FieldEntity fieldEntity = new FieldEntity();
					fieldEntity.setFieldName(v.getName().toString());
					fieldEntity.setFieldType(fieldType.toString());
					fieldList.add(fieldEntity);
				}
			}
		return true;
	}
 
//	access the method
	@Override
	public boolean visit(MethodDeclaration node) {
			MethodEntity methodEntity = new MethodEntity();
//			get the method name
			methodEntity.setMethodName(node.getName().toString());
			
//			get the return type of method
			Type returnType = node.getReturnType2();
			if(returnType == null) {
				methodEntity.setReturnType("");
			}
			else {
//				if return type is generic type
				if(returnType.isParameterizedType()) {
					ParameterizedType parameterizedType = (ParameterizedType) returnType;
					String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
					methodEntity.setReturnType(parameterizedType2);
				}
				else {
					methodEntity.setReturnType(returnType.toString());
				}
				
			}
			
//			get the parameters of the method
			List<String> paramters = new ArrayList<>();
			List<SingleVariableDeclaration> parameterList = node.parameters();
			if(parameterList == null || parameterList.size() == 0) {
				paramters.add("");
			}
			else {
				for(SingleVariableDeclaration SingleVariableDeclaration : parameterList) {
					Type paramterType = SingleVariableDeclaration.getType();
//					if the parameter type is generic type
					if(paramterType.isParameterizedType()) {
						ParameterizedType parameterizedType = (ParameterizedType) paramterType;
						String parameterizedType2 = parameterizedType.typeArguments().get(0).toString();
						paramters.add(parameterizedType2);
					}
					else {
						paramters.add(paramterType.toString());
					}
				}
			}
			methodEntity.setParametersList(paramters);
			
//			get the exception thrown by method
			List<String> exceptions = new ArrayList<>();
			List<Type> exceptionList = node.thrownExceptionTypes();
			if(exceptionList == null || exceptionList.size() == 0) {
				exceptions.add("");
			}
			else {
				for(Type type : exceptionList) {
					exceptions.add(type.toString());
				}
			}
			methodEntity.setThrowException(exceptions);
			
			methodList.add(methodEntity);

		return true;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public ArrayList<FieldEntity> getFieldList() {
		return fieldList;
	}

	public void setFieldList(ArrayList<FieldEntity> fieldList) {
		this.fieldList = fieldList;
	}

	public ArrayList<MethodEntity> getMethodList() {
		return methodList;
	}

	public void setMethodList(ArrayList<MethodEntity> methodList) {
		this.methodList = methodList;
	}

}
