package com.cmall.testcase;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmall.pages.LoginPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * 登录
 * @author cm
 *
 */
public class TestCase_Login implements ITestCase {
	
	private AndroidDriver<MobileElement> driver;

	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	@Override
	public void runCase() {
		LoginPage loginPage = new LoginPage(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 20 ,TimeUnit.SECONDS), loginPage);
		loginPage.login("18521035133", "111111");
	}
}
