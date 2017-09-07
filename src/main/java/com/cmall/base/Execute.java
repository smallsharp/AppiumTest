package com.cmall.base;

import java.util.ArrayList;
import java.util.List;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Execute {

	/**
	 * 多线程初始化driver实例
	 * 
	 * @param testcase
	 */
	public static void runTestCase(final Class<?> testcaseClass) {

		List<Thread> threadsList = new ArrayList<Thread>();
		List<AndroidDriver<MobileElement>> drivers = DriverManage.getDriverList();

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



