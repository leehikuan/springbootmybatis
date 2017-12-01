package com.zuobiao.app.iservice;
/**
* @author:LHK
* 2017年11月30日 下午5:04:29
* 类说明
*/
public interface IGeographyService {
	public boolean ReadExcelGeo(int ColumnNumOfAddr,String filePath,int firstDataRowNum);
	public boolean ReadExcelReGeo(int ColumnNumOfLocation,String filePath,int firstDataRowNum);
}
