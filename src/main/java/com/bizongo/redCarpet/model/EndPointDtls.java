package com.bizongo.redCarpet.model;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class EndPointDtls implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long endPointId;
	private String endPoint;
	private String endPointName;
	

	public EndPointDtls(Long endPointId, String endPoint, String endPointName) {
		super();
		this.endPointId = endPointId;
		this.endPoint = endPoint;
		this.endPointName = endPointName;
	}
	
	public Long getEndPointId() {
		return endPointId;
	}
	public void setEndPointId(Long endPointId) {
		this.endPointId = endPointId;
	}
	
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	public String getEndPointName() {
		return endPointName;
	}
	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}
	
}
