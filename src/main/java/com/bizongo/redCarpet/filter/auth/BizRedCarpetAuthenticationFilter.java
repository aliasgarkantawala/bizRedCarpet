package com.bizongo.redCarpet.filter.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.bizongo.redCarpet.constant.BizRedCarpetConstants;
import com.bizongo.redCarpet.filter.auth.config.JwtConfig;
import com.bizongo.redCarpet.filter.exception.InvalidJWTException;
import com.bizongo.redCarpet.filter.exception.InvalidSecreteTokenException;
import com.bizongo.redCarpet.filter.exception.UnAuthorisedUserException;

import okhttp3.Response;

public class BizRedCarpetAuthenticationFilter implements Filter {
    
	private final Logger log = LoggerFactory.getLogger(BizRedCarpetAuthenticationFilter.class);
	
	@Autowired
    private JwtConfig jwtConfig;
    
    @Autowired
    private RestTemplate restTemplate;
    	
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

    	log.info("Entring into BizRedCarpetAuthenticationFilter");

    	
    	HttpServletRequest request = (HttpServletRequest) servletRequest;
    	HttpServletResponse response = (HttpServletResponse) servletResponse;
    	
    	String path = request.getRequestURI();

        // Ignore authentication if its not a REST API Request
        if (path.contains(BizRedCarpetConstants.BRC_ACTUATOR) || 
        	BizRedCarpetConstants.BRC_FAVICON_URL.equals(path) || 
        	path.contains(BizRedCarpetConstants.BRC_SWAGGER) || 
        	path.contains(BizRedCarpetConstants.BRC_FILES) || 
        	path.contains(BizRedCarpetConstants.BRC_THREADS) || 
        	path.contains(BizRedCarpetConstants.BRC_MESSAGES)) {
        	
            filterChain.doFilter(request, response);
            return;
        }
        
        String jwtToken = request.getHeader(BizRedCarpetConstants.BRC_AUTHORIZATION).
        		replace(BizRedCarpetConstants.BRC_BEARER_SPACE, "");
        String secretToken = request.getHeader(BizRedCarpetConstants.BRC_SECRET_TOKEN);
        
        try {

            // Return Exception if JWT is not in headers
            if (jwtToken != null && !jwtToken.isEmpty()) {

                // Build request for UMS to validate JWT
                HttpHeaders headers = new HttpHeaders();
                headers.add("Service-Token", jwtConfig.getUmsSecret());

                HttpEntity req = new HttpEntity(headers);
                
                ResponseEntity<Response> responseEntity = 
                		restTemplate.postForEntity(jwtConfig.getValidateEndpoint(), 
                				req, Response.class, jwtToken);
                
                Response authResponse = responseEntity.getBody();

                //If response is successful authenticate the requestCommunicatorTest
                if (authResponse.isSuccessful()) {
                	filterChain.doFilter(request, response);
                } else {
                    throw new InvalidJWTException(String.format(BizRedCarpetConstants.BRC_JWT_INVALID_MESSAGE,responseEntity.getStatusCode(), jwtToken));
                }
            } else if (secretToken != null && !secretToken.isEmpty()) {
                if (secretToken.equals(jwtConfig.getSecret())) {
                	filterChain.doFilter(request, response);
                } else {
                    throw new InvalidSecreteTokenException(String.format(BizRedCarpetConstants.BRC_INVALID_SECRETE_TOKEN_MESSAGE, jwtToken));
                }

            } else {
                throw new UnAuthorisedUserException(BizRedCarpetConstants.BRC_UNAUTHENTICATED_USER_MESSAGE);
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        log.info("Exiting from BizRedCarpetAuthenticationFilter");
        // go to the next filter in the filter chain
        filterChain.doFilter(request, response);
		
	}
}
