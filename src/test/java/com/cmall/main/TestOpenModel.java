package com.cmall.main;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.cmall.base.AppiumServerManage;
import com.cmall.base.DriverManage;
import com.cmall.base.Execute;
import com.cmall.cases.OpenModel;

public class TestOpenModel {
	
	AppiumServerManage serverManage = new AppiumServerManage();
	
	@BeforeSuite
	public void init() {
		serverManage.startServer();
		DriverManage.init();
	}
	
	@Test(priority=0)
	public void testOpenModel() {
		Execute.runTestCase(OpenModel.class);
	}
	
	
	@AfterSuite
	public void finish() {
		DriverManage.finish();
		serverManage.stopServer();
	}
	
	

}
