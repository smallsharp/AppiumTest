package com.cmall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import com.cmall.testcase.ITestCase;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Execute {

	private static Logger log = Logger.getLogger(Execute.class);
	private static final String KILL_NODE = "taskkill /F /im node.exe";
	private static MyRunnable runnable;
	private static final String IP = "127.0.0.1";


	/**
	 * 默认4723，--bootstrap-port 默认4724，--chromedriver-port：默认9515
	 * @param ip
	 * @param port
	 * @param deviceName
	 */
	private static void startServer(String ip, int port, String deviceName) {

		Runtime runtime = Runtime.getRuntime();
		Process proc;
		try {
			runtime.exec(KILL_NODE);
			log.info("start to launch server on " + ip + " " + port + " " + deviceName);
			int bpport = port + 1;
			int chromeport = port + 4792;
			String launch = "appium.cmd -a " + ip + " -p " + port + " -bp " + bpport
					+ " --chromedriver-port " + chromeport + " -U " + deviceName +" --session-override" ;
			log.info(launch);

			proc = runtime.exec(launch);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			log.info(sb);
			log.info(proc.waitFor());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Appium Server is running on " + ip + " " + port + " " + deviceName);

	}

	/**
	 * 多线程启动服务
	 */
	public void startServer() {
		int port = 4723;
		DDMlibUtil ddMlib = DDMlibUtil.getInstance();
		ddMlib.init();
		List<String> devicesName = ddMlib.getSerialNumber();
		ddMlib.finish();
		log.info("检测到设备"+devicesName.size()+"台");
		if (devicesName.size() <= 0) {
			return;
		}
		List<Thread> threadsList = new ArrayList<>();
		for (final String name : devicesName) {
			runnable = new MyRunnable();
			runnable.setIp(IP);
			runnable.setPort(port);
			runnable.setDeviceName(name);
			Thread thread = new Thread(runnable);
			thread.start();
			threadsList.add(thread);
			port+=2;
		}
		
		for (Thread thread : threadsList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

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
						System.out.println("driver:" + driver);
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
	
	class MyRunnable implements Runnable {

		private String ip;
		private int port;
		private String deviceName;

		public void setIp(String ip) {
			this.ip = ip;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}


		@Override
		public void run() {
			try {
				startServer(ip, port, deviceName);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}

		public MyRunnable() {
		}
	}
	
	

	public void test() {
		startServer();
	}
	
	@Test
	public void test2() {
		String cmd = "appium.cmd -a 127.0.0.1 -p 4723 -bp 4724 --chromedriver-port 9515 --udid 127.0.0.1:62001 --session-override";
		Process process = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(KILL_NODE);
			process = runtime.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		
		try {
			while((line = bufferedReader.readLine())!=null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}



