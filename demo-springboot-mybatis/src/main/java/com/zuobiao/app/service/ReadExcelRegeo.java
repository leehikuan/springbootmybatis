package com.zuobiao.app.service;

import java.io.File;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zuobiao.app.util.HttpUtil;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ReadExcelRegeo {
	static int ColumnNumOfLocation;//经纬度所在的列数  
//	static int sheetNums;//sheet的数目
	static String filePath;//文件的路径
	static int firstDataRowNum;//第一条数据的行数
	public static void main(String[] args) {
		//此数值需要修改
		ColumnNumOfLocation=3;
		firstDataRowNum=1;
		filePath="C:\\Users\\Administrator\\Desktop\\2-9月份逆编码结果.xls";
		WritableWorkbook wwb = null;
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new File(filePath));
			//打开一个文件的副本，并且将制定数据写回到原文件中
			wwb = Workbook.createWorkbook(new File(filePath), wb);
			//对第一个工作簿的更新
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);  
			WritableCellFormat wcfFC = new WritableCellFormat(wfc); 
			//表格样式
			WritableCell cell = null;
			int sheetNums=wwb.getNumberOfSheets();
			boolean overLimit=false;
			for(int a=0;a<sheetNums;a++) {
				WritableSheet ws = wwb.getSheet(a);
				int columnNums=ws.getColumns();//该sheet总列数
				int rowNums=ws.getRows();//该sheet总行数
				System.out.println("sheet的名字是："+ws.getName()+"=====sheet的列数："+columnNums+"=====sheet的行数："+rowNums);
				for(int j=firstDataRowNum-1;j<rowNums;j++){
					/*Cell jingduC=ws.getCell(2, j);
					String jingdu=jingduC.getContents();
					System.out.println("jingdu==========="+jingdu);
					Cell weiduC=ws.getCell(3, j);
					String weidu=weiduC.getContents();
					System.out.println("weidu==========="+weidu);
					String location=jingdu+","+weidu;
					System.out.println("location===================="+location);
					String newlocation="";*/
					Cell locationCell=ws.getCell(ColumnNumOfLocation-1, j);
					String location=locationCell.getContents();
					System.out.println(location);
					/*if(location.contains("125.")) {
						Cell addC=ws.getCell(1, j);
						location=HttpUtil.getLocationByAddr(addC.getContents());
						WritableCell c=new Label(2, j, location,wcfFC);
						ws.addCell(c);
					}else {
						continue;
					}*/
					
					String res=HttpUtil.getAddrByLocation(location);
					if(res.contains("DAILY_QUERY_OVER_LIMIT")) {
						overLimit=true;
						System.out.println("该key的调用次数超出限制！！！！！！！！！！！！！！！！！！！！！！！");
						break;
					}
					JSONObject jsObj=JSON.parseObject(res);
					String formatted_address="#";//格式化地址
					String streetName="#";//道路名
					String township="#";//街道名
					String name="#";//小区名
					String area="#";//小区面积
					StringBuilder strBuilder=new StringBuilder();
					String status=jsObj.getString("status");
					if("1".equals(status)) {
						JSONObject regeocode=jsObj.getJSONObject("regeocode");
						if(regeocode.isEmpty()) {
							break;
						}else {
							formatted_address=regeocode.getString("formatted_address");//streetNumber
							JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
							if(!addressComponent.isEmpty()) {
								township=addressComponent.getString("township");
								JSONObject streetNumber=addressComponent.getJSONObject("streetNumber");
								if(!streetNumber.isEmpty()) {
									streetName=streetNumber.getString("street");
								}
							}
						}
						JSONArray pois=regeocode.getJSONArray("pois");
						if(!pois.isEmpty()) {
							if(pois.size()>5) {
								for (int q=0; q<5; q++) {
									String pName=pois.getJSONObject(q).getString("name");
									if(q!=0) {
										strBuilder.append(",");
									}
									strBuilder.append(pName);
								}
							}else {
								for (int q=0; q<pois.size(); q++) {
									String pName=pois.getJSONObject(q).getString("name");
									if(q!=0) {
										strBuilder.append(",");
									}
									strBuilder.append(pName);
								}
							}
						}
						JSONArray aois=regeocode.getJSONArray("aois");
						if(!aois.isEmpty()) {
							name=aois.getJSONObject(0).getString("name");
							area=aois.getJSONObject(0).getString("area");
						}
					}else {
						continue;
					}
					cell = new Label(columnNums, j, township,wcfFC);
					WritableCell streetNameCell = new Label(columnNums+1, j, streetName,wcfFC);
					WritableCell nameCell = new Label(columnNums+2, j, name,wcfFC);
					WritableCell areaCell = new Label(columnNums+3, j, area,wcfFC);
					WritableCell formattedAddressCell = new Label(columnNums+4, j, formatted_address,wcfFC);
					WritableCell strBuilderCell = new Label(columnNums+5, j, strBuilder.toString(),wcfFC);
					ws.addCell(cell);
					ws.addCell(streetNameCell);
					ws.addCell(nameCell);
					ws.addCell(areaCell);
					ws.addCell(formattedAddressCell);
					ws.addCell(strBuilderCell);
				}
				if(overLimit) {
					break;
				}
			}
			wwb.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}finally {
			try {
				wwb.close();
				wb.close();
			} catch (WriteException e) {
				System.out.println("关闭异常");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("关闭异常");
				e.printStackTrace();
			}
		}

	}

}
