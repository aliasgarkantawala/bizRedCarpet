package com.bizongo.redCarpet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserAccessInfoDtls implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private Map<Long, FeatureDtls> accesibleFeatures;

	private Map<String, EndPointDtls> endPoints;
	
	
	public Map<Long, FeatureDtls> getAccesibleFeatures() {
		if(accesibleFeatures == null)
			accesibleFeatures = new HashMap<Long, FeatureDtls>();
		return accesibleFeatures;
	}
	
	public Map<String, EndPointDtls> getEndPoints() {
		if(endPoints == null) {
			endPoints = new HashMap<String, EndPointDtls>();
		}
		return endPoints;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
