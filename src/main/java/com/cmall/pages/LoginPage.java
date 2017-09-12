package com.cmall.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.FindBy;

import com.cmall.base.Helper;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * 简单的自动化脚本-登录页面-po模式的简单运用
 * 
 * @author lee
 */
public class LoginPage {

	private static AndroidDriver<MobileElement> driver;
	private Logger log = Logger.getLogger(LoginPage.class);

	//init需要添加，反射时需要
	public LoginPage() {
	}

	public LoginPage(AndroidDriver<MobileElement> driver) {
		LoginPage.driver = driver;
	}

	@AndroidFindBy(id = "com.play.android:id/btn_profile")
	private MobileElement btn_profile; // 我的

	@FindBy(id = "com.play.android:id/tv_account")
	private MobileElement tv_account; // 账号密码登录

	@FindBy(id = "com.play.android:id/et_account")
	private MobileElement et_account; // 手机号

	@FindBy(id = "com.play.android:id/et_password")
	private MobileElement e_password; // 密码

	@FindBy(id = "com.play.android:id/btn_login")
	private MobileElement btn_login; // 登录

	@FindBy(id = "com.play.android:id/tv_sign_in")
	private MobileElement tv_sign_in;

	@FindBy(id = "com.play.android:id/cb_confrim_deal")
	private MobileElement cb_confrim;

	/**
	 * 通过：手机号，密码登录
	 * 
	 * @param mobile
	 * @param password
	 */
	public void login(String mobile, String password) {
		
		Helper.clickonElement(btn_profile);
		Helper.clickonElement(tv_account);
		Helper.sendKeys(et_account, mobile);
		Helper.sendKeys(e_password, password);
		Helper.clickonElement(btn_login);
		
	}

}
