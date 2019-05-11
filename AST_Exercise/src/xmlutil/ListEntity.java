package xmlutil;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ClassList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListEntity {

	@XmlElement(name = "Class")
	private List<ClassEntity> classList;

	public ListEntity() {
		super();
	}

	public List<ClassEntity> getClassList() {
		return classList;
	}

	public void setClassList(List<ClassEntity> classList) {
		this.classList = classList;
	}
	
	
}
