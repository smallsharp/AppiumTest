package com.cmall.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
	
	@SuppressWarnings("resource")
	public void test() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		ActionOne one = (ActionOne) ac.getBean("action_one");
		one.login();
		one.logout();
 	}

}
