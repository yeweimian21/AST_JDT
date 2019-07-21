package xmlutil;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class FieldEntity {
	
	@XmlElement(name = "FieldName")
	private String fieldName;
	
	@XmlElement(name = "FieldType")
	private String fieldType;

	public FieldEntity() {
		super();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}
