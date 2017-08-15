package com.cmall.testcase;

import org.apache.log4j.Logger;

import com.spring.constant.IDFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase_Logout implements ITestCase{
	
	private AndroidDriver<MobileElement> driver;
	private Logger log = Logger.getLogger(TestCase_Logout.class);
	
	@Override
	public void runCase() {
		log.info("开始执行测试用例--02 -->"+ driver);
		log.info(driver.currentActivity() +" "+ driver);
		driver.findElementById(IDFactory.BTN_PROFILE).click();
		driver.findElementById(IDFactory.BTN_LOGOUT).click();
		driver.findElementById(IDFactory.BTN_QUIT).click();
		log.info(driver.findElementById(IDFactory.BTN_DIY).isEnabled());
	}

	@Override
	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}

}
