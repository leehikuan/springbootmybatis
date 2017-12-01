package com.zuobiao.app.entity;
/**
* @author:LHK
* 2017年11月29日 上午10:36:08
* 类说明
*/
public class Person {
	private Integer id;
	private String score;
	private String age;
	public Person(String score,String age){
		this.score=score;
		this.age=age;
	}
	public Person(){
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", score=" + score + ", age=" + age + "]";
	}
	
}
