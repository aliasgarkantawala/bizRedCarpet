package com.bizongo.redCarpet.filter.auth.config;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppConfig {
	
	private static final Object reloadLock = new Object();
    private static final AtomicInteger reloadCount = new AtomicInteger();

    @Autowired
	private RestTemplate restTemplate;
    
    private AtomicReference<DbSettings> dbSettingsRef = new AtomicReference<DbSettings>();
    
    private HashMap<String, HashMap<String, String>> theSettings;
    
    @Autowired
	private DiscoveryClient discoveryClient;
	
	private String getUri(String appId) {
		
		return discoveryClient.getInstances(appId).get(0).getUri().toString();
	}
	
    public DbSettings getDbSettings() {

        DbSettings dbs = dbSettingsRef.get();
        if (dbs == null) {
            dbs = reload();
        }
        return dbs;
    }
    
    public String getValueSettings(String groupId, String keyName) {
    	return this.getDbSettings().getValueSettings(groupId, keyName);
    }

    public DbSettings reload() {

        DbSettings dbs = null;
        int rld = reloadCount.get();
        synchronized (reloadLock) {
            if (rld < reloadCount.get()) {
                // Reload was already done
                dbs = dbSettingsRef.get();
            } else {
                reloadCount.incrementAndGet();
                dbs = new DbSettings();
                dbs.load();
                dbSettingsRef.set(dbs);
            }
        }
        return dbs;
    }
    
    class DbSettings {

        @SuppressWarnings({ "unchecked", "rawtypes" })
		public void load() {

            ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(getUri("ACCESS-CONTROL")+"/configuration/", HashMap.class);
            
            theSettings = responseEntity.getBody();

            
        }

        public String getValueSettings(String groupId, String keyName) {

            String value = null;
            
            HashMap<String, String> group = theSettings.get(groupId);
            
            if (group != null && !group.isEmpty()) {
                value = group.get(keyName);
            }
            return value;
        }
    }
}
