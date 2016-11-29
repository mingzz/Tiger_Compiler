package RegAlloc;

import FlowGraph.*;


public class NodeInfo {
	java.util.Set in = new java.util.HashSet(); //��ָ��ǰ�Ļ��Ա���
	java.util.Set out = new java.util.HashSet(); //��ָ���Ļ��Ա��� �� ����Ծ����
	java.util.Set use = new java.util.HashSet(); //ĳָ��ʹ�õı��� - ��ֵ���ұ�
	java.util.Set def = new java.util.HashSet(); //ĳָ���ı��� - ��ֵ�����
	public NodeInfo(Graph.Node node){
		for(Temp.TempList i = ((FlowGraph)node.mygraph).def(node); i != null; i = i.tail){
			def.add(i.head);
		}
		for(Temp.TempList i = ((FlowGraph)node.mygraph).use(node); i != null; i = i.tail){
			use.add(i.head);
		}
	}
}
