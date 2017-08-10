package com.cmall.testcase;

import org.apache.log4j.Logger;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase02 implements ITestCase{
	
	private AndroidDriver<MobileElement> driver;
	private Logger log = Logger.getLogger(TestCase02.class);
	
	public void runCase() {
		log.info("开始执行测试用例--02 -->"+ driver);
		log.info(driver.currentActivity() +" "+ driver);
	}

	@Override
	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}

}
