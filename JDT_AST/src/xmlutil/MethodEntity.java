package xmlutil;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class MethodEntity {
	
	@XmlElement(name = "MethodName")
	private String methodName;
	
	@XmlElement(name = "ReturnType")
	private String returnType;
	
	@XmlElementWrapper(name = "ParameterList")
	@XmlElement(name = "ParameterType")
	private List<String> parametersList;
	
	@XmlElementWrapper(name = "ThrowExceptionList")
	@XmlElement(name = "ExceptionType")
	private List<String> throwException;

	public MethodEntity() {
		super();
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<String> getParametersList() {
		return parametersList;
	}

	public void setParametersList(List<String> parametersList) {
		this.parametersList = parametersList;
	}

	public List<String> getThrowException() {
		return throwException;
	}

	public void setThrowException(List<String> throwException) {
		this.throwException = throwException;
	}
	
}
