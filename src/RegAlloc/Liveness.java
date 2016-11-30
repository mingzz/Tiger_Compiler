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
	
	//RegAlloc.Liveness �Ķ���ӿ�,������ͼ,���л��Է���,���������ͼ
	public Liveness(FlowGraph flowGraph) {
		this.flowGraph = flowGraph;
		InitNodeInfo(); //��ʼ��
		calculateLiveness(); //������Ա���
		buildGraph(); //���ɸ���ͼ
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
				//������ͼ����ָ��
				NodeInfo inf = (NodeInfo) info.get(node.head);//����ǰ�Ļ�����Ϣ
				//inf = (NodeInfo) info.get(node.head);
				//��ʽ 1
				Set in1 = new HashSet(inf.out);
				in1.removeAll(inf.def);
				in1.addAll(inf.use);
				if (!in1.equals(inf.in)) done = false; //�����Ƿ����
				inf.in = in1; //���� in
				//��ʽ 2
				Set out1 = new HashSet();
				for (NodeList succ = node.head.succ(); succ != null; succ = succ.tail) {
					NodeInfo i = (NodeInfo) info.get(succ.head);
					out1.addAll(i.in);
				}
				if (!out1.equals(inf.out)) done = false; //�����Ƿ����
				inf.out = out1; //���� out
			}
		} while (!done);
		//���� liveMap
		for (NodeList node = flowGraph.nodes();node != null;node = node.tail) {
			TempList list = null;
			//�õ�������Ϣ�л�Ծ�����ĵ�����
			Iterator i = ((NodeInfo) info.get(node.head)).out.iterator();
			while (i.hasNext()) {
				Temp j = (Temp)i.next();
				list = new TempList((Temp)j, list);
			}
			if (list != null) liveMap.put(node.head, list);
		}
	}
	
	void buildGraph() {
		Set temps = new HashSet(); //������ͼ�������йصı���(ʹ�õĺͶ����)
		//���� temps
		for (NodeList node = flowGraph.nodes(); node != null; node = node.tail)
		{
			for (TempList t = flowGraph.use(node.head); t != null; t = t.tail)
				temps.add(t.head);
			for (TempList t = flowGraph.def(node.head); t != null; t = t.tail)
				temps.add(t.head);
		}
		//���� tnode  
		Iterator i = temps.iterator();
		while (i.hasNext()) newNode((Temp) i.next());
		//���ɸ���ͼ
		for (NodeList node = flowGraph.nodes(); node != null; node = node.tail)
			//������ͼ��ÿһ��ָ��
			for (TempList t = flowGraph.def(node.head); t != null; t = t.tail)
				//����ָ���ж�������б���
				for (TempList t1 = (TempList) liveMap.get(node.head); t1 != null; t1 = t1.tail)
					//����ָ������л�Ծ����
					//Ӧ�����Ϲ��� a,b,������ӱ�
					if (t.head != t1.head //��ֹ�Ի�·
						&& !(flowGraph.isMove(node.head)
						&& flowGraph.use(node.head).head == t1.head)) 
					{
							addEdge(tnode(t.head), tnode(t1.head));
							addEdge(tnode(t1.head), tnode(t.head)); //����ͼ,˫��ӱ�
					}
	}
		
}
