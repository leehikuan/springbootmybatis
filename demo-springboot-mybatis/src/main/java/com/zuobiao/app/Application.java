package com.zuobiao.app;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration 
@SpringBootApplication
@MapperScan("com.zuobiao.app.dao")
public class Application {
	private static Logger logger = Logger.getLogger(Application.class);
	 
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
