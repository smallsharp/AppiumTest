package com.cmall.testcase;

import org.apache.log4j.Logger;
import com.spring.constant.IDFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * 登录
 * @author cm
 *
 */
public class TestCase_Login implements ITestCase{
	
	private AndroidDriver<MobileElement> driver;
	private Logger log = Logger.getLogger(TestCase_Login.class);

	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	@Override
	public void runCase() {
		log.info("开始测试 --> " + driver);
		try {
			driver.findElementById(IDFactory.BTN_PROFILE).click();
			driver.findElementById(IDFactory.TV_ACCOUNT).click();
			driver.findElementById(IDFactory.ET_ACCOUNT).sendKeys("18521035133");
			driver.findElementById(IDFactory.ET_PASSWORD).sendKeys("111111");
			driver.findElementById(IDFactory.BTN_LOGIN).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("结束测试--> " + driver);
	}

}
