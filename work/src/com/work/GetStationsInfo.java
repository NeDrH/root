package com.work;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetStationsInfo {

	public static double[][] getData(File f) throws IOException {
		
		String content = FileUtils.readFileToString(f, "UTF-8");
		JSONArray arrContent = new JSONArray(content);
		double[][] drr = new double[arrContent.length()][2];
		System.out.println(arrContent.length());
		for (int i = 0; i < arrContent.length(); i++) {
			JSONObject temp = (JSONObject) arrContent.get(i);
			String longitude = temp.getString("lng");// 经度
			String latitude = temp.getString("lat");// 纬度
			drr[i][1] = Double.parseDouble(longitude);
			drr[i][0] = Double.parseDouble(latitude);
//			System.out.println("("+longitude+","+latitude+")");
		}
		return drr;
	}
	public static void main(String[] args) throws IOException {
		File f = new File("I:\\苏富特\\20180422\\23路路径信息");
		BufferedWriter bw = new BufferedWriter(new FileWriter("I:\\苏富特\\23Path.txt"));
		double[][] drr = getData(f);
		for (int i = 0; i < drr.length; i++) {
			bw.write("["+drr[i][0]+","+drr[i][1]+"]"+",\n");
			bw.flush();
		}
		bw.close();
	}

}
