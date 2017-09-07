package com.cmall.main;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.cmall.base.ServerManage;
import com.cmall.base.DriverManage;
import com.cmall.base.Execute;
import com.cmall.cases.OpenModel;

/**
 * 执行顺序：
 * 1.执行BeforeSuite，进行服务和driver的初始化
 * 2.执行@Test脚本
 * 3.执行AfterSuite
 * @author cm
 *
 */
public class TestOpenModel {
	
	@BeforeSuite
	public void init() {
		ServerManage.startServer();
		DriverManage.initDriver();
	}
	
	@Test(priority=0)
	public void testOpenModel() {
		Execute.runTestCase(OpenModel.class);
	}
	
	
	@AfterSuite
	public void finish() {
		DriverManage.finish();
	}
	
	

}
