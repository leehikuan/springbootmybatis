package com.zuobiao.app.iservice;

import java.util.List;

import com.zuobiao.app.entity.Person;

/**
* @author:LHK
* 2017年11月29日 上午10:38:30
* 类说明
*/
public interface IPersonService {
	public Person getPersonById(long id);
	public Person addAndUpdateByID(Person p);
	public List<Person> findAll();
}
