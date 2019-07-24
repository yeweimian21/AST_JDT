package xmlentity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RelationPair")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassRelationEntity {

	@XmlElement(name = "Class1")
	private String class1;
	
	@XmlElement(name = "Class2")
	private String class2;
	
	@XmlElement(name = "RelationType")
	private String relationType;
	
	public ClassRelationEntity() {
		super();
	}

	public String getClass1() {
		return class1;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}

	public String getClass2() {
		return class2;
	}

	public void setClass2(String class2) {
		this.class2 = class2;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
}
