package com.zuobiao.app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zuobiao.app.entity.Person;
import com.zuobiao.app.iservice.IGeographyService;
import com.zuobiao.app.iservice.IPersonService;

/**
* @author:LHK
* 2017年11月29日 上午11:38:16
* 类说明
*/
@RestController
public class GeographyController2 {
//	@Value("${location}")
//	private String location;
	@Autowired
	private IGeographyService geographyService;
	@Autowired
	private IPersonService personService;
	
	@PostMapping(value = "/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("ColumnNum") Integer ColumnNum,
			@RequestParam("firstDataRowNum") Integer firstDataRowNum,
			@RequestParam("type") String type) throws Exception {
		Map <String,String>map=new HashMap<String,String>();
		long startTime = System.currentTimeMillis();
		String pa=System.getProperty("user.dir");
		System.out.println(pa);
		String location=pa+"/src/main/resources/";
		System.out.println("fileName：" + file.getOriginalFilename());
		OutputStream os=null;
		InputStream is=null;
//		String dirPath=location +File.separator;
		String path=location.replace("\\", "/") + new Date().getTime()+ file.getOriginalFilename();
		try {
//			if(!new File(dirPath).exists()) {
//				new File(dirPath).mkdirs();
//			}
			// 获取输出流
			os = new FileOutputStream(path);
			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			is = file.getInputStream();
			int temp;
			// 一个一个字节的读取并写入
			while ((temp = is.read()) != (-1)) {
				os.write(temp);
			}
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			os.close();
			is.close();
		}
		boolean res=false;
		//地理编码
//		if("1".equals(type)) {
//			res=geographyService.ReadExcelGeo(ColumnNum, path, firstDataRowNum);
//		}else {
//			//逆地理编码
//			res=geographyService.ReadExcelReGeo(ColumnNum, path, firstDataRowNum);
//		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		map.put("status", "success");
		map.put("path", path);
		return JSON.toJSONString(map);
	}
	@PostMapping(value="/change")
	public String change(
			@RequestParam("ColumnNum") Integer ColumnNum,
			@RequestParam("firstDataRowNum") Integer firstDataRowNum,
			@RequestParam("type") String type,
			@RequestParam("path") String path) {
		//地理编码
		boolean res=false;
		if("1".equals(type)) {
			res=geographyService.ReadExcelGeo(ColumnNum, path, firstDataRowNum);
		}else {
			//逆地理编码
			res=geographyService.ReadExcelReGeo(ColumnNum, path, firstDataRowNum);
		}	
		
		return "success";
		
	}
}
