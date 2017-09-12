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
 * 1.启动服务 ServerManage.startServer()
 * 2.初始化driver DriverManage.initDriver()
 * 3.执行测试用例
 * 4.还原测试环境
 * 
 * 注：BeforeSuite和AfterSuite 只需要写一次
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
