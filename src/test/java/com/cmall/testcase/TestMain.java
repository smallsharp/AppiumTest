package com.cmall.testcase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.cmall.utils.AppiumServerManage;
import com.cmall.utils.DriverManage;
import com.cmall.utils.Execute;

public class TestMain {
	
	AppiumServerManage serverManage = new AppiumServerManage();
	
	@BeforeSuite
	public void init() {
		serverManage.startServer();
		DriverManage.init();
	}
	
	@Test(priority=0)
	public void testGotoModel() {
		Execute.runTestCase(TestCase_GotoModel.class);
	}
	
	@Test(priority=10)
	public void testCheckModel() {
		Execute.runTestCase(TestCase_CheckModel.class);
	}
	
	@Test(priority=20)
	public void testCheckGoods() {
		Execute.runTestCase(TestCase_CheckGoods.class);
	}
	
/*	@Test(priority=10)
	public void test2() {
		Execute.runTestCase(TestCase_Login.class);
	}
	
	@Test(priority=20)
	public void test3() {
		Execute.runTestCase(TestCase_Logout.class);
	}*/
	
	
	@AfterSuite
	public void finish() {
		DriverManage.finish();
		serverManage.stopServer();
	}
	
	

}
