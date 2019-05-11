package xmlutil;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClassEntity {
	
	@XmlElement(name = "Id")
	private long id;

	@XmlElement(name = "Package")
	private String packageName;
	
	@XmlElement(name = "ClassName")
	private String className;
	
	@XmlElement(name = "SuperClass")
	private String superClassName;
	
	@XmlElementWrapper(name = "SuperInterfaceList")
	@XmlElement(name = "SuperInterface")
	private List<String> superInterface;
	
	@XmlElementWrapper(name = "FieldList")
	@XmlElement(name = "Field")
	private List<FieldEntity> field;
	
	@XmlElementWrapper(name = "MethodList")
	@XmlElement(name = "Method")
	private List<MethodEntity> method;
	
	public ClassEntity() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	public List<FieldEntity> getField() {
		return field;
	}

	public void setField(List<FieldEntity> field) {
		this.field = field;
	}

	public List<MethodEntity> getMethod() {
		return method;
	}

	public void setMethod(List<MethodEntity> method) {
		this.method = method;
	}

	public List<String> getSuperInterface() {
		return superInterface;
	}

	public void setSuperInterface(List<String> superInterface) {
		this.superInterface = superInterface;
	}

}
