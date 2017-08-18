package com.cmall.main;

import org.testng.annotations.Test;

import com.cmall.base.Execute;
import com.cmall.cases.CheckGoods;

public class TestCheckGoods {
	
	@Test(priority=10)
	public void testCheckModel() {
		Execute.runTestCase(CheckGoods.class);
	}
	
}
