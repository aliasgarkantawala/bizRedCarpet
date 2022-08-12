package com.bizongo.redCarpet.filter.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import com.bizongo.redCarpet.constant.BizRedCarpetConstants;
import com.bizongo.redCarpet.filter.auth.config.AppConfig;
import com.bizongo.redCarpet.filter.exception.MissingConfigurationException;
import com.bizongo.redCarpet.filter.exception.UnAuthorisedUserException;
import com.bizongo.redCarpet.model.UserAccessInfoDtls;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BizRedCarperAuthorizerFilterHelper {
	
	@Autowired
	private AppConfig appConfig;

	private String getApiName(HttpMethod httpMethod, ServerHttpRequest request) {
		String uri = request.getURI().toString().split("\\?")[0];
		String appName = uri.split("/")[3];
		String apiName = appConfig.getValueSettings("1", appName);
		if(apiName == null || apiName.isBlank()) {
			
			appName += "_" + uri.split("/")[4];
			
			apiName = appConfig.getValueSettings("1", appName);
			if(apiName == null || apiName.isBlank()) {
				throw new MissingConfigurationException(BizRedCarpetConstants.MISSING_APPLICATION_NAME);
			}
			
		}
		switch(httpMethod) {	
		case GET:
			return apiName+"_GET";
		case POST:
			return apiName+"_POST";
		case PUT:
			return apiName+"_PUT";
		case DELETE:
			return apiName+"_DEL";
		default:
			return "";
		}
	}
	
	public boolean checkAuthorisation(ServerHttpRequest request, 
			UserAccessInfoDtls userAccessInfoDtls) {
		
		String apiName = getApiName(request.getMethod(), request);
		
		if(!userAccessInfoDtls.getEndPoints().containsKey(apiName)) {
			throw new UnAuthorisedUserException(userAccessInfoDtls.getUserId().toString(), 
					String.format(BizRedCarpetConstants.BRC_UNAOTHORIZED_USER_MESSAGE, 
							userAccessInfoDtls.getUserId().toString()));
		}
		
		return true;
	}
}
