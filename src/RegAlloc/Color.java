package RegAlloc;

import Graph.*;
import Temp.*;
import FlowGraph.*;
import java.util.*;
import Frame.*;

public class Color {
	NodeList nodeStack;
	java.util.Dictionary map = new java.util.Hashtable();;
	java.util.HashSet regs = new java.util.HashSet();;
	TempMap init;
	InterferenceGraph interGraph;
	Frame f;
	java.util.HashSet ret;
	
	Stack stack = new Stack();
	
	public Color(InterferenceGraph interGraph, Frame f, java.util.HashSet ret){
		this.interGraph = interGraph;
		this.f = f;
		this.ret = ret;
	}
	
	void color() {
		int number = 0;
		//遍历每个临时变量结点
		for (NodeList node=interGraph.nodes(); node != null;node = node.tail) {
			++number;
			//得到结点所对应的临时变量 temp
			Temp temp = interGraph.gtemp(node.head);
			//如果 temp 已经被分配了寄存器
			if (init.tempMap(temp) != null) {
				--number;
				stack.push(node.head); //将改结点压入堆栈
				map.put(temp, temp); //放入分配列表 map 中，它们的寄存器就是本身了
				//删除从该结点出发的所有边
				for (NodeList adj = node.head.succ(); adj != null; adj = adj.tail)
					interGraph.rmEdge(node.head, adj.head);
			}
		}
		//剩下是 number 个还没有被分配寄存器的
		for (int i = 0; i < number; ++i) {
			Node node = null;
			int max = -1;
			//再次遍历每个临时变量结点
			for (NodeList n = interGraph.nodes(); n != null; n = n.tail)
				if (init.tempMap(interGraph.gtemp(n.head)) == null
						&& !isInStack(n.head))
				{ //没有被分配寄存器且不在堆栈中
					int num = n.head.outDegree(); //出度
					if (max < num && num < regs.size()) 
					{
						//找到一个度最大的且小于寄存器数目的结点
						max = num;
						node = n.head;
					}
				}
			if (node == null) { //度大于等于寄存器数目，溢出
				System.err.println("Color.color() : register spills.");
				break;
			}
			//否则继续推入堆栈并移去从不在堆栈中的结点指向该结点的所有边
			stack.push(node);
			for (NodeList adj = node.pred(); adj != null; adj = adj.tail)
				if (!isInStack(adj.head))
					interGraph.rmEdge(adj.head, node);
		}
		//接下来开始分配那 number 个没有分配寄存器的临时变量，它们处在栈顶
		for (int i = 0; i < number; ++i) {
			Node node = (Node) stack.pop(); //弹出一个
			Set available = new HashSet(regs); //可供分配寄存器列表
			for (NodeList adj = node.succ(); adj != null; adj = adj.tail) {
				//从可供分配寄存器列表中移除该结点指向的某个结点所代表的寄存器
				available.remove(map.get(interGraph.gtemp(adj.head)));
			}
		//取剩下的一个作为寄存器
		Temp reg = (Temp) available.iterator().next();
		//加入寄存器分配表
		map.put(interGraph.gtemp(node), reg);
		}
	}
	
	boolean isInStack(Node node){
		int pos = stack.search(node);
		if(pos == -1)
			return false;
		else
			return true;
	}
}
