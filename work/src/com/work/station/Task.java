package com.work.station;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.work.*;

public class Task {
	
	//����ȷ��һ��ֱ��,���ز���k,b;����Ϊk*x+y+b=0
	private static double[] line(double[] drr1,double[] drr2) {
		double[] parameter = new double[2];
		parameter[0] = (drr1[1]-drr2[1])/(drr1[0]-drr2[0]);
		parameter[1] = -(parameter[0]*drr1[0]+drr1[1]);
		return parameter;
	}
	//�㵽ֱ�߾���,parameterΪ����k,b;drrΪ��
	private static double distance(double[] parameter,double[] drr) {
		double k = parameter[0];
		double b = parameter[1];
		double result = Math.abs(k*drr[0]+drr[1]+b)/Math.sqrt(k*k+1);
		return result;
	}
	
	static boolean isSamePoint(double[] drr1,double[] drr2) {
		double x = Math.abs(drr1[0]-drr2[0]);
		double y = Math.abs(drr1[1]-drr2[1]);
//		System.out.println("x:"+x+",y:"+y);
		if(x<0.0005&&y<0.0005) {
			return true;
		}
		return false;
	}
	private static List<double[]> pointInfo(File f1,File f2) throws IOException {
		double[][] pathPoint = FirstTask.getData(f1);//·����Ϣ��
		double[][] stationPoint = GetStationsInfo.getData(f2);//վ��
//		double[][] drr = new double[pathPoint.length][2];
		List<double[]> list = new LinkedList<>();
		for (int i = 0; i < stationPoint.length; i++) {
			list.add(stationPoint[i]);
		}
//		drr[0] = stationPoint[0];
//		drr[1] = stationPoint[1];
		double[] para = new double[2];
		double d = 0;
		double max = -1;
		int temp = 1;
		int tmp = temp;
		for (int i = 0; i < list.size()-1; i++) {
//			System.out.println(i);
			para = line(list.get(i), list.get(i+1));
			for (int j = temp; j < pathPoint.length; j++) {
				d = distance(para, pathPoint[j]);
//				System.out.println("d:"+d);
				if(isSamePoint(pathPoint[j], list.get(i+1))) {
//					System.out.println("j:"+j+",temp:"+temp+",list.size:"+list.size());
//					System.out.println("same");
					if(max>0.0005) {
//						System.out.println("max:"+max);
						list.add(i+1, pathPoint[tmp]);
						i = i-1;
						max = -1;
					}else {
						temp = j+1;
					}
					tmp = temp;
					break;
				}else if(max<d){
						max = d;
						tmp = j;//tmp��¼���ֵ��λ��
				}
			}
		}
		System.out.println("list.size():"+list.size());
		return list;
	}
	public static void main(String[] args) throws IOException {
		File f1 = new File("I:\\�ո���\\20180422\\23··����Ϣ");
		File f2 = new File("I:\\�ո���\\20180422\\23·վ����Ϣ");
		BufferedWriter bw = new BufferedWriter(new FileWriter("I:\\�ո���\\20180422\\23·ɸѡ����.txt"));
		double[][] drr1 = FirstTask.getData(f1);
		double[][] drr2 = GetStationsInfo.getData(f2);
		List<double[]> list = pointInfo(f1, f2);
		for (int i = 0; i < list.size(); i++) {
			bw.write("["+list.get(i)[0]+","+list.get(i)[1]+"],\n");
			bw.flush();
		}
		bw.close();
		
	}
}
