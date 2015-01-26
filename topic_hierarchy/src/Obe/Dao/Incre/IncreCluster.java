package Obe.Dao.Incre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Obe.Util.ReadWSim;
import Obe.Util.Readt_r;
import Obe.Util.file;
import Obe.Util.common.GetPropertiesUtils;

public class IncreCluster {
	private  List<Cluster> list = new ArrayList<Cluster>();//collection of C
	private HashMap<String,String> relation = new HashMap<String, String>();
	private  double lamda = 0; 
	private  int key = 0;
	
	public void start(List<String> l) throws IOException {
		 file f = new file();
		 Readt_r r = new Readt_r();
		 relation = r.getRelation();//check whether in the same file
	     f.setoutpath(GetPropertiesUtils.getoutPutDir()+"/IncreMean/IncreCluster_"+lamda+".txt");f.delete();
	     System.out.println("clustering...");
		 f.write("clustering:\r\n");
		 //initial C0
		 System.out.println("initial first cluster");

		 Cluster cl = new Cluster();
		 List<String> concept = new ArrayList<String>();
		 concept.add(l.get(0));
		 cl.setConcept(concept);
		 list.add(cl);
		 //for each concept
		 for(int i=1;i<l.size();i++){
			 System.out.println("concept_"+i);
			 String str = l.get(i);
			 //traverse collection
			 List<Double> s = new ArrayList<Double>();
			 for(int j=0;j<list.size();j++){
				 System.out.println("list_"+j);
				 Cluster clu = list.get(j);
				 List<String> conceptT = clu.getConcept();
				 List<Double> sim = new ArrayList<Double>();
				 //traverse each concept in collection
				 for(int k=0;k<conceptT.size();k++){
					 String ct = conceptT.get(k);
					 double val = getsim(str,ct);
					 if(val==0)
					 {
						 System.out.println(str+"+"+ct);
					 }
					 sim.add(val);
				 }
				 double weight = 0;
				if(key==0)
				  weight = findMax(sim);//find max
				 else if(key==1)
				  weight = findMaxMean(sim);//find mean of max and min 
				 else if(key==2)
				  weight = findAllMean(sim);//find all mean
				 s.add(weight);

			 }
			//sort
			 HashMap<Double,Integer> x = new HashMap<Double, Integer>();
			 for(int j=0;j<s.size();j++){
				 x.put(s.get(j), j);
			 }
			 Collections.sort(s);
			 Collections.reverse(s); 
			 int k = 0;int flag = 0;
			 while(s.get(k)>lamda){
				 Cluster c = list.get(x.get(s.get(k)));
				 List<String> cT = c.getConcept();
				 if(!check(str,cT)){//whether root is not same
					 flag = 1;
					 cT.add(str);
					 c.setConcept(cT);
					 list.set(x.get(s.get(k)),c);
					 break;
				 }
				 else
					 k++;
			 }
			 if(flag==0){
				 Cluster c = new Cluster();
				 List<String> con = new ArrayList<String>();
				 con.add(str);
				 c.setConcept(con);
				 list.add(c);
			 }
		 }
		 System.out.println("cluster finished!");
		 for(int j=0;j<list.size();j++){
			 Cluster clu = list.get(j);
			 System.out.println("cluster id: "+j+"concept: ");
			 f.write("cluster id: "+j+"concept: \r\n");
			 for(int i=0;i<clu.getConcept().size();i++){
				 System.out.print(clu.getConcept().get(i)+"#");
				 f.write(clu.getConcept().get(i)+"#");
				 }
			 System.out.println();
			 f.write("\r\n");
		 }
	}

	private boolean check(String str, List<String> cT) {
		String root = relation.get(str).trim();
		for(int i=0;i<cT.size();i++){
			 String root1 = relation.get(cT.get(i)).trim();
			 if(root.equals(root1))
				 return true;
		}
		return false;
	}

	private double findAllMean(List<Double> sim) {
		double sum = 0.0;
		for(int i=0;i<sim.size();i++)
			sum += sim.get(i);
		return sum/(double)sim.size();
	}
	private double findMaxMean(List<Double> sim) {
		double max = Collections.max(sim);
		double min = Collections.min(sim);
		return (max+min)/(double)2.0;
	}
	private double findMax(List<Double> sim) {
		double max = Collections.max(sim);
		return max;
	}
	private double getsim(String a, String b) {
		 HashMap<String,Double> map = ReadWSim.getNew().getMap();

		 double val = 0.0;
		 String str = a +"+" +b;
		 String str2 = b +"+" +a;
		 if(map.containsKey(str))
			 val =  map.get(str);
		 else if(map.containsKey(str2))
			 val =  map.get(str2);
		 return val;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	public double getlamda() {
		return lamda;
	}

	public void setlamda(double lamda) {
		this.lamda = lamda;
	}
}
