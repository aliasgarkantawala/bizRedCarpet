package com.bizongo.redCarpet.filter.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalPostFilter implements GlobalFilter , Ordered {
	
	private final Logger log = LoggerFactory.getLogger(LoggingGlobalPostFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Exiting to Red Carpet. Current Request is : " + exchange.getRequest().toString());

        return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 4;
	}
}
