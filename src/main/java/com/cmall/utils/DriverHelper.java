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
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * driver操作工具类
 * @author cm
 *
 */
public class DriverHelper {

	private Logger log = Logger.getLogger(DriverHelper.class);
	private AndroidDriver<MobileElement> driver;
	public DriverHelper() {
		
	}
	
	public DriverHelper(AndroidDriver<MobileElement> mdriver) {
		this.driver = mdriver;
	}

	public void setDriver(AndroidDriver<MobileElement> mdriver) {
		this.driver = mdriver;
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
			log.error("[ActivityNotFound]:" + "(" + activityName + ")" + "\n" + "CurrentActivity is:"
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
	public boolean waitElement(MobileElement mobileElement) {

		for (int j = 0; j < 3; j++) {
			log.info("[Element] Waiting element ==> " + "(" + splitElement(mobileElement) + ")");
			if (mobileElement.isDisplayed()) {
				log.info("[Element]" + "(" + splitElement(mobileElement) + ")" + " Found");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
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
	public void clickonElement(MobileElement mobileElement) { 
		log.info("[Element] click on element ==> " + "(" + splitElement(mobileElement) + ")");
		mobileElement.click();
	}

	/**
	 * 输入
	 * 
	 * @param element
	 * @param text
	 */
	public void sendKeys(MobileElement mobileElement, CharSequence... text) {
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


	/**
	 * 返回指定页面
	 */
	public void backToActivity(String ActivityName) {

		try {
			if (ActivityName.contains(driver.currentActivity())) {
				return;
			}

			for (int i = 0; i < 6; i++) {
				if (ActivityName.contains(driver.currentActivity())) {
					return;
				}
				Thread.sleep(2500);
				pressKeyCode(AndroidKeyCode.BACK);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 获取操作的控件字符串
	private String splitElement(MobileElement mobileElement) {
		// 用"->"分割，分成数组，取下标为1的
		// [[MyAndroidDriver: on LINUX (750e968d-5203-408c-9407-cf695a5eb436)]
		// -> id: com.tude.android:id/btn_jump]
		String str = mobileElement.toString().split("-> ")[1];
		return str.substring(0, str.length() - 1);
	}

	public void tap_StartBuilt() {

		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		System.out.println("width:" + width);
		System.out.println("height:" + height);

		if (height == 1280) {
			driver.tap(1, width / 2, height / 1280 * 1250, 500);
			return;
		}

		if (height == 1776) {
			driver.tap(1, width / 2, height / 1776 * 1710, 500);
			return;
		}

		if (height == 1920) {
			driver.tap(1, width / 2, height / 1920 * 1880, 500);
			return;
		}
	}

	public void tap_White() {

		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		if (height == 1280) {
			driver.tap(1, width / 720 * 92, height / 1280 * 850, 500);
			return;
		}

		if (height == 1920) {
			driver.tap(1, width / 100 * 13, height / 80 * 53, 500);
			return;
		}
		if (height == 1776 && width == 1080) {
			driver.tap(1, width / 1080 * 138, height / 1776 * 1126, 500);
			return;
		}
	}

	public void tap_S() {

		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		if (height == 1280) {
			driver.tap(1, width / 720 * 92, height / 1280 * 980, 500);
			return;
		}

		if (height == 1920) {
			driver.tap(1, width / 100 * 13, height / 30 * 23, 500);
			return;
		}

		if (height == 1776 && width == 1080) {
			driver.tap(1, width / 1080 * 138, height / 1776 * 1328, 500);
			return;
		}
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
	public void pause(long millis) {
		log.info("执行了等待");
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

	IDevice device;

	private IDevice getDevice() {

		AndroidDebugBridge.init(false);
		AndroidDebugBridge bridge = AndroidDebugBridge.createBridge();
		try {
			Thread.sleep(1000);
			// Calling getDevices() right after createBridge(String, boolean)
			// will generally result in an empty list.
			if (bridge.hasInitialDeviceList()) {
				IDevice devices[] = bridge.getDevices();
				if (devices.length >= 0) {
					device = devices[0];
				}
				return device;
			}
			System.out.println("没有检测到设备");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDeviceName() {

		if (device == null) {
			device = getDevice();
		}
		String brand = device.getProperty("ro.product.brand");
		String name = device.getSerialNumber();
		return brand + "_" + name;
	}

}