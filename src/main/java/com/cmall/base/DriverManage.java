package com.cmall.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * driver管理类
 * 主要作用：
 * 根据EnvironmentCheck.getConfigs()获取的配置信息，批量初始化driver
 * 并将driver放到drivers中管理，以备后用
 * 
 * @author cm
 *
 */

public class DriverManage {
	
	private static Logger log = Logger.getLogger(DriverManage.class);
	private static List<Config> configs = Environment.getConfigs();
	private static List<AndroidDriver<MobileElement>> drivers = new ArrayList<AndroidDriver<MobileElement>>();

	/**
	 * 
	 * @return
	 */
	public static List<AndroidDriver<MobileElement>> getDriverList(){
		return DriverManage.drivers;
	}

	/**
	 * 初始化driver
	 */
	public static void initDriver() {
		log.info("[驱动准备阶段] ==> 准备初始化");
		List<Thread> threadList = new ArrayList<Thread>();
		for (final Config config : configs) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// 分别初始化driver实例
					AndroidDriver<MobileElement> driver = DriverFactory.initDriver(config.getIp(), config.getPort(),config.getName());
					drivers.add(driver);
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
	 * 销毁driver
	 */
	public static void finish() {
		for (AndroidDriver<MobileElement> driver : drivers) {
			driver.quit();
		}
	}

}
