package com.cmall.cases;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * 测试用例
 * 用例的执行，需要driver驱动，一个用例对应一个driver（可以理解为一个设备）
 * 所以，多设备同时进行时，要分别分配driver，即用例和driver是一一对应的关系。
 * @author cm
 *
 */
public interface ITestCase {
	
	/**
	 * 用例驱动
	 * @param driver
	 */
	public void setDriver(AndroidDriver<MobileElement> driver);
	
	/**
	 * 执行用例
	 */
	public void runCase();	
	
}
