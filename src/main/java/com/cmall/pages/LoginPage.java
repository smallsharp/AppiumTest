package com.cmall.pages;

import org.openqa.selenium.support.FindBy;
import com.cmall.base.Helper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * 简单的自动化脚本-登录页面-po模式的简单运用
 * 
 * 业务逻辑中，如果需要用到driver，可以定义一个构造方法，将driver传进来
 * 注意：默认的无参构造方法，必须要存在，否则调用页面的时候，反射会报错。
 * @author lee
 */
public class LoginPage {

	/**
	 * 我的 按钮
	 */
	@AndroidFindBy(id = "com.play.android:id/btn_profile")
	private MobileElement btn_profile; // 我的
	
	/**
	 * 账号密码 登录
	 */
	@FindBy(id = "com.play.android:id/tv_account")
	private MobileElement tv_account; // 账号密码登录

	/**
	 * 手机号 输入框
	 */
	@FindBy(id = "com.play.android:id/et_account")
	private MobileElement et_account; // 手机号

	/**
	 * 密码 输入框
	 */
	@FindBy(id = "com.play.android:id/et_password")
	private MobileElement e_password; // 密码
	
	/**
	 * 登录 按钮
	 */
	@FindBy(id = "com.play.android:id/btn_login")
	private MobileElement btn_login; // 登录

	/**
	 * 通过：手机号，密码登录
	 * 
	 * @param mobile
	 * @param password
	 */
	public void login(String mobile, String password) {
		Helper.clickElement(btn_profile);
		Helper.clickElement(tv_account);
		Helper.sendKeys(et_account, mobile);
		Helper.sendKeys(e_password, password);
		Helper.clickElement(btn_login);
	}
}
