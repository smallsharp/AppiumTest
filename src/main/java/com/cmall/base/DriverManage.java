package com.cmall.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.cmall.utils.DDMlibUtil;
import com.spring.constant.IServerArgs;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class DriverManage {
	
	private static Logger log = Logger.getLogger(DriverManage.class);
	private static List<AndroidDriver<MobileElement>> driversList = new ArrayList<AndroidDriver<MobileElement>>();
	private static List<Config> serverConfigList = new ArrayList<>();
	private static List<String> devicesName = DDMlibUtil.getInstance().getDevicesName();
	
	public static List<AndroidDriver<MobileElement>> getDriverList(){
		return DriverManage.driversList;
	}

	static {
		String ip = IServerArgs.IP;
		int port = 4723;
		for (String name : devicesName) {
			Config config = new Config();
			config.setIp(ip);
			config.setPort(port);
			config.setName(name);
			port += 2;
			serverConfigList.add(config);
		}
	}

	/**
	 * 初始化driver
	 */
	public static void initDriver() {
		log.info("[驱动准备阶段] ==> 准备初始化");
		List<Thread> threadList = new ArrayList<Thread>();
		for (final Config config : serverConfigList) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// 分别初始化driver实例
					AndroidDriver<MobileElement> driver = DriverFactory.initDriver(config.getIp(), config.getPort(),config.getName());
					driversList.add(driver);
				}
			});
			thread.start();
			threadList.add(thread);
		}
		// 等待所有设备初始化完成
		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("所有设备初始化完成");
	}
	
	/**
	 * 结束测试
	 */
	public static void finish() {
		for (AndroidDriver<MobileElement> driver : driversList) {
			driver.quit();
		}
	}

}
