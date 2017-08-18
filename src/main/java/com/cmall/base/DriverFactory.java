package com.cmall.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * driver
 * @author cm
 *
 */
public class DriverFactory {
	
	private static Logger log = Logger.getLogger(DriverFactory.class);
	private AndroidDriver<MobileElement> driver;
	private String apkPath;
	private String platformName;
	private String deviceName;
	private String appPackage;
	private String appActivity;
	private String ip;
	private int port;

	// #{driverBean.mdriver} 引用getter方法	
	public AndroidDriver<MobileElement> getDriver() {
		return driver;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public void setDriver(AndroidDriver<MobileElement> mdriver) {
		this.driver = mdriver;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	public void setAppActivity(String appActivity) {
		this.appActivity = appActivity;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "[apkPath=" + apkPath + ", platformName=" + platformName + ", deviceName=" + deviceName
				+ ", appPackage=" + appPackage + ", appActivity=" + appActivity + ", ip=" + ip + ", port=" + port + "]";
	}

	public AndroidDriver<MobileElement> initDriver() {
		
		System.out.println("------------ init begin ---------------");
		System.out.println(this.toString());
		
		Map<String, String> map = new HashMap<>();
		File app = new File(apkPath); // 指定app的存放目录
		map.put("app", app.getAbsolutePath());
		map.put("platformName", platformName);
		map.put("deviceName", deviceName);
		map.put("appPackage", appPackage);
		map.put("appActivity", appActivity);

		DesiredCapabilities dct = new DesiredCapabilities(map);
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://" +ip+ ":" + port +"/wd/hub"), dct);
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("------------ init ok ---------------");

		return driver;
	}
	
	
	/**
	 * 初始化AndroidDriver
	 * @param ip
	 * @param port
	 * @param deviceName
	 * @return
	 */
	public static AndroidDriver<MobileElement> initDriver(String ip, int port, String deviceName) {

		log.info("init driver with: [ip= " + ip +" ,deviceName= "+ deviceName + " ,port= " + port + "]");
		
		DesiredCapabilities dc = new DesiredCapabilities();
		File app = new File("apps/play-debug.apk"); // 指定app的存放目录
		dc.setCapability("app", app.getAbsolutePath());
		dc.setCapability("unicodeKeyboard", true); // 支持中文输入
		dc.setCapability("platformName", "Android");
		dc.setCapability("deviceName", deviceName);
		dc.setCapability("appPackage", "com.play.android");
		dc.setCapability("appActivity", "com.play.android.activity.SplashActivity");
		AndroidDriver<MobileElement> mdriver = null;
		try {
			String url = "http://" + ip + ":" + port + "/wd/hub";
			mdriver = new AndroidDriver<MobileElement>(new URL(url), dc);
			mdriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return mdriver;
	}

}
