package xmlutil;

import java.util.ArrayList;

public class TestXml {
	public static void main(String[] args) {
		//
		ArrayList<FieldEntity> fieldList = new ArrayList<>();
		FieldEntity fieldEntity1 = new FieldEntity();
		FieldEntity fieldEntity2 = new FieldEntity();
		fieldEntity1.setFieldName("name");
		fieldEntity2.setFieldName("age");
		fieldList.add(fieldEntity1);
		fieldList.add(fieldEntity2);
		ArrayList<MethodEntity> methodList = new ArrayList<>();
		MethodEntity methodEntity1 = new MethodEntity();
		MethodEntity methodEntity2 = new MethodEntity();
		methodEntity1.setMethodName("set");
		methodEntity2.setMethodName("get");
		methodList.add(methodEntity1);
		methodList.add(methodEntity2);
		//
		ClassEntity classEntity = new ClassEntity();
		classEntity.setPackageName("package.util");
		classEntity.setClassName("Action");
		classEntity.setSuperClassName("BeanAction");
		classEntity.setField(fieldList);
		classEntity.setMethod(methodList);
		//
		ArrayList<ClassEntity> classList = new ArrayList<>();
		classList.add(classEntity);
		
		ListEntity listEntity = new ListEntity();
		listEntity.setClassList(classList);
		String xml = JaxbUtil.beanToXml(listEntity);
		System.out.println(xml);
	}
}
