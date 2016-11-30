package RegAlloc;

import Graph.*;
import java.util.*;
import Temp.*;
import RegAlloc.*;
import FlowGraph.*;

public class Liveness extends InterferenceGraph {
	java.util.Dictionary info = new java.util.Hashtable();
	java.util.Dictionary liveMap = new java.util.Hashtable();
	java.util.Dictionary tnode = new java.util.Hashtable();
	java.util.Dictionary temp = new java.util.Hashtable();
	//java.util.Dictionary temp = new java.util.Hashtable();
	MoveList movelist = null;
	FlowGraph flowGraph;
	//NodeInfo inf = new NodeInfo();
	
	//RegAlloc.Liveness 的对外接口,输入流图,进行活性分析,并输出干扰图
	public Liveness(FlowGraph flowGraph) {
		this.flowGraph = flowGraph;
		InitNodeInfo(); //初始化
		calculateLiveness(); //计算活性变量
		buildGraph(); //生成干扰图
	}
	
	@Override
	public Node tnode(Temp temp) {
		Node n = (Node) tnode.get(temp);
		if (n == null)
			return newNode(temp);
		return n;
	}

	@Override
	public Temp gtemp(Node node) {
		//return (Temp)temp.get(node);
		
		if (!(node instanceof TempNode))
			throw new Error("Node "+node.toString()+" not a member of graph.");
		else 
			return ((TempNode)node).temp;
			
	}

	@Override
	public MoveList moves() {
		return movelist;
	}
	
	public Node newNode(Temp temp){
		TempNode n = new TempNode(this, temp);
		tnode.put(temp, n);
		return n;
	}
	
	class TempNode extends Node {
		Temp temp;
		TempNode(Graph g, Temp t) {
		    super(g);
		    temp = t;
		}
	}
	
	void InitNodeInfo(){
		for(NodeList node=flowGraph.nodes();node != null; node=node.tail){
			NodeInfo p = new NodeInfo(node.head);
			info.put(node.head, p);
		}
	}
	
	void calculateLiveness() {
		boolean done = false;
		do {
			done = true;
			for(NodeList node=flowGraph.nodes();node != null; node=node.tail) {
				//遍历流图所有指令
				NodeInfo inf = (NodeInfo) info.get(node.head);//更新前的活性信息
				//inf = (NodeInfo) info.get(node.head);
				//等式 1
				Set in1 = new HashSet(inf.out);
				in1.removeAll(inf.def);
				in1.addAll(inf.use);
				if (!in1.equals(inf.in)) done = false; //测试是否完成
				inf.in = in1; //更新 in
				//等式 2
				Set out1 = new HashSet();
				for (NodeList succ = node.head.succ(); succ != null; succ = succ.tail) {
					NodeInfo i = (NodeInfo) info.get(succ.head);
					out1.addAll(i.in);
				}
				if (!out1.equals(inf.out)) done = false; //测试是否完成
				inf.out = out1; //更新 out
			}
		} while (!done);
		//生成 liveMap
		for (NodeList node = flowGraph.nodes();node != null;node = node.tail) {
			TempList list = null;
			//得到活性信息中活跃变量的迭代器
			Iterator i = ((NodeInfo) info.get(node.head)).out.iterator();
			while (i.hasNext()) {
				Temp j = (Temp)i.next();
				list = new TempList((Temp)j, list);
			}
			if (list != null) liveMap.put(node.head, list);
		}
	}
	
	void buildGraph() {
		Set temps = new HashSet(); //包含流图中所有有关的变量(使用的和定义的)
		//生成 temps
		for (NodeList node = flowGraph.nodes(); node != null; node = node.tail)
		{
			for (TempList t = flowGraph.use(node.head); t != null; t = t.tail)
				temps.add(t.head);
			for (TempList t = flowGraph.def(node.head); t != null; t = t.tail)
				temps.add(t.head);
		}
		//生成 tnode  
		Iterator i = temps.iterator();
		while (i.hasNext()) newNode((Temp) i.next());
		//生成干扰图
		for (NodeList node = flowGraph.nodes(); node != null; node = node.tail)
			//遍历流图中每一条指令
			for (TempList t = flowGraph.def(node.head); t != null; t = t.tail)
				//遍历指令中定义的所有变量
				for (TempList t1 = (TempList) liveMap.get(node.head); t1 != null; t1 = t1.tail)
					//遍历指令的所有活跃变量
					//应用以上规则 a,b,尝试添加边
					if (t.head != t1.head //防止自回路
						&& !(flowGraph.isMove(node.head)
						&& flowGraph.use(node.head).head == t1.head)) 
					{
							addEdge(tnode(t.head), tnode(t1.head));
							addEdge(tnode(t1.head), tnode(t.head)); //无向图,双向加边
					}
	}
		
}
