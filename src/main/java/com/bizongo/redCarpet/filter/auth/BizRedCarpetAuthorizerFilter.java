package com.bizongo.redCarpet.filter.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.bizongo.redCarpet.constant.BizRedCarpetConstants;
import com.bizongo.redCarpet.filter.exception.UnAuthorisedUserException;
import com.bizongo.redCarpet.model.UserAccessInfoDtls;
//import com.bizongo.redCarpet.security.config.RedisHashComponent;
import com.bizongo.redCarpet.redis.component.RedisHashComponent;

import reactor.core.publisher.Mono;

@Component
public class BizRedCarpetAuthorizerFilter implements GlobalFilter, Ordered {

	private final Logger log = LoggerFactory.getLogger(BizRedCarpetAuthorizerFilter.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired 
	private RedisHashComponent redisHashComponent;
	
	@Autowired
	private BizRedCarperAuthorizerFilterHelper helper;
	 
	private String getUri(String appId) {
		
		return discoveryClient.getInstances(appId).get(0).getUri().toString();
	}

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info("Executing authorizer filter from red carpet.");
		
		ServerHttpRequest request = exchange.getRequest();
		
		String userId = String.valueOf(request.getQueryParams().getFirst(BizRedCarpetConstants.BRC_USER_ID));
		
		log.info("Got userId : " + userId);
		
		UserAccessInfoDtls userAccessInfoDtls = redisHashComponent.get(userId);
		if(userAccessInfoDtls == null) {
			log.info("Calling ACL to get previliges of user : " + userId);

			ResponseEntity<UserAccessInfoDtls> resp = restTemplate.exchange(
					getUri("ACCESS-CONTROL")+"/acl/{userId}", 
					HttpMethod.GET, null, 
					new ParameterizedTypeReference<UserAccessInfoDtls>() {}, 
					(Object) userId);
			
			userAccessInfoDtls = resp.getBody();
			log.info("Got response from ACL: \n" + userAccessInfoDtls);     
			redisHashComponent.set(String.valueOf(userAccessInfoDtls.getUserId()), userAccessInfoDtls);
		}
		try {
			helper.checkAuthorisation(request, userAccessInfoDtls);
		}
		catch(UnAuthorisedUserException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
					String.format(BizRedCarpetConstants.BRC_UNAOTHORIZED_USER_MESSAGE, userId),e);
		}
		return chain.filter(exchange);
	}
}
