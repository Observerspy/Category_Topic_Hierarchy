package Obe.Dao.Chu_Liu;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int ID;
	private String concept;
	private int pre;
	private int visit=0;//0 haven't visited
	private int circle=0;//0 no circle
	private double cost=-Double.MAX_VALUE;
	
	private List<Integer> child = new ArrayList<Integer>();
	private List<Double> outweight = new ArrayList<Double>();
	
	private List<Integer> parent = new ArrayList<Integer>();
	private List<Double> inweight = new ArrayList<Double>();
	private int toC = -Integer.MAX_VALUE;
	private int fromC = -Integer.MAX_VALUE;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public int getPre() {
		return pre;
	}
	public void setPre(int pre) {
		this.pre = pre;
	}
	public int getVisit() {
		return visit;
	}
	public void setVisit(int visit) {
		this.visit = visit;
	}
	public int getCircle() {
		return circle;
	}
	public void setCircle(int circle) {
		this.circle = circle;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public List<Integer> getChild() {
		return child;
	}
	public void setChild(List<Integer> child) {
		this.child = child;
	}
	public List<Double> getOutweight() {
		return outweight;
	}
	public void setOutweight(List<Double> outweight) {
		this.outweight = outweight;
	}
	public List<Integer> getParent() {
		return parent;
	}
	public void setParent(List<Integer> parent) {
		this.parent = parent;
	}
	public List<Double> getInweight() {
		return inweight;
	}
	public void setInweight(List<Double> inweight) {
		this.inweight = inweight;
	}
	public int getToC() {
		return toC;
	}
	public void setToC(int toC) {
		this.toC = toC;
	}
	public int getFromC() {
		return fromC;
	}
	public void setFromC(int fromC) {
		this.fromC = fromC;
	}

}
