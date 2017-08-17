package com.cmall.temp;

/**
 * Created by tangyao on 2017/8/7.
 */

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;


public class AppiumTest {
	
    private AndroidDriver driver;
    
    @BeforeClass
    public void setUp() throws Exception {
        //设置apk的路径
        File app = new File("apps", "ContactManager.apk");

        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "127.0.0.1:62001");
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath());
        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.example.android.contactmanager");
        capabilities.setCapability("appActivity", ".ContactManager");
        //初始化
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void addContact(){
        System.out.println(driver);
        WebElement el=driver.findElementByAccessibilityId("Add Contact");
        System.out.println(el);
        el.click();

    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}

