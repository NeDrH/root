package com.work;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FirstTask {

	private static double slope(double[] drr1,double[] drr2) {
		double k = (drr2[1]-drr1[1])/(drr2[0]-drr1[0]);
		return k;
	}
	//注解
	//判断此点是否为拐点，返回拐点，否则返回null
	private static double[] isInflectionPoint(double[][] drr) {
		int n = drr.length;
		for(int k = 1;k<5;k++) {
			for (int i = 0; i < (n-2*k); i++) {
				if(Math.abs(slope(drr[i], drr[i+k])-slope(drr[i+k], drr[i+k*2]))>(Math.sqrt(3)/3)) {
					return drr[i+k];
				}
			}
		}
		return null;
	}
	private static boolean slopeD(double[] drr1,double[] drr2,double[] drr3,double[] drr4) {
		double a = Math.abs(slope(drr1, drr2)-slope(drr2, drr3));
		double b = Math.abs(slope(drr1, drr3)-slope(drr3, drr4));
		if((a-b)>0) {//threshold=(Math.sqrt(3)/3)
			return true;
		}
		return false;
	}
	private static boolean isInflectionPoint(double[] drr1,double[] drr2,double[] drr3,double threshold) {
		
		if(Math.abs(slope(drr1, drr2)-slope(drr2, drr3))>threshold) {//threshold=(Math.sqrt(3)/3)
			return true;
		}
		return false;
	}
	public static double[][] getData(File f) throws IOException {
		
		String content = FileUtils.readFileToString(f, "UTF-8");
		JSONArray arrContent = new JSONArray(content);
		double[][] drr = new double[arrContent.length()][2];
		System.out.println(arrContent.length());
		for (int i = 0; i < arrContent.length(); i++) {
			JSONObject temp = (JSONObject) arrContent.get(i);
			String longitude = temp.getString("longitude");// 经度
			String latitude = temp.getString("latitude");// 纬度
			drr[i][1] = Double.parseDouble(longitude);
			drr[i][0] = Double.parseDouble(latitude);
//			System.out.println("("+longitude+","+latitude+")");
		}
		return drr;
	}
	private static LinkedList<double []> calcInflectionPoint(File f,int n) throws IOException {
		double[][] drr = getData(f);
		int tmp = 0;
		int count = 0;
		LinkedList<double []> lt = new LinkedList<double []>();
//		double a = Math.abs(lt.get(i)[0]-lt.get(i+1)[0]);
//		double b = Math.abs(lt.get(i)[1]-lt.get(i+1)[1]);
//		if(a<0.001||b<0.001) {
//			lt.remove(i+1);
//		}
		for (int i = 1; i < drr.length-2; i++) {
			count++;
				boolean a = isInflectionPoint(drr[i-1], drr[i], drr[i+1],Math.sqrt(3)*5);
				if(a&&!lt.contains(drr[i])) {
					lt.add(drr[i]); 
					tmp = i-1;
					count = 0;
				}	
				if(count>=2) {
					double c = Math.abs(drr[tmp][0]-drr[i][0]);
					double b = Math.abs(drr[tmp][1]-drr[i][1]);
					if(c>0.0005||b>0.0005) {
						lt.add(drr[i]); 
						tmp = i;
						count = 0;
					}
				}
			
			
	}
		return lt;
	}
	private static LinkedList<double []> calcInflectionPoint1(File f) throws IOException {
		double[][] drr = getData(f);
		LinkedList<double []> lt = new LinkedList<double []>();
		LinkedList<double []> lt1 = new LinkedList<double []>();
//		lt.add(drr[0]);
		for (int i = 0; i < drr.length; i++) {
			lt.add(drr[i]);
		}
		for (int i = 0; i < lt.size()-1; i++) {
			double a = Math.abs(lt.get(i)[0]-lt.get(i+1)[0]);
			double b = Math.abs(lt.get(i)[1]-lt.get(i+1)[1]);
			if(a<0.001||b<0.001) {
				lt.remove(i+1);
			}
		}
		double[][] drr1 = new double[lt.size()][2];
		for (int i = 0; i < drr1.length; i++) {
			drr1[i][0] = lt.get(i)[0];
			drr1[i][1] = lt.get(i)[1];
		}
		for (int i = 0; i < drr1.length; i++) {
			for (int k = 1; k < 10; k++) {
				if(Math.max((i+2*k), i+k+2)>drr1.length-1) {
					break;
				}
				if(slopeD(drr[i], drr1[i+k], drr1[i+1+k], drr1[i+2+k])) {
					if(lt1.contains(drr[i+k])) {
						break;
					}
					lt1.add(drr1[i+k]);
					i = i+k-1;
					break;
				}
				if(isInflectionPoint(drr1[i+k-1], drr1[i+k], drr1[i+1+k],(Math.sqrt(3)/6))) {
					if(lt1.contains(drr1[i+k])) {
						break;
					}
					lt1.add(drr1[i+k]);
					i = i+k-2;
					break;
				}
				if(isInflectionPoint(drr1[i], drr1[i+k], drr1[i+1+k],(Math.sqrt(3)/6))) {
					if(lt1.contains(drr1[i+k])) {
						break;
					}
					lt1.add(drr1[i+k]);
					i = i+k-2;
					break;
				}
			}
		}
		return lt1;
	}
	private static LinkedList<double []> calcInflectionPoint(File f) throws IOException {
		double[][] drr = getData(f);
//		System.out.println("double[][] drr = getData(f);");
		LinkedList<double []> lt = new LinkedList<double []>();
		LinkedList<double []> lt1 = new LinkedList<double []>();
		lt.add(drr[0]);
		lt1.add(drr[0]);
		// LinkedList<ArrayList<Double>> lt = new LinkedList<ArrayList<Double>>();
		for (int i = 0; i < drr.length; i++) {
			for (int k = 1; k < 10; k++) {
				if(Math.max((i+2*k), i+k+2)>drr.length-1) {
					break;
				}
				if(slopeD(drr[i], drr[i+k], drr[i+1+k], drr[i+2+k])) {
					if(lt1.contains(drr[i+k])) {
						break;
					}
					lt1.add(drr[i+k]);
					i = i+k-1;
					break;
				}
				if(isInflectionPoint(drr[i+k-1], drr[i+k], drr[i+1+k],(Math.sqrt(3)/3))) {
					if(lt1.contains(drr[i+k])) {
						break;
					}
					lt1.add(drr[i+k]);
					i = i+k-2;
					break;
				}
				if(isInflectionPoint(drr[i], drr[i+k], drr[i+1+k],(Math.sqrt(3)/3))) {
					if(lt1.contains(drr[i+k])) {
						break;
					}
					lt1.add(drr[i+k]);
					i = i+k-2;
					break;
				}
			}
		}
		for (int i = 1; i < drr.length-1; i++) {
				if(isInflectionPoint(drr[i-1], drr[i], drr[i+1],(Math.sqrt(3)/64))) {
					lt.add(drr[i]);                                                                                                                 
				}
//					else {
//					double a = Math.abs(drr[i-1][0]-drr[i+1][0]);
//					double b = Math.abs(drr[i-1][1]-drr[i+1][1]);
//					if(a>0.001||b>0.001) {
//						lt.add(drr[i]);
//					}
//				}
		}
		return lt;
	}
	public static void main(String[] args) throws IOException {
		File f = new File("I:\\苏富特\\201804176891-1");
//		FileOutputStream out = new FileOutputStream("I:\\苏富特\\output19108.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter("I:\\苏富特\\output6892.txt"));
		LinkedList<double []> lt = calcInflectionPoint1(f);
		System.out.println("size: "+lt.size());
		for (int i = 0; i < lt.size(); i++) {
			bw.write("["+lt.get(i)[1]+","+lt.get(i)[0]+"]"+",\n");
			bw.flush();
		}
		bw.close();
	}

}
