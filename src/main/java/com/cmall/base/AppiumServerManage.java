package com.cmall.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.log4j.Logger;

import com.cmall.utils.DDMlibUtil;

public class AppiumServerManage {

	private static Logger log = Logger.getLogger(AppiumServerManage.class);
	private static final String KILL_NODE = "taskkill /F /im node.exe";
	private static List<String> devicesName = DDMlibUtil.getInstance().getDevicesName();
	private static boolean isRunning = false;

	static {
		checkEnvironment();
	}

	/**
	 * 1.清理node进程
	 * 2.检查设备连接
	 */
	private static void checkEnvironment() {
		// 清理使用node进程
		try {
			Runtime.getRuntime().exec(KILL_NODE);
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("【第一步】检查环境，测试依赖的环境,稍后…");
		log.info("检测到设备" + devicesName.size() + "台");
		if (devicesName.size() <= 0) {
			return;
		}
	}

	/**
	 * 多线程启动服务
	 * 
	 */
	public void startServer() {
		log.info("【第二步】：批量启动Appium服务器");
		List<Thread> threadsList = new ArrayList<>();
		String default_ip = "127.0.0.1";
		int default_port = 4723;
		for (int i = 0; i < devicesName.size(); i++) {
			log.info("第" + (i + 1) + "台设备名称：" + devicesName.get(i));
			MyRunnable runnable = new MyRunnable();
			runnable.setIp(default_ip);
			runnable.setPort(default_port);
			runnable.setDeviceName(devicesName.get(i));
			Thread thread = new Thread(runnable);
			threadsList.add(thread);
			thread.start();
			default_port += 2;
		}

		for (Thread thread : threadsList) {
			try {
				log.info("执行join");
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 启动Appium服务
	 * 
	 * @param ip
	 * @param port
	 * @param deviceName
	 */
	public void startAppiumServer(String ip, int port, String deviceName) {

		int bpport = port + 1;
		int chromeport = port + 4792;
		String launch_cmd = "appium.cmd -a " + ip + " -p " + port + " -bp " + bpport + " --chromedriver-port "
				+ chromeport + " -U " + deviceName + " --session-override >c:/" + port + ".txt";
		log.info(launch_cmd);
		// 根据官方文档，如果使用非阻塞执行，可以这样做:首先创建一个非阻塞的handler DefaultExecuteResultHandler，这个是专门用来处理非阻塞
		// 在创建一个watchdog用来监控输出，设置timeout时间60s
		// 创建一个执行器设置退出代码为1，代表执行成功
		// 注意，这里必须设置一个waitfor time，如果没有设置会报错
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		CommandLine commandLine = CommandLine.parse(launch_cmd);
		ExecuteWatchdog dog = new ExecuteWatchdog(60 * 1000);
		Executor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.setWatchdog(dog);
		try {
			executor.execute(commandLine, resultHandler);
			resultHandler.waitFor(5000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Appium Server is running on " + ip + " " + port + " " + deviceName);
	}

	/**
	 * 默认4723，--bootstrap-port 默认4724，--chromedriver-port：默认9515
	 * 使用Runtime 或者 ProcessBuilder 导致线程阻塞，无法继续执行
	 * @param ip
	 * @param port
	 * @param deviceName
	 */
	@SuppressWarnings("unused")
	private static void startServer(String ip, int port, String deviceName) {

		Runtime runtime = Runtime.getRuntime();
		Process proc;
		try {
			log.info("start to launch server on " + ip + " " + port + " " + deviceName);
			int bpport = port + 1;
			int chromeport = port + 4792;
			String launch = "appium.cmd -a " + ip + " -p " + port + " -bp " + bpport + " --chromedriver-port "
					+ chromeport + " -U " + deviceName + " --session-override";
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
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		log.info("Appium Server is running on " + ip + " " + port + " " + deviceName);

	}
	
	/**
	 * 停止服务
	 */
	public void stopServer() {
		try {
			Runtime.getRuntime().exec(KILL_NODE);
		} catch (IOException e) {
			e.printStackTrace();
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
				log.info("执行MyRunnable中run方法");
				startAppiumServer(ip, port, deviceName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
