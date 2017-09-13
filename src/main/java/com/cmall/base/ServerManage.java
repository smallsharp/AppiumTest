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

/**
 * AppiumServer管理类
 * 主要作用：根据EnvironmentCheck.getConfigs()获取到的配置信息，批量启动Appium服务程序
 * 
 * 
 * @author cm
 *
 */
public class ServerManage {

	private static Logger log = Logger.getLogger(ServerManage.class);
	private static final String KILL_NODE = "taskkill /F /im node.exe";
	private static List<Config> configs = Environment.getConfigs();


	public static void startServer() {
		
		log.info("[服务器准备阶段] ==> 启动Appium服务器");
		List<Thread> threadsList = new ArrayList<>();
		for (final Config config : configs) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// 批量启动服务
					startAppiumServer(config.getIp(), config.getPort(), config.getName());
				}
			});
			thread.start();
			threadsList.add(thread);
		}
		
		for (Thread thread : threadsList) {
			try {
				thread.join(); // 等待所有启动服务的子线程执行完毕，即每个run方法执行一次后，会执行一次join
				log.info("执行join");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 启动Appium服务
	 * 
	 * @param ip
	 * @param port
	 * @param deviceName
	 */
	private static void startAppiumServer(String ip, int port, String deviceName) {

		int bpport = port + 1;
		int chromeport = port + 4792;
		String launch_cmd = "appium.cmd -a " + ip + " -p " + port + " -bp " + bpport + " --chromedriver-port "
				+ chromeport + " -U " + deviceName + " --session-override >c:/" + deviceName + System.currentTimeMillis() + ".txt";
		log.info(launch_cmd);
		// 如果使用非阻塞执行:首先创建一个非阻塞的DefaultExecuteResultHandler，这个是专门用来处理非阻塞
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		CommandLine commandLine = CommandLine.parse(launch_cmd);
		// 在创建一个watchdog用来监控输出，设置timeout时间60s
		ExecuteWatchdog dog = new ExecuteWatchdog(60 * 1000);
		Executor executor = new DefaultExecutor();
		// 创建一个执行器设置退出代码为1，代表执行成功
		executor.setExitValue(1);
		executor.setWatchdog(dog);
		try {
			executor.execute(commandLine, resultHandler);
			// 注意，这里必须设置一个waitfor time，如果没有设置会报错
			resultHandler.waitFor(5000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Appium server is running on " + "[ip= " + ip +" ,deviceName= "+ deviceName + " ,port= " + port + "]");
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

}
