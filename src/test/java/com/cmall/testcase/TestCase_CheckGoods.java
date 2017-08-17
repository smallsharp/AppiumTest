package com.cmall.testcase;

import static org.testng.Assert.assertTrue;
import java.util.List;
import org.apache.log4j.Logger;
import com.cmall.utils.Helper;
import com.spring.constant.IActivities;
import com.spring.constant.IDFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase_CheckGoods implements ITestCase {

	private AndroidDriver<MobileElement> driver;
	private Logger log = Logger.getLogger(TestCase_CheckGoods.class);

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
		
		if (!driver.currentActivity().equals(IActivities.GOODS_WEB3DVIEW_ACTIVITY)) {
			this.gotoModel();
		}
		// 模型显示界面
		List<MobileElement> m_iv_goods = driver.findElementsById(IDFactory.IV_GOOD);
		int goods_length = m_iv_goods.size();
		log.info("goods_length:" + goods_length);

		for (int i = 0; i < goods_length; i++) {
			m_iv_goods.get(i).click();
			if (driver.findElementById(IDFactory.NAVTIVE_MODEL).isDisplayed()) {
				assertTrue(true, "model is not displayed");
			}
		}
	}

	/**
	 * 从首页进入模型页面
	 */
	private void gotoModel() {
		
		List<MobileElement> homeList = driver.findElementsById(IDFactory.SDV_IMAGE);

		if (homeList.size() < 1) {
			assertTrue(false, "首页产品List，没有加载成功");
		}
		// 点击 一级目录 男装,进入二级目录，如果由于某种原因导致未打开，则重新点击，直至打开页面
		do {
			homeList.get(1).click();
		} while (!Helper.waitActivity(driver, IActivities.PRODUCT_CLASSIFITION_ACTIVITY));
		
		MobileElement m_tee_native = driver.findElementById(IDFactory.NAVIVE_TEE);
		int x = m_tee_native.getLocation().getX();
		int y = m_tee_native.getLocation().getY();
		int height = m_tee_native.getSize().getHeight();
		
		// 点击二级目录 Tee
		do {
			driver.tap(1, x / 2, height / 6 + y, 500);
			log.info("点击：TEE");
		} while (!Helper.waitActivity(driver, ".activity.member.LoginActivity"));

		// 跳转登录界面
		log.info("进入登录界面");
		driver.findElementById(IDFactory.TV_ACCOUNT).click();
		driver.findElementById(IDFactory.ET_ACCOUNT).sendKeys("18521035133");
		driver.findElementById(IDFactory.ET_PASSWORD).sendKeys("111111");
		driver.findElementById(IDFactory.BTN_LOGIN).click();

		// 登录成功应该返回前置页面，没有则失败
		if (!Helper.waitActivity(driver, IActivities.PRODUCT_CLASSIFITION_ACTIVITY)) {
			assertTrue(false, "登录后，没有回到二级目录页面！");
		}
		// 点击二级目录 Tee
		do {
			driver.tap(1, x / 2, height / 6 + y, 500);
		} while (!Helper.waitActivity(driver, IActivities.GOODS_WEB3DVIEW_ACTIVITY));
		
		log.info("进入模型界面");
		if (!Helper.waitElement(driver.findElementById(IDFactory.NAVTIVE_MODEL))) {
			assertTrue(false, "模型没有加载完成");
		}
	}

}
