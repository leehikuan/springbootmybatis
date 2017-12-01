package com.zuobiao.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
	@Value("${score}")
	private String score;
	@Value("${age}")
	private Integer age;
	@Autowired
	private Properties properties;
	@Autowired
	private PersonRepository personRepository;
	//请求：http://127.0.0.1:8081/program/hello
	//请求：http://127.0.0.1:8081/program/hi
	@RequestMapping(value={"/hello","/hi"},method=RequestMethod.GET)
	public String sayHello() {
		return score+"|"+age;
	}
	//请求：http://127.0.0.1:8081/program/getScore
	@RequestMapping(value="/getScore",method=RequestMethod.GET)
	public String getScore() {
		return properties.getScore();
	}
	//请求：http://127.0.0.1:8081/program/testParam/11
	@RequestMapping(value="/testParam/{id}",method=RequestMethod.GET)
	public String testParam(@PathVariable("id") Integer id) {
		return "id:"+id;
	}
	//请求：http://127.0.0.1:8081/program/11/testParam2
	@RequestMapping(value="/{id}/testParam2",method=RequestMethod.GET)
	public String testParam2(@PathVariable("id") Integer id) {
		return "id:"+id;
	}
	//请求：http://127.0.0.1:8081/program/testParam3?id=11
	@RequestMapping(value="/testParam3",method=RequestMethod.GET)
	public String testParam3(@RequestParam("id") Integer myId) {
		return "myId:"+myId;
	}
	//请求：http://127.0.0.1:8081/program/testParam4
	//请求：http://127.0.0.1:8081/program/testParam4?id=11
	@RequestMapping(value="/testParam4",method=RequestMethod.GET)
	public String testParam4(@RequestParam(value="id",required=false,defaultValue="0") Integer myId) {
		return "myId:"+myId;
	}
	//请求：http://127.0.0.1:8081/program/testParam5?id=11
	@GetMapping(value="/testParam5")
	public String testParam5(@RequestParam(value="id",required=false,defaultValue="0") Integer myId) {
		return "myId:"+myId;
	}
	
	@PostMapping(value="/addPerson")
	public Person addPerson(@RequestParam("score") String score,
			@RequestParam("age") Integer age) {
		Person p=new Person();
		p.setAge(age);
		p.setScore(score);
		return personRepository.save(p);
	}
	@GetMapping(value="/getOnePerson/{id}")
	public Person getPerson(@PathVariable("id") Integer id) {
		return personRepository.findOne(id);
	}
	@PutMapping(value="/updatePerson/{id}")
	public Person updatePerson(@PathVariable("id") Integer id,
			@RequestParam("score") String score,
			@RequestParam("age") Integer age) {
		Person p=new Person();
		p.setId(id);
		p.setAge(age);
		p.setScore(score);
		return personRepository.save(p);
	}
	
	@DeleteMapping(value="/delete/{id}")
	public void deleteById(@PathVariable("id") Integer id) {
		personRepository.delete(id);
	}
}
