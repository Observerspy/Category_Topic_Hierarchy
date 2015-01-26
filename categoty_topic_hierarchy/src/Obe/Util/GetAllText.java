package Obe.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Obe.Dto.Concept;
import Obe.Util.common.GetPropertiesUtils;

public class GetAllText {
	private String dir = GetPropertiesUtils.getoutPutDir()+"/F_cluster_map"+GetPropertiesUtils.getEx();
	private String content = "";
	private HashMap<String,Integer> map = new HashMap<String, Integer>();
	private static GetAllText all = null;
	private int length;
	private List<Concept> l = new ArrayList<Concept>();

	
	public GetAllText(){
		System.out.println("reading...");
		file f = new file();
		f.setfilepath(dir);f.read();
		String text = f.returnS();
		String[] t = text.split("###");
		System.out.println("geting all text");
		String tempt = "";Concept con = null;
		for(int i=1;i<t.length;i++){
			if(!t[i].startsWith("ID:")){
				if(t[i].startsWith("#TEXT:"))
					if(t[i].split("TEXT:").length>1)
						t[i] = t[i].split("TEXT:")[1];
				content += t[i];
				tempt += t[i];
				con.setText(tempt.toLowerCase());
			}
			else{
				//if(con!=null){
				if(con!=null&&!con.getText().equals("#text:")){
				l.add(con);}
				String[] str = t[i].split("ID:");
				String[] id = str[1].split("#",2);
				con = new Concept();
				con.setID(Integer.valueOf(id[0].trim()));
				con.setConcept(id[1].trim().toLowerCase());
				tempt = "";
			}
		}
		l.add(con);
		System.out.println("creating text map");
		String[] c = content.split(" ");
		length = c.length;
		for(int i=0;i<c.length;i++){
			if(isNumeric(c[i])||isSingleLeter(c[i]))
				continue;
			if(!c[i].equals("")||!c[i].equals(" ")||c[i]!=null)
				if(map.containsKey(c[i])){
					int val = map.get(c[i]);
					map.put(c[i], val+1);
					}
				else{
					map.put(c[i], 1);
					}
		}
		Iterator<Entry<String,Integer>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			int num = (Integer)entry.getValue();
			String str = (String)entry.getKey();

			if(num<3)
				iter.remove();
		}
		System.out.println("get all text finished!");
	}

	private boolean isSingleLeter(String str) {
		 Pattern pattern = Pattern.compile("[a-zA-Z]{1}"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		  // System.out.println("remove"+str);
		   return true; 
	}

	private boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   //System.out.println("remove"+str);
		   return true; 
		}
	
	public String getContent() {
		return content;
	}

	public HashMap<String,Integer> getMap() {
		return map;
	}
	
	public static GetAllText getNew(){
		if(all==null)
			all = new GetAllText();
		return all;
	}

	public int getLength() {
		return length;
	}
	
	public List<Concept> getL() {
		return l;
	}

}
