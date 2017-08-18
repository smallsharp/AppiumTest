package com.cmall.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Execute {

	private static Logger log = Logger.getLogger(Execute.class);

	/**
	 * 多线程执行测试用例
	 * 
	 * @param testcase
	 */
	public static void runTestCase(final Class<?> testcaseClass) {

		List<Thread> threadsList = new ArrayList<Thread>();
		List<AndroidDriver<MobileElement>> drivers = DriverManage.driverList;

		for (final AndroidDriver<MobileElement> driver : drivers) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					ITestCase testcase = null;
					try {
						testcase = (ITestCase) testcaseClass.newInstance();
						testcase.setDriver(driver);
						testcase.runCase();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			thread.start();
			threadsList.add(thread);
		}

		for (Thread thread : threadsList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	
}



