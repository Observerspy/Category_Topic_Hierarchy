package Obe.Dto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Obe.Util.GetAllText;

public class Concept {
	private int ID;
	private String concept;
	private String text;
	private HashMap<String,Double> p = new HashMap<String, Double>();
	private HashMap<String,Integer> maps = new HashMap<String,Integer>();//a map for each concept

	public void initialP(){
		HashMap<String,Integer> map = GetAllText.getNew().getMap();//wordsmap and frequency
		Iterator<Entry<String,Integer>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String str = (String)entry.getKey();
			p.put(str, 0.0);
		}
	}
	
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public HashMap<String,Double> getP() {
		return p;
	}
	public void setP(HashMap<String,Double> p) {
		this.p = p;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public HashMap<String,Integer> getMaps() {
		return maps;
	}

	public void setMaps(HashMap<String,Integer> maps) {
		this.maps = maps;
	}

}
