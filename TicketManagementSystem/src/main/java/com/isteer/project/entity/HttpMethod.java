package com.isteer.project.entity;

public class HttpMethod {

	private String methodId;
	private String methodName;
	
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Override
	public String toString() {
		return "HttpMethod [methodId=" + methodId + ", methodName=" + methodName + "]";
	}
}
