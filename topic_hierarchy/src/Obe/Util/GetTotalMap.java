package Obe.Util;

import java.util.ArrayList;
import java.util.List;

import Obe.Dto.Concept;
import Obe.Util.common.GetPropertiesUtils;

public class GetTotalMap {
	private List<String> l = new ArrayList<String>();
	private static GetTotalMap gtm = null;
	private String dir = GetPropertiesUtils.getoutPutDir()+"/total_map2.txt";

	public GetTotalMap(){
		System.out.println("reading...");
		file f = new file();
		f.setfilepath(dir);f.read();
		String text = f.returnS();
		String[] t = text.split("###");
		System.out.println("geting total map");
		for(int i=2;i<t.length;i++){
			String str = t[i].split(":")[1].trim();
			l.add(str);
		}
		System.out.println("get total map finished!");

	}
	
	public List<String> getL() {
		return l;
	}
	
	public static GetTotalMap getNew(){
		if(gtm==null)
			gtm = new GetTotalMap();
		return gtm;
	}
}
