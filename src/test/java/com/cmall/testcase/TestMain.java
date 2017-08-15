package com.cmall.testcase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.cmall.utils.AppiumServerManage;
import com.cmall.utils.DriverManage;
import com.cmall.utils.Execute;

public class TestMain {
	
	@BeforeSuite
	public void init() {
		AppiumServerManage serverManage = new AppiumServerManage();
		serverManage.startServer();
		DriverManage.init();
	}
	
	@AfterSuite
	public void finish() {
		DriverManage.finish();
	}
	
	@Test(priority=10)
	public void test2() {
		Execute.runTestCase(TestCase_Login.class);
	}
	
	@Test(priority=20)
	public void test3() {
		Execute.runTestCase(TestCase_Logout.class);
	}
	
	

}
