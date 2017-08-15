package com.cmall.testcase;

import static org.testng.Assert.assertTrue;

import java.util.List;

import com.spring.constant.IDFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase_CheckModel implements ITestCase {
	
	private AndroidDriver<MobileElement> driver;

	@Override
	public void runCase() {
		
		List<MobileElement> list = driver.findElementsById(IDFactory.SDV_IMAGE);
		if (list.size() < 1) {
			assertTrue(false, "首页产品List，没有加载成功");
		}
		list.get(1).click();
		MobileElement m_tee_native = driver.findElementById(IDFactory.NAVIVE_TEE);
		int x = m_tee_native.getLocation().getX();
		int y = m_tee_native.getLocation().getY();
		int height = m_tee_native.getSize().getHeight();
		driver.tap(1, x / 2, height / 6 + y, 500);
		
		List<MobileElement> m_iv_goods = driver.findElementsById(IDFactory.IV_GOOD);
		int goods_length = m_iv_goods.size();
		
		for (int i = 0; i < goods_length; i++) {
			m_iv_goods.get(i).click();
		}
		

		
	}

	@Override
	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}

}
