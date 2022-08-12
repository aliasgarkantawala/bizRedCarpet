package com.bizongo.redCarpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizongo.redCarpet.filter.auth.config.AppConfig;

@RestController
@RequestMapping("/refreshConfig")
public class ConfigController {

	@Autowired
	private AppConfig appConfig;
	
	@PostMapping
	public void refreshConfig() {
		appConfig.reload();
	}
}
