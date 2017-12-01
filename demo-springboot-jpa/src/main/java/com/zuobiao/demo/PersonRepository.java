package com.zuobiao.demo;

import org.springframework.data.jpa.repository.JpaRepository;


/**
* @author:LHK
* 2017年11月28日 下午3:51:43
* 类说明
*/
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
