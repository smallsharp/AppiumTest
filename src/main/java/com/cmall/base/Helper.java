package com.cmall.base;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * driver操作工具类
 * Po模式下用到较多
 * @author cm
 *
 */
public class Helper {

	private static Logger log = Logger.getLogger(Helper.class);
	
	/**
	 * 等待Activity指定的Activity出现，等待时间5秒钟
	 * @param driver
	 * @param activity
	 * @return
	 */
	public static boolean waitActivity(AndroidDriver<MobileElement> driver,String activity) {
		
		for (int i = 0; i < 10; i++) {
			String currentActivity = driver.currentActivity();
			if (i%2 == 0) {
				log.info("wait activity=["+ activity +"],current= [" + currentActivity + "]");
			}
			if (currentActivity.equals(activity)) {
				log.info("[Activity] Found activity ==> " + "(" + activity + ")");
				return true;
			} else {
				pause(500);
			}
		}
		
		return false;
	}

	/**
	 * 手动等待元素出现
	 * @param mobileElement
	 * @return
	 */
	public static boolean waitElement(MobileElement mobileElement) {

		for (int j = 0; j < 20; j++) {
			if (j%2 == 0) {
				log.info("[Element] Waiting element ==> " + "(" + splitElement(mobileElement) + ")");
			}
			
			if (mobileElement.isDisplayed()) {
				log.info("[Element]" + "(" + splitElement(mobileElement) + ")" + " Found");
				return true;
			}
			pause(1000);
		}
		log.error("[Element] ElementNotFound ==> " + "(" + splitElement(mobileElement) + ")");
		return false;
	}

	/**
	 * 点击
	 * @param element
	 */
	public static void clickElement(MobileElement mobileElement) { 
		log.info("[Click] ==> " + "(" + splitElement(mobileElement) + ")");
		mobileElement.click();
	}

	/**
	 * 输入
	 * 
	 * @param element
	 * @param text
	 */
	public static void sendKeys(MobileElement mobileElement, CharSequence... text) {
		log.info("[Input] ==> " + "(" + splitElement(mobileElement) + ")");
		mobileElement.sendKeys(text);
	}

	/**
	 * 点击系统按键
	 * 
	 * @param androidkeycode
	 */
	public void pressKeyCode(AndroidDriver<MobileElement> driver,int androidkeycode) {
		log.info("[Press AndroidKeyCode] ==> " + androidkeycode);
		driver.pressKeyCode(androidkeycode);
	}
	
	
	public String getCurrentActivity(AndroidDriver<MobileElement> driver) {
		String current  = driver.currentActivity();
		log.info("Current Activity:"+ current);
		return current;
	}

	/***
	 * 切换WEB页面查找元素WEBVIEW、 NATIVE_APP
	 */
	public static void contextWebview(AndroidDriver<MobileElement> driver) {

		Set<String> ContextHandles = driver.getContextHandles();
		log.info("All ContextHandles :" + ContextHandles);
		for (String contextName : ContextHandles) {
			if (contextName.contains("WEBVIEW") || contextName.contains("webview")) {
				driver.context(contextName);
				log.info("[Webview] context_to_webview success :" + contextName);
				break;
			}
		}
	}

	public static void contextNative(AndroidDriver<MobileElement> driver) {
		driver.context("NATIVE_APP");
	}

	/***
	 * 检查网络
	 * 
	 * @return 是否正常
	 */
	public static boolean checkNet(AndroidDriver<MobileElement> driver) {
		String text = driver.getConnection().toString();
		if (text.contains("Data: true"))
			return true;
		else
			return false;
	}

	/***
	 * 根据UIautomator底层方法得到对应desc的view
	 * 
	 * @param desc名
	 * @return View
	 */
	public MobileElement findElementByDesc(AndroidDriver<MobileElement> driver,String name) {
		return driver.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\"" + name + "\")");
	}


	/**
	 * 截图，将图片放在test-output\\screenshots中
	 * 
	 * @param screenShotName
	 */
	public void takeScreenShot(AndroidDriver<MobileElement> driver,String screenShotName) {

		String path = "test-output\\screenshots\\";
		File screenShot = driver.getScreenshotAs(OutputType.FILE);

		try {
			File destFile = new File(path + screenShotName);
			FileUtils.copyFile(screenShot, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("截图成功：" + screenShotName);
	}


	/**
	 * 滑动到指定元素高度并点击，需要在webview下执行
	 * 
	 * @param element
	 */
	public static void scrollClick(AndroidDriver<MobileElement> driver,MobileElement element) {
		int elementPosition = element.getLocation().getY();
		String js = String.format("window.scroll(0, %s)", elementPosition);
		((JavascriptExecutor) driver).executeScript(js);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		element.click();
	}

	/**
	 * 滑动到指定元素高度，需要在webview下执行
	 * 
	 * @param element
	 */
	public static void scrollToElement(AndroidDriver<MobileElement> driver,MobileElement mobileElement) {

		int elementPosition = mobileElement.getLocation().getY();
		String js = String.format("window.scroll(0, %s)", elementPosition);
		driver.executeScript(js);
	}

	// 获取操作的控件字符串
	private static String splitElement(MobileElement mobileElement) {
		// 用"->"分割，分成数组，取下标为1的
		// [[MyAndroidDriver: on LINUX (750e968d-5203-408c-9407-cf695a5eb436)]-> id: com.tude.android:id/btn_jump]
		String str = mobileElement.toString().split("-> ")[1];
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 等待
	 * 
	 * @param millis
	 */
	public static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}