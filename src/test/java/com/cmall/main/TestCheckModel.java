package com.cmall.main;

import org.testng.annotations.Test;

import com.cmall.base.Execute;
import com.cmall.cases.CheckModel;

public class TestCheckModel {
	
	@Test(priority=10)
	public void testCheckModel() {
		Execute.runTestCase(CheckModel.class);
	}
	
}
