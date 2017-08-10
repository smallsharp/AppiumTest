package com.cmall.testcase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.cmall.spring.ActionOne;
import com.cmall.utils.DriverManage;
import com.cmall.utils.Execute;

public class TestMain {
	
	private ApplicationContext ac;
	private Logger log = Logger.getLogger(TestMain.class);
	
	public void test() {
		ac = new ClassPathXmlApplicationContext("spring.xml");
		ActionOne one = (ActionOne) ac.getBean("action_one");
		one.login();
		one.logout();
 	}
	
	@BeforeSuite
	public void init() {
		DriverManage.init();
	}
	
	@AfterSuite
	public void finish() {
		DriverManage.finish();
	}
	
	@Test(priority=10)
	public void test2() {
//		ITestCase testcase = new TestCase_Login();
		Execute.runTestCase(TestCase_Login.class);
	}
	
	@Test(priority=20)
	public void test3() {
		//ITestCase testcase = new TestCase02();
		Execute.runTestCase(TestCase02.class);
	}
	
	

}
