package RegAlloc;

import FlowGraph.*;


public class NodeInfo {
	java.util.Set in = new java.util.HashSet(); //来指令前的活性变量
	java.util.Set out = new java.util.HashSet(); //出指令后的活性变量 － 即活跃变量
	java.util.Set use = new java.util.HashSet(); //某指令使用的变量 - 赋值号右边
	java.util.Set def = new java.util.HashSet(); //某指令定义的变量 - 赋值号左边
	public NodeInfo(Graph.Node node){
		for(Temp.TempList i = ((FlowGraph)node.mygraph).def(node); i != null; i = i.tail){
			def.add(i.head);
		}
		for(Temp.TempList i = ((FlowGraph)node.mygraph).use(node); i != null; i = i.tail){
			use.add(i.head);
		}
	}
}
