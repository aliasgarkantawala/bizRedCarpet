package com.bizongo.redCarpet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class FeatureDtls implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long featureId;
	private String featureName;
	
	private Map<String, EndPointDtls> endPoints;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public void setEndPoints(Map<String, EndPointDtls> endPoints) {
		this.endPoints = endPoints;
	}
	
	public Map<String, EndPointDtls> getEndPoints() {
		if(endPoints == null) {
			endPoints = new HashMap<String, EndPointDtls>();
		}
		return endPoints;
	}
}
