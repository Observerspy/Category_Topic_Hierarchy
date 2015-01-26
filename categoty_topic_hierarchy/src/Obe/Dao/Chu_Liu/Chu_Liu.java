package Obe.Dao.Chu_Liu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

public class Chu_Liu {
	private HashMap<Integer,Node> nMap = new HashMap<Integer, Node>();
	private HashMap<String, Double> eback = new  HashMap<String, Double>();
	private HashMap<String, Edge> eback2 = new  HashMap<String, Edge>();
	private Stack<HashMap<Integer,Node>> stack = new Stack<HashMap<Integer,Node>>();
	private Stack<Integer> stackC = new Stack<Integer>();

	private int time = 0;
	private HashMap<Integer, HashMap<Integer,Node>> CMap = new  HashMap<Integer, HashMap<Integer,Node>>();


	
	public List<Edge> Directed_MST(List<Node> n,List<Edge> e,int root){
		for(int i=0;i<e.size();i++){
			Edge ed = e.get(i);
			eback.put(ed.getSource()+"->"+ed.getTarget(),ed.getWeight());
			eback2.put(ed.getSource()+"->"+ed.getTarget(),ed);

		}
		nMap.clear();
		for(int i=0;i<n.size();i++){
			if(n.get(i).getConcept().equals("root"))
				root = n.get(i).getID();
			nMap.put(n.get(i).getID(), n.get(i));
		}
		//1. find the max IN edge to create G_M
	     HashMap<Integer,Integer> viss = new HashMap<Integer, Integer>();
		 HashMap<Integer,Integer> vist = new HashMap<Integer, Integer>();

		for(int i=0;i<e.size();i++){
			Edge edge = e.get(i);
			int s = edge.getSource();
			int t = edge.getTarget();
			Node source = nMap.get(s);
			Node target = nMap.get(t);
			
			if(viss.containsKey(s)){
				List<Integer> l = source.getChild();
				l.add(target.getID());
				source.setChild(l);
				List<Double> ll = source.getOutweight();
				ll.add(edge.getWeight());
				source.setOutweight(ll);
			}
			else{
				List<Integer> l = new ArrayList<Integer>();
				l.add(target.getID());
				source.setChild(l);
				List<Double> ll = new ArrayList<Double>();
				ll.add(edge.getWeight());
				source.setOutweight(ll);
				viss.put(s, 0);
			}			
			nMap.put(source.getID(), source);
			
			if(vist.containsKey(t)){
				List<Integer> l1 = target.getParent();
				l1.add(source.getID());
				target.setParent(l1);
				List<Double> ll1 = target.getInweight();
				ll1.add(edge.getWeight());
				target.setInweight(ll1);
			}
			else{
				List<Integer> l1 = new ArrayList<Integer>();
				l1.add(source.getID());
				target.setParent(l1);
				List<Double> ll1 =  new ArrayList<Double>();
				ll1.add(edge.getWeight());
				target.setInweight(ll1);
				vist.put(t, 0);
			}
			nMap.put(target.getID(), target);
			
			if(target.getID()!=root&&edge.getWeight()>=target.getCost()&&s!=t){
				target.setCost(edge.getWeight());
				target.setPre(source.getID());
				nMap.put(target.getID(), target);
			}
		}
		
		int circle = 0;
		//update the list
		n.clear();
		Iterator<Entry<Integer,Node>> iter = nMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Node t = (Node) entry.getValue();
			n.add(t);
			if(t.getID()!=root){
			    System.out.println("min edges"+nMap.get(t.getPre()).getID()+"--->"+t.getID()+" "+t.getCost());
			    }
		}
		
		//check whether G_M has circle
		HashMap<Integer,Integer> C = new HashMap<Integer,Integer>();
		HashMap<Integer,Node> circleC = new HashMap<Integer,Node>();
		double sumC = 0.0;int key = 0;
		for(int i=0;i<n.size();i++){
			cleanVIS(nMap);
		    Node  node = nMap.get(n.get(i).getID());
			while(node.getID()!=root&&node.getVisit()==0&&node.getCircle()==0){
				node.setVisit(1);
				nMap.put(node.getID(), node);
				node = nMap.get(node.getPre());
			}
			if(node.getID()!=root&&node.getCircle()==0){//in circle
				circle++;key = 1;
				node.setCircle(circle);
				circleC.put(node.getID(),node);
				C.put(node.getPre(), node.getID());
				System.out.print("circle "+(time+1)+" "+node.getID());
				sumC += eback.get(node.getPre()+"->"+node.getID());
				nMap.put(node.getID(), node);
				Node nodepre = nMap.get(node.getPre());
				while(nodepre.getID()!=node.getID()){
					System.out.print("->"+nodepre.getID());
					circleC.put(nodepre.getID(),nodepre);
					nodepre.setCircle(circle);
					nMap.put(nodepre.getID(), nodepre);
					C.put(nodepre.getPre(), nodepre.getID());
					sumC += eback.get(nodepre.getPre()+"->"+nodepre.getID());

					nodepre = nMap.get(nodepre.getPre());

				}
				C.put(nodepre.getPre(), nodepre.getID());
				System.out.println();
				break;
			}
		}
		
