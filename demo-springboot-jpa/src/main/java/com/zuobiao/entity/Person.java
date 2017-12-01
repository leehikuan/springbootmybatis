package com.zuobiao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
* @author:LHK
* 2017年11月28日 下午1:50:44
* 类说明
*/
@Entity
public class Person {
	@Id
	@GeneratedValue
	private Integer id;
	private String score;
	private Integer age;
	
	public Person() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
