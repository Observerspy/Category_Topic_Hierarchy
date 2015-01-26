package Obe.Dao.Incre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Obe.Util.GetTotalMap;

public class Main {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		 for(int i=1;i<10;i++){
		 List<String> l = GetTotalMap.getNew().getL();
		 IncreCluster startCluster = new IncreCluster();
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
			String d = df.format(0.05*i+0.05);
		 startCluster.setKey(2);startCluster.setlamda(Double.valueOf(d));
		 startCluster.start(l);
		 }
	}



}
