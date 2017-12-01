package com.zuobiao.app.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zuobiao.app.entity.Person;
import com.zuobiao.app.iservice.IGeographyService;
import com.zuobiao.app.iservice.IPersonService;

/**
* @author:LHK
* 2017年11月29日 上午11:38:16
* 类说明
*/
@RestController
public class GeographyController {
	@Value("${location}")
	private String location;
	@Autowired
	private IGeographyService geographyService;
	@Autowired
	private IPersonService personService;
	
	@GetMapping(value="/getPersonById/{id}")
	public Person getPersonById(@PathVariable("id") Integer id) {
		Person p=personService.getPersonById(id);
		return p;
	}
	@GetMapping(value="/addAndUpdate/{id}")
	public Person addAndUpdate(@PathVariable("id") Integer id) {
		Person p=new Person();
//		p.setId(id);
		personService.addAndUpdateByID(p);
		return p;
	}
	@RequestMapping("/")
	public ModelAndView index(Model model) {
		ModelAndView mv = new ModelAndView("index");
		Person single = new Person("aa", "11");
        List<Person> people = new ArrayList<Person>();
        Person p1 = new Person("zhangsan", 11+"");
        Person p2 = new Person("lisi", "22");
        Person p3 = new Person("wangwu", "33");
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people=personService.findAll();
        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);
        return mv;
    }
	@GetMapping(value = "uploadFile")
	public String cs2(@RequestParam("file") MultipartFile file,
			@RequestParam("ColumnNum") Integer ColumnNum,
			@RequestParam("firstDataRowNum") Integer firstDataRowNum,
			@RequestParam("type") String type) throws Exception {
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + file.getOriginalFilename());
		OutputStream os=null;
		InputStream is=null;
		try {
			String path=location + new Date().getTime() + file.getOriginalFilename();
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
		long endTime = System.currentTimeMillis();
		System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return "success";
	}
}
