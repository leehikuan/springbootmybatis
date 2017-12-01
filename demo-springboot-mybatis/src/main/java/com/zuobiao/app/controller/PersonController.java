package com.zuobiao.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zuobiao.app.entity.Person;
import com.zuobiao.app.iservice.IPersonService;

/**
* @author:LHK
* 2017年11月29日 上午11:38:16
* 类说明
*/
@RestController
public class PersonController {
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
}
