package com.bizongo.redCarpet.filter.logging;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class BizRedCarpetLoggingFilterConfiguration {
	
	@Bean
	public GlobalFilter preGlobalFilter() {
		return (exchange, chain) -> {
			return chain.filter(exchange)
					.then(Mono.fromRunnable(() -> {
						log.info("Welcome to Red Carpet. Current Request is : " + exchange.toString());
					}));
		};
	}
	
	@Bean
	public GlobalFilter postGlobalFilter() {
		return (exchange, chain) -> {
			return chain.filter(exchange)
					.then(Mono.fromRunnable(()-> {
						log.info("Exiting to Red Carpet. Current Request is : " + exchange.toString());
					}));
		};
	}
}
