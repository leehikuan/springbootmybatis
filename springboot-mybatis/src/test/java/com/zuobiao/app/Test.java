package com.zuobiao.app;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author:LHK
* 2017年12月1日 下午3:20:37
* 类说明
*/
public class Test {
	@org.junit.Test
	public void test1() {
		String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date());
		System.out.println(time);
	}
}
