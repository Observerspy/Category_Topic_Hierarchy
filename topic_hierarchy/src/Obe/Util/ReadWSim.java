package Obe.Util;

import java.util.HashMap;

import Obe.Util.common.GetPropertiesUtils;

public class ReadWSim {
	
	private static ReadWSim r = null;
	private static HashMap<String,Double> map = new HashMap<String, Double>();

	public ReadWSim() {
		System.out.println("reading...");
		LoadBigFile l = new LoadBigFile();
		l.setPath(GetPropertiesUtils.getoutPutDir()+"/new_sim.txt");
		String ss = "";
		try {
			ss = l.Load();
		} catch (Exception e) {
			System.out.println("load error!");
			e.printStackTrace();
		}
		String[] text = ss.split("\r\n");
		for(int i=2;i<text.length;i++){
			Double val = Double.valueOf(text[i].split("sim:")[1].trim());
			String[] str = text[i].split("sim:")[0].split(" -AND- ");
			String a = str[0].split(":")[1].trim();
			String b = str[1].split(":")[1].trim();
			String s = a+"+"+b;
			map.put(s, val);
		}
		System.out.println("newsim load finished!");

	}
	
	public static ReadWSim getNew() {
		if(r==null)
			r = new ReadWSim();
		return r;
	}

	public  HashMap<String,Double> getMap() {
		return map;
	}

}
