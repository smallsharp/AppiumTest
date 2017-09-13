package com.cmall.cases;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;

import com.cmall.pages.LoginPage;
import com.spring.constant.IDFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * 登录，可以使用po模式，也可以使用普通模式
 * @author cm
 *
 */
public class Login implements ITestCase {
	
	private AndroidDriver<MobileElement> driver;

	@Override
	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	@Override
	public void runCase() {
		LoginPage loginPage = new LoginPage();
		PageFactory.initElements(new AppiumFieldDecorator(driver, 20, TimeUnit.SECONDS), loginPage);
		loginPage.login("18521035133", "111111");
	}
	
	/**
	 * 登录
	 * @param account 账号
	 * @param password 密码
	 */
	@SuppressWarnings("unused")
	private void login(String account,String password) {
		driver.findElementById(IDFactory.BTN_PROFILE).click();
		driver.findElementById(IDFactory.TV_ACCOUNT).click();
		driver.findElementById(IDFactory.ET_ACCOUNT).sendKeys(account);
		driver.findElementById(IDFactory.ET_PASSWORD).sendKeys(password);
		driver.findElementById(IDFactory.BTN_LOGIN).click();
	}
}
