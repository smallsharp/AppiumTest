package com.cmall.testcase;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public interface ITestCase {
	
	/**
	 * 执行用例
	 */
	public void runCase();
	
	
	/**
	 * 用例驱动
	 * @param driver
	 */
	public void setDriver(AndroidDriver<MobileElement> driver);
	
}
