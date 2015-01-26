package Obe.Util;

import java.util.HashMap;

import Obe.Util.common.GetPropertiesUtils;

public class Readt_r {
	private String dir = GetPropertiesUtils.getoutPutDir()+"/topic_root_root.txt";
	private HashMap<String,String> relation = new HashMap<String,String>();

	public HashMap<String,String> getRelation(){
		file f = new file();
		f.setfilepath(dir);f.read();
		String text = f.returnS();
		String[] t = text.split("###");
		for(int i=2;i<t.length;i++){
			String[] str = t[i].split("-AND-");
			relation.put(str[0].trim(),str[1].trim());
		}
		return relation;
	}
}
