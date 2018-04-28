package com.work;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class getData {
	public static void main(String[] args) throws IOException {
//		 String s = "{\"name\":\"a\",\"family\":[\"张三\",\"李四\"]}";
		 
		File f = new File("I:\\苏富特\\testJson");
		
	    String content = FileUtils.readFileToString(f,"UTF-8");
	    JSONArray arrContent = new JSONArray(content);
	    System.out.println(arrContent.length());
	    for (int i = 0; i < arrContent.length(); i++) {
	    	JSONObject temp = (JSONObject) arrContent.get(i);      
//            String name = temp.getString("name");      
//            String age = temp.getString("age");
	    	String longitude = temp.getString("longitude");//经度
	    	String latitude = temp.getString("latitude");//纬度
            System.out.println("("+longitude+" , "+latitude+")");
		}
	    
//		 JSONObject jsonObject = new JSONObject(content);
//		 System.out.println("姓名是："+jsonObject.getString("name"));
//	        System.out.println("年龄："+jsonObject.getDouble("age"));
//	        System.out.println("学到的技能："+jsonObject.getJSONArray("major"));
//	        System.out.println("国家："+jsonObject.getJSONObject("Nativeplace").getString("country"));
//
//	    
//	        JSONObject jsonOb = JSONObject.fromObject(jsonObject);
//
//	        //注意：family中的内容带有中括号[]，所以要转化为JSONArray类型的对象
//	        JSONArray family = jsonOb.getJSONArray("family");

	      String jsonData = "[{ \"id\": \"27JpL~jd99w9nM01c000qc\", \"version\": \"abc\" },"
	      		+ "{ \"id\": \"27JpL~j6UGE0LX00s001AH\", \"version\": \"bbc\" },"
	      		+ "{ \"id\": \"27JpL~j7YkM0LX01c000gt\", \"version\": \"Wa_\" }]";    
	        JSONArray arr = new JSONArray(jsonData);      
	        for (int i = 0; i < arr.length(); i++) {      
	            JSONObject temp = (JSONObject) arr.get(i);      
	            String id = temp.getString("id");      
	            String version = temp.getString("version");
	            System.out.println(id+":"+version);
	        } 
	        
	        
//	        for (int i = 0; i < family.length(); i++) {
//	            //提取出family中的所有
//	            String s1 = (String) family.get(i);
//	            System.out.println("currentFamily:" + s1);
//
//	        }
	}
}
