package com.bizongo.redCarpet.filter.auth.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.prefix:Bearer }")
    private String prefix;

    @Value("${secret-token}")
    private String secret;

    @Value("${ums.url}")
    private String umsUrl;

    @Value("${ums.validate.api}")
    private String validateRoute;

    @Value("${ums.fetch-user.api}")
    private String fetchUserUrl;

    @Value("${ums.fetch-company.api}")
    private String fetchCompanyUrl;

    @Value("${ums.secret-token}")
    private String umsSecret;

    private String validateEndpoint;

    private String fetchUserEndpoint;

    private String fetchCompanyEndpoint;

    private String fetchCentreEndpoint;

    private String fetchAdminUserUrlEndPoint;

    @PostConstruct
    public void init(){
        validateEndpoint = umsUrl+validateRoute;
        fetchUserEndpoint = umsUrl+fetchUserUrl;
        fetchCompanyEndpoint = umsUrl+fetchCompanyUrl;
    }

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUmsUrl() {
		return umsUrl;
	}

	public void setUmsUrl(String umsUrl) {
		this.umsUrl = umsUrl;
	}

	public String getValidateRoute() {
		return validateRoute;
	}

	public void setValidateRoute(String validateRoute) {
		this.validateRoute = validateRoute;
	}

	public String getFetchUserUrl() {
		return fetchUserUrl;
	}

	public void setFetchUserUrl(String fetchUserUrl) {
		this.fetchUserUrl = fetchUserUrl;
	}

	public String getFetchCompanyUrl() {
		return fetchCompanyUrl;
	}

	public void setFetchCompanyUrl(String fetchCompanyUrl) {
		this.fetchCompanyUrl = fetchCompanyUrl;
	}

	public String getUmsSecret() {
		return umsSecret;
	}

	public void setUmsSecret(String umsSecret) {
		this.umsSecret = umsSecret;
	}

	public String getValidateEndpoint() {
		return validateEndpoint;
	}

	public void setValidateEndpoint(String validateEndpoint) {
		this.validateEndpoint = validateEndpoint;
	}

	public String getFetchUserEndpoint() {
		return fetchUserEndpoint;
	}

	public void setFetchUserEndpoint(String fetchUserEndpoint) {
		this.fetchUserEndpoint = fetchUserEndpoint;
	}

	public String getFetchCompanyEndpoint() {
		return fetchCompanyEndpoint;
	}

	public void setFetchCompanyEndpoint(String fetchCompanyEndpoint) {
		this.fetchCompanyEndpoint = fetchCompanyEndpoint;
	}

	public String getFetchCentreEndpoint() {
		return fetchCentreEndpoint;
	}

	public void setFetchCentreEndpoint(String fetchCentreEndpoint) {
		this.fetchCentreEndpoint = fetchCentreEndpoint;
	}

	public String getFetchAdminUserUrlEndPoint() {
		return fetchAdminUserUrlEndPoint;
	}

	public void setFetchAdminUserUrlEndPoint(String fetchAdminUserUrlEndPoint) {
		this.fetchAdminUserUrlEndPoint = fetchAdminUserUrlEndPoint;
	}

}
