package com.zuobiao.app.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	/**
	 * 根据地址获取经纬度
	 * @param add
	 * @return 返回值   "120.123456,20.123456"
	 * @throws IOException
	 */
	public static String getLocationByAddr(String add) throws IOException{
		String location=null;
		String urlPath = new String("http://restapi.amap.com/v3/geocode/geo"); 
//		String key="1e1b8d3b2fc2b8247f62a8498f329012";
//		String key="1af1d9ea766a42b7698294e443cba636";
//		String key="710fea795046133db39b3e92ea1cab20";
		String key="bda051aab84f50738a08b5edd7f73917";
		//String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
//		String param="address="+URLEncoder.encode(add,"UTF-8")+"&city=金华"+"&distinct=义乌"+"&key="+key;
		String param="address="+URLEncoder.encode(add,"UTF-8")+"&key="+key+"&city="+URLEncoder.encode("金华","UTF-8")+"&distinct="+URLEncoder.encode("义乌","UTF-8");
		
		//建立连接
		URL url=new URL(urlPath);
		HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
		//设置参数
		httpConn.setDoOutput(true);   //需要输出
		httpConn.setDoInput(true);   //需要输入
		httpConn.setUseCaches(false);  //不允许缓存
		httpConn.setRequestMethod("POST");   //设置POST方式连接
		//设置请求属性
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		httpConn.setRequestProperty("Charset", "UTF-8");
		//连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
		httpConn.connect();
		//建立输入流，向指向的URL传入参数
		DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
		dos.writeBytes(param);
		dos.flush();
		dos.close();
		//获得响应状态
		int resultCode=httpConn.getResponseCode();
		if(HttpURLConnection.HTTP_OK==resultCode){
			StringBuffer sb=new StringBuffer();
			String readLine=new String();
			BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
			while((readLine=responseReader.readLine())!=null){
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			String res=sb.toString();
			if(res.contains("DAILY_QUERY_OVER_LIMIT")) {
				return "DAILY_QUERY_OVER_LIMIT";
			}
			JSONObject jso=JSON.parseObject(sb.toString());
			String num=jso.getString("count");
			if(!"0".equals(num)) {
				JSONArray arr=jso.getJSONArray("geocodes");
				if(arr.isEmpty()) {
					location="未取到";
				}else {
					location=arr.getJSONObject(0).getString("location");
				}
			}else {
				location="未取到";
			}
		}
		return location;
	}
	/**
	 * 根据经纬度获取结构化地址
	 * @param location 经纬度  "120.123456,20.123456"
	 * @return  返回值  json串
	 * @throws IOException
	 */
	public static String getAddrByLocation(String location) throws IOException{
		String res="";//返回值
		String urlPath = new String("http://restapi.amap.com/v3/geocode/regeo"); 
//		String key="1e1b8d3b2fc2b8247f62a8498f329012";
//		String key="1af1d9ea766a42b7698294e443cba636";
		String key="710fea795046133db39b3e92ea1cab20";
//		String key="bda051aab84f50738a08b5edd7f73917";
		//String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
		String param="location="+location+"&extensions=all"+"&radius=1000"+"&roadlevel=0"+"&key="+key;
//		String param="address="+URLEncoder.encode(add,"UTF-8")+"&city=金华"+"&distinct=义乌"+"&key="+key;
		//建立连接
		URL url=new URL(urlPath);
		HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
		//设置参数
		httpConn.setDoOutput(true);   //需要输出
		httpConn.setDoInput(true);   //需要输入
		httpConn.setUseCaches(false);  //不允许缓存
		httpConn.setRequestMethod("POST");   //设置POST方式连接
		//设置请求属性
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		httpConn.setRequestProperty("Charset", "UTF-8");
		//连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
		httpConn.connect();
		//建立输入流，向指向的URL传入参数
		DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
		dos.writeBytes(param);
		dos.flush();
		dos.close();
		//获得响应状态
		int resultCode=httpConn.getResponseCode();
		if(HttpURLConnection.HTTP_OK==resultCode){
			StringBuffer sb=new StringBuffer();
			String readLine=new String();
			BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
			while((readLine=responseReader.readLine())!=null){
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			res=sb.toString();
			/*if(res.contains("DAILY_QUERY_OVER_LIMIT")) {
				return "DAILY_QUERY_OVER_LIMIT";
			}
			JSONObject jso=JSON.parseObject(sb.toString());
			String formatted_address="";
			String streetName="";
			String township="";
			String name="";
			String area="";
			String status=jso.getString("status");
			if("1".equals(status)) {
				JSONObject regeocode=jso.getJSONObject("regeocode");
				if(regeocode.isEmpty()) {
					location="未取到";
				}else {
					formatted_address=regeocode.getString("formatted_address");//streetNumber
					JSONObject streetNumber=jso.getJSONObject("streetNumber");
					JSONObject addressComponent=jso.getJSONObject("addressComponent");
					if(!streetNumber.isEmpty()) {
						streetName=streetNumber.getString("name");
					}
					if(!addressComponent.isEmpty()) {
						township=addressComponent.getString("township");
					}
				}
				JSONArray aois=jso.getJSONArray("aois");
				if(aois.isEmpty()) {
					name="未取到";
				}else {
					name=aois.getJSONObject(0).getString("name");
					area=aois.getJSONObject(0).getString("area");
				}
			}else {
				location="未取到";
			}*/
		}
		return res;
		
	}
	
}
