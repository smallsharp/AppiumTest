package com.cmall.cases;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.cmall.base.ITestCase;
import com.cmall.pages.LoginPage;
import com.spring.constant.IDFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * 登录
 * @author cm
 *
 */
public class Login implements ITestCase {
	
	private AndroidDriver<MobileElement> driver;

	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	@Override
	public void runCase() {
/*		LoginPage loginPage = new LoginPage(driver);
		FieldDecorator decorator = new AppiumFieldDecorator(driver, 20, TimeUnit.SECONDS);
		PageFactory.initElements(decorator, loginPage);
		loginPage.login("18521035133", "111111");*/
		login("18521035133", "111111");
	}
	
	/**
	 * 登录
	 * @param account 账号
	 * @param password 密码
	 */
	private void login(String account,String password) {
		driver.findElementById(IDFactory.BTN_PROFILE).click();
		driver.findElementById(IDFactory.TV_ACCOUNT).click();
		driver.findElementById(IDFactory.ET_ACCOUNT).sendKeys(account);
		driver.findElementById(IDFactory.ET_PASSWORD).sendKeys(password);
		driver.findElementById(IDFactory.BTN_LOGIN).click();
	}
}
