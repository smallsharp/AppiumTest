package com.cmall.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.cmall.utils.DDMlibUtil;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class DriverManage {
	
	private static Logger log = Logger.getLogger(DriverManage.class);
	public static List<AndroidDriver<MobileElement>> driverList = new ArrayList<AndroidDriver<MobileElement>>();
	public static List<DriverManage> dmList = new ArrayList<DriverManage>();

	private String ip;
	private int port;
	private String name;
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	static {
//		log.info("准备开始测试，正在进行测试环境初始化…");
		String ip = "127.0.0.1";
		int port = 4723;
		
		List<String> devicesName = DDMlibUtil.getInstance().getDevicesName();
		
//		log.info("检测到设备"+devicesName.size()+"台");
		
		int i = 1;
		for (String name : devicesName) {
			DriverManage dManage = new DriverManage();
			dManage.setIp(ip);
			dManage.setPort(port);
			dManage.setName(name);
//			log.info("第"+i+"台设备：" + name + ", port:" + port);
			port += 2;
			i ++;
			dmList.add(dManage);
		}
	}

	/**
	 * 初始化driver
	 */
	public static void init() {
		log.info("【第三步】：批量初始化AndroidDriver");
		List<Thread> threadList = new ArrayList<Thread>();
		for (final DriverManage dm : dmList) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					AndroidDriver<MobileElement> driver = DriverFactory.initDriver(dm.ip, dm.port,dm.name);
					log.info(dm.name+" --> "+driver);
					driverList.add(driver);
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
		
		for (AndroidDriver<MobileElement> driver : driverList) {
			driver.quit();
		}
	}

}
