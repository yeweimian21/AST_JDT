package getrelation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RelationList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassRelationListEntity {

	@XmlElement(name = "RelationPair")
	private List<ClassRelationEntity> relationList;

	public ClassRelationListEntity() {
		super();
	}

	public List<ClassRelationEntity> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<ClassRelationEntity> relationList) {
		this.relationList = relationList;
	}
	
}
