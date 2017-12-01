package com.zuobiao.app.service;

import java.io.File;
import java.io.IOException;

import com.zuobiao.app.util.HttpUtil;

//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

public class ReadExcelGeo {
	static int ColumnNumOfAddr;//地址所在的列数
//	static int sheetNums;//sheet的数目
	static String filePath;//文件的路径
	static int firstDataRowNum;//第一条数据的行数
	public static void main(String[] args) {
		WritableWorkbook wwb=null;
		Workbook wb=null;
		try {
			firstDataRowNum=1;
			filePath="C:\\Users\\Administrator\\Desktop\\123.xlsx";
//			String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());  
//		    InputStream is = null;  
//		    Workbook wb = null;  
//		    try {  
//		        is = new FileInputStream(filePath);  
//		        if (fileType.equals("xls")) {  
//		        	HSSFWorkbook   wfb = new HSSFWorkbook(is);  
//		        } else if (fileType.equals("xlsx")) {  
//		            wb = new XSSFWorkbook(is);  
//		        } else {  
//		            throw new Exception("读取的不是excel文件");  
//		        } 
			//表格样式
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);  
			WritableCellFormat wcfFC = new WritableCellFormat(wfc); 
			
			wb = Workbook.getWorkbook(new File(filePath));
			//打开一个文件的副本，并且将制定数据写回到原文件中
			wwb = Workbook.createWorkbook(new File(filePath), wb);
			
			int sheetNums=wwb.getNumberOfSheets();
			boolean overLimit=false;//调用是否超限的标志
			for(int a=0;a<sheetNums;a++) {
				//对第一个工作簿的更新
				WritableSheet ws = wwb.getSheet(a);//
				int columnNums=ws.getColumns();//该sheet总列数
				int rowNums=ws.getRows();
				System.out.println("sheet的名字是："+ws.getName()+"=====sheet的列数："+columnNums+"=====sheet的行数："+rowNums);
				WritableCell cell = null;
				for(int j=firstDataRowNum-1;j<rowNums;j++){
					Cell addrCell=ws.getCell(ColumnNumOfAddr-1, j);
					String addr=addrCell.getContents();
					String location=HttpUtil.getLocationByAddr(addr);
					if(location.equals("DAILY_QUERY_OVER_LIMIT")) {
						System.out.println("该key的调用次数超出限制！！！！！！！！！！！！！！！！！！！！！！！");
						overLimit=true;
						break;
					}
					cell = new Label(columnNums, j, location,wcfFC);
					ws.addCell(cell);
				}
				if(overLimit) {
					break;
				}
			}
			wwb.write();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
