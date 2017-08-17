package com.cmall.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java_cup.runtime.virtual_parse_stack;

/**
 * driver操作工具类
 * @author cm
 *
 */
public class Helper {

	private static Logger log = Logger.getLogger(Helper.class);
	private AndroidDriver<MobileElement> driver;
	
	public Helper(AndroidDriver<MobileElement> mdriver) {
		this.driver = mdriver;
	}
	
	
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
				log.info("wait activity=["+ activity +"],current=["+currentActivity+"]");
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
	 * 
	 * @Description 动态等待activity出现
	 * @Data 2017年5月3日
	 * @return true or false
	 */
	public boolean waitActivity(String activityName) {

		log.info("[Activity] Waiting activity ==> " + "(" + activityName + ")");
		try {
			for (int i = 0; i < 20; i++) {
				Thread.sleep(500);
				if (activityName.contains(driver.currentActivity())) {
					log.info("[Activity] Found activity ==> " + "(" + activityName + ")");
					return true;
				}
			}
			log.error("[ActivityNotFound]:" + "(" + activityName + ")" + "\n" + "currentActivity is:"
					+ "(" + driver.currentActivity() + ")");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
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
	 * 
	 * @param activityName
	 * @param mobileElement
	 * 
	 */
	public boolean waitElement(String activityName, MobileElement mobileElement) {

		if (waitActivity(activityName)) {
			if (waitElement(mobileElement)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 点击
	 * @param element
	 */
	public static void clickonElement(MobileElement mobileElement) { 
		log.info("[Element] click on element ==> " + "(" + splitElement(mobileElement) + ")");
		mobileElement.click();
	}

	/**
	 * 输入
	 * 
	 * @param element
	 * @param text
	 */
	public static void sendKeys(MobileElement mobileElement, CharSequence... text) {
		log.info("[Element] input text ==> " + "(" + splitElement(mobileElement) + ")");
		mobileElement.sendKeys(text);
	}

	/**
	 * 点击系统按键
	 * 
	 * @param androidkeycode
	 */
	public void pressKeyCode(int androidkeycode) {
		log.info("Press AndroidKeyCode ==> " + androidkeycode);
		driver.pressKeyCode(androidkeycode);
	}
	
	
	public String getCurrentActivity() {
		String current  = driver.currentActivity();
		log.info("Current Activity:"+current);
		return current;
	}

	/***
	 * 切换WEB页面查找元素WEBVIEW、 NATIVE_APP
	 */
	public void context_to_webview() {

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

	public void context_to_native() {
		driver.context("NATIVE_APP");
	}

	/***
	 * 检查网络
	 * 
	 * @return 是否正常
	 */
	public boolean checkNet() {
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
	public MobileElement findElementByDesc(String name) {
		return driver.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\"" + name + "\")");
	}
	
	
	public List<MobileElement> findElementsById(String id) {
		return driver.findElementsById(id);
	}
	
	public MobileElement findElementById(String id) {
		return driver.findElementById(id);
	}
	
	public MobileElement findElementByName(String name) {
		return driver.findElementByName(name);
	}


	/***
	 * 根据UIautomator底层方法得到对应text的view
	 * 
	 * @param text名
	 * @return View
	 */
	public MobileElement findElementByText(String name) {
		return driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + name + "\")");
	}

	/**
	 * 截图，将图片放在test-output\\screenshots中
	 * 
	 * @param screenShotName
	 */
	public void takeScreenShot(String screenShotName) {

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

	/***
	 * 特殊左滑
	 * 
	 * @param 传入从上到下宽度的百分比(1-99之间)
	 */
	public void slideLeft(int i) {
		Assert.assertFalse(i <= 0 || i >= 100, "左滑宽度传入错误");
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		driver.swipe(x / 4 * 3, y / 10 * i, x / 4 * 2, y / 10 * i, 0);
	}

	/**
	 * 向上滑动1/3屏幕高度
	 */
	public void swipeUp() {

		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height * 2 / 3, width / 2, height * 1 / 3, 1000);
		pause(1000);
	}

	/**
	 * 滑动到指定元素高度并点击，需要在webview下执行
	 * 
	 * @param element
	 */
	public void scrollAndClick(MobileElement element) {
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
	public void scrollToElement(MobileElement mobileElement) {

		int elementPosition = mobileElement.getLocation().getY();
		String js = String.format("window.scroll(0, %s)", elementPosition);
		driver.executeScript(js);
	}

	// 获取操作的控件字符串
	private static String splitElement(MobileElement mobileElement) {
		// 用"->"分割，分成数组，取下标为1的
		// [[MyAndroidDriver: on LINUX (750e968d-5203-408c-9407-cf695a5eb436)]
		// -> id: com.tude.android:id/btn_jump]
		String str = mobileElement.toString().split("-> ")[1];
		return str.substring(0, str.length() - 1);
	}

	public boolean isPageLoaded() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String status = (String) jsExecutor.executeScript("var status=document.readyState;return status");
		if (status.contains("complete")) {
			return true;
		}

		int i = 0;
		while (!status.contains("complete")) {
			i++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (i > 10) {
				return false;
			}
		}
		return false;
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

	/**
	 * 等待
	 * 
	 * @param millis
	 */
	public void pause_default_time() {
		pause(2000);
	}
	
	public void tap(int fingers, int x, int y, int duration) {
		driver.tap(fingers, x, y, duration);
	}
	
	public void swipe(int startx, int starty, int endx, int endy, int duration) {
		driver.swipe(startx, starty, endx, endy, duration);
	}

	public int getDeviceWidth() {
		int width = driver.manage().window().getSize().width;
		return width;
	}

	public int getDeviceHeight() {
		int height = driver.manage().window().getSize().height;
		return height;
	}


}