		HashMap<Integer,Node> nMapb = new HashMap<Integer, Node>();
		Iterator<Entry<Integer,Node>> iter1 = nMap.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			Node t = (Node) entry.getValue();
			Node n1 = new Node();
			n1.setID(t.getID());
			n1.setConcept(t.getConcept());
			n1.setChild(t.getChild());
			n1.setCircle(t.getCircle());
			n1.setCost(t.getCost());
			n1.setFromC(t.getFromC());
			n1.setInweight(t.getInweight());
			n1.setOutweight(t.getOutweight());
			n1.setParent(t.getParent());
			n1.setPre(t.getPre());
			n1.setToC(t.getToC());
			n1.setVisit(t.getVisit());
			nMapb.put(n1.getID(), n1);
		}
		stack.push(nMapb);
		//2.if hasn't circle,then return G_M
		if(key==0){	
			double sum = 0.0;
			List<Edge> eL = new ArrayList<Edge>();
			for(int i=0;i<n.size();i++){
				if(n.get(i).getCost()!=-Double.MAX_VALUE)
					sum += n.get(i).getCost();
				String str = n.get(i).getPre()+"->"+n.get(i).getID();
				Edge e1 = eback2.get(str);
				if(e1!=null)
					eL.add(e1);
				
			}
			System.out.println("weight:"+sum);
			return eL;
		}


		//4.contract the circle use contract(G,C,s),return G_C
		e = contract(n,e,circleC,root,sumC);
		//update list
		n.clear();
		Iterator<Entry<Integer,Node>> iter11 = nMap.entrySet().iterator();
		while (iter11.hasNext()) {
			Map.Entry entry = (Map.Entry) iter11.next();
			Node t = (Node) entry.getValue();
			n.add(t);
		}
		
		//5.recursively using Chu_liu_edmonds(G_C,s) return y
		e = Directed_MST(n,e,root);
		

		//6.find a node x inC,x' is a node outside C,x'' is a node inside C
		//put x'->x in the new graph and remove x''-x,then return new graph
		nMap = stack.pop();int tt = stackC.pop();
		List<Edge> eL = new ArrayList<Edge>();
		eL.addAll(e);int Cin = -Integer.MAX_VALUE;List<Integer> out = null;
		
		for(int i=0;i<e.size();i++){ 
			Edge edge = e.get(i);
			int s = edge.getSource();
			int t = edge.getTarget();
			if(t==tt){
			Node in = nMap.get(s);
			Node target = nMap.get(t);
			if(target.getConcept().equals("circel!")){
				circleC = CMap.get(target.getID());

				out = getOut(s,t,eL);
				eL = remove(s,t,eL);
				
				Edge edg = new Edge();
				edg.setSource(s);
				edg.setTarget(in.getToC());
				Cin = in.getToC();
				eL.add(edg);break;
			}
			}
		}
		
		
		HashMap<Integer,Integer> visC = new HashMap<Integer,Integer>();
		visC.put(Cin,0);
		Iterator<Entry<Integer, Node>> iter111 = circleC.entrySet().iterator();
		while (iter111.hasNext()) {
			Map.Entry entry = (Map.Entry) iter111.next();
			Node c = (Node) entry.getValue();
			List<Integer> l = c.getParent();
			for(int i1=0;i1<l.size();i1++){
				if(Cin==l.get(i1)&&!visC.containsKey(c.getID()))
				{
					Edge edg = new Edge();
					edg.setSource(Cin);
					edg.setTarget(c.getID());
					Cin = c.getID();
					visC.put(Cin, 0);
					eL.add(edg);
					iter111 = circleC.entrySet().iterator();
					break;
				}
			}
		}
		for(int q=0;q<out.size();q++){
		if(out.get(q)!=-Integer.MAX_VALUE){
			Node outn = nMap.get(out.get(q));
			Edge edg = new Edge();
		    edg.setSource(outn.getFromC());
		    edg.setTarget(out.get(q));
		    eL.add(edg);
		}
		}
		return eL;
	}
	
	private List<Integer> getOut(int s2, int t2, List<Edge> eL) {
		List<Integer> t = new ArrayList<Integer>();
		for(int i=0;i<eL.size();i++){ 
			Edge edge = eL.get(i);
			int s = edge.getSource();
			int t1 = edge.getTarget();
			if(s==t2){
				t.add(t1);
			}				
		}		
		return t;
	}

	private List<Edge> remove(int s2, int t2, List<Edge> eL) {
		for(int i=0;i<eL.size();i++){ 
			Edge edge = eL.get(i);
			int s = edge.getSource();
			int t = edge.getTarget();
			if(s==s2&&t==t2){
				eL.remove(i);
				i--;
			}				
		}
		for(int i=0;i<eL.size();i++){ 
			Edge edge = eL.get(i);
			int s = edge.getSource();
			if(s==t2){
				eL.remove(i);
				i--;
			}				
		}
		return eL;
	}

	private List<Edge> contract(List<Node> n, List<Edge> e, HashMap<Integer, Node> circleC, int root, double sumC) {
		//1. remove all nodes and related edges in circle C,get G_C
		int max_id = 0;
		for(int i=0;i<n.size();i++){
			if(circleC.containsKey(n.get(i).getID())){
				n.remove(i);
				i--;
			}
		}
		for(int i=0;i<e.size();i++){
			Edge edge = e.get(i);
			int s = edge.getSource();
			int t = edge.getTarget();
			if(circleC.containsKey(s)||circleC.containsKey(t)){
				e.remove(i);
				i--;
			}			
		}
		nMap.clear();
		for(int i=0;i<n.size();i++){
			if(max_id<n.get(i).getID())
				max_id = n.get(i).getID();
			if(n.get(i).getConcept().equals("root"))
				root = n.get(i).getID();
			nMap.put(n.get(i).getID(), n.get(i));
		}

			
		//2.add node c to G_C, which represent circle C
		Node C = new Node();
		time++;
		int Cid = -1*time;
		C.setID(Cid);
		C.setConcept("circel!");
		n.add(C);
		stackC.push(Cid);
		nMap.put(C.getID(),C);

		//3.add c->x edge to G_C,which is the max out edge in circle C
		for(int i=0;i<n.size()-1;i++){
			int flag = 0;Edge edge = new Edge();double max =-Double.MAX_VALUE;
			Iterator<Entry<Integer,Node>> iter = circleC.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Node node = (Node) entry.getValue();
				List<Integer> l = node.getChild();
				List<Double> ll = node.getOutweight();
				for(int j=0;j<l.size();j++){
					if(l.get(j)==n.get(i).getID()){
						if(ll.get(j)>max){
							max = ll.get(j);
							flag = 1;
							edge.setSource(Cid);
							edge.setTarget(l.get(j));
							edge.setWeight(ll.get(j));
							
							Node newn = nMap.get(l.get(j));
							newn.setFromC(node.getID());
							nMap.put(newn.getID(), newn);
							Node nn = circleC.get(node.getID());
							nn.setToC(newn.getID());
							circleC.put(node.getID(), nn);
						}
					}
				}
			}
			if(flag==1)
				e.add(edge);
		}


		//4.add x->c edge to G_C,which is the max in edge
		//the score of circle c just sum the edges in c 
		for(int i=0;i<n.size()-1;i++){
			Edge edge = new Edge();double max = -Double.MAX_VALUE;int flag = 0;
			Node node = n.get(i);
			List<Integer> l = node.getChild();
			List<Double> ll = node.getOutweight();
			for(int j=0;j<l.size();j++){
				if(circleC.containsKey(l.get(j))){
					Node c = circleC.get(l.get(j));
					double val = ll.get(j)-c.getCost()+sumC;
					if(val>max){
						flag = 1;
						edge.setSource(node.getID());
						edge.setTarget(Cid);
						edge.setWeight(val);
						
						Node newn = nMap.get(node.getID());
						newn.setToC(c.getID());
						nMap.put(newn.getID(), newn);
						
						
						c.setFromC(newn.getID());
						circleC.put(c.getID(), c);
					}
				}
			}
			if(flag==1)
				e.add(edge);
		}
		CMap.put(Cid, circleC);
		return e;
	}

	private void cleanVIS(HashMap<Integer, Node> nMap2) {
		Iterator<Entry<Integer,Node>> iter = nMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Node t = (Node) entry.getValue();
			t.setVisit(0);
			nMap.put(t.getID(), t);
		}
	}
}
