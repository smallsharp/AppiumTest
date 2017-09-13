package com.cmall.main;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.cmall.base.DriverManage;
import com.cmall.base.Execute;
import com.cmall.base.ServerManage;
import com.cmall.cases.Login;

public class TestLogin {
	
	
	@BeforeSuite
	public void init() {
		ServerManage.startServer(); 
		DriverManage.initDriver();
	}
	
	@AfterSuite
	public void finish() {
		DriverManage.finish();
	}
	
	@Test(priority=10)
	public void testLogin() {
		Execute.runTestCase(Login.class);
	}
	

}
