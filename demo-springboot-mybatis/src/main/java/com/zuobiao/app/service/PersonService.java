package com.zuobiao.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuobiao.app.dao.IPersonMapper;
import com.zuobiao.app.entity.Person;
import com.zuobiao.app.iservice.IPersonService;

/**
* @author:LHK
* 2017年11月29日 上午10:37:00
* 类说明
*/
@Service
public class PersonService implements IPersonService{
	
	@Autowired
	private IPersonMapper personMapper;
	@Override
	public Person getPersonById(long id) {
		return personMapper.findPersonById(id);
	}
	@Transactional
	public Person addAndUpdateByID(Person p) {
		p.setAge("18");
		p.setScore("a");
		personMapper.addPerson(p);
		long num=personMapper.getNum();
		System.out.println("总条数======"+num);
		p.setScore("123456789");
		p.setId(8);
		personMapper.updateByID(p);
		return null;
	}
	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return personMapper.findAll();
	}
	
}
