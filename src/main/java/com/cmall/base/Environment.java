package com.cmall.base;

import java.util.ArrayList;
import java.util.List;

import com.spring.constant.IServerArgs;

/**
 * 配置信息类
 * 主要作用：
 * 用于存放取得的config信息，给Server、driver初始化使用
 * @author cm
 *
 */
public class Environment {
	
	private static List<Config> configs = new ArrayList<>();
	private static List<String> devicesName = DDMlibUtil.getInstance().getDevicesName();
	private static final String KILL_NODE = "taskkill /F /im node.exe";
	
	public static List<Config> getConfigs() {
		return Environment.configs;
	}
	
	static {
		try {
			addConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addConfig() throws Exception {
		
		if (devicesName.size() <= 0) {
			throw new Exception("没有检测到可用的连接设备");
		}
		Runtime.getRuntime().exec(KILL_NODE);
		Thread.sleep(500);
		String ip = IServerArgs.IP;
		int port = 4723;
		for (String name : devicesName) {
			Config config = new Config();
			config.setIp(ip);
			config.setPort(port);
			config.setName(name);
			configs.add(config);
			port += 2;
		}
	}
}