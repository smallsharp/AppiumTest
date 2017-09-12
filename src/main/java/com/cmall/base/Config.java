package com.cmall.base;

import java.util.List;

/**
 * 初始化Server和Driver时，需要的类
 * @author cm
 *
 */
public class Config {
	
	private String ip;
	private int port;
	private String name;
	private List<Config> configs;
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Config> getConfigs() {
		return configs;
	}

	public void setConfigs(List<Config> configs) {
		this.configs = configs;
	}
	
	
}
