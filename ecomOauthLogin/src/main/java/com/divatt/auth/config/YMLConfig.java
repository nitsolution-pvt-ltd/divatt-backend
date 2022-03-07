package com.divatt.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YMLConfig {
	
	private String name;
	private String environment;
	private String contextpath;
	private List<String> servers = new ArrayList<>();
	
	
	
	public List<String> getServers() {
		return servers;
	}
	public void setServers(List<String> servers) {
		this.servers = servers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getContextpath() {
		return contextpath;
	}
	public void setContextpath(String contextpath) {
		this.contextpath = contextpath;
	}

}
