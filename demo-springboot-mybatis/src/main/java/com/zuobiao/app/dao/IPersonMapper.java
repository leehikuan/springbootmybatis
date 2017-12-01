package com.zuobiao.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.zuobiao.app.entity.Person;

/**
* @author:LHK
* 2017年11月29日 上午11:14:54
* 类说明
*/
//@Mapper
//@Repository
public interface IPersonMapper {
	public Person findPersonById(Long id);

	public void addPerson(Person p);

	public void updateByID(Person p);

	public long getNum();
	
	public void deleteById(long id);

	public List<Person> findAll();
}
