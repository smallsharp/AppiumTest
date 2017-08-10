package com.cmall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class ForTest {

	private static final String KILL_NODE = "taskkill /F /im node.exe";
	String cmd = "appium.cmd -a 127.0.0.1 -p 4723 -bp 4724 --chromedriver-port 9515 --udid 127.0.0.1:62001 --session-override";
	String cmd2 = "adb devices";
	
	@Test
	public void test() throws IOException, InterruptedException {
		start(cmd);
	}

	public void start(String cmd) throws IOException, InterruptedException {
		
//		Runtime.getRuntime().exec(KILL_NODE);
		String[] cmdArr = cmd.split(" ");
		List<String> cmdList = new ArrayList<String>();
		for (int i = 0; i < cmdArr.length; i++) {
		    cmdList.add(cmdArr[i]);
		}

		ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
		processBuilder.redirectErrorStream(true);
		Process p = processBuilder.start();
		InputStream is = p.getInputStream();
		BufferedReader bs = new BufferedReader(new InputStreamReader(is));
		p.waitFor();
		String line = null;
		while ((line = bs.readLine()) != null) {
		    System.out.println(line);
		}
		
		if (p.isAlive()) {
			p.destroy();
		}
	}
	

}
