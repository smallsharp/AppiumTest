package com.cmall.cases;

import com.spring.constant.IDFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class EditModel implements ITestCase {

	private AndroidDriver<MobileElement> driver;

	@Override
	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}

	@Override
	public void runCase() {
		this.showAllGoods();
	}

	/**
	 * 遍历首屏的所有商品
	 */
	private void showAllGoods() {
		driver.findElementById(IDFactory.NAVTIVE_MODEL).click();
	}

}
