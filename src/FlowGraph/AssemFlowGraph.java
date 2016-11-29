package FlowGraph;


import Graph.*;
import Temp.*;
import Assem.*;

public class AssemFlowGraph extends FlowGraph {
	java.util.Dictionary represent = new java.util.Hashtable();
	
	//���ݻ��ָ�����ͼ
	public AssemFlowGraph(InstrList instrs) 
	{
		java.util.Dictionary labels = new java.util.Hashtable(); //��ű�
		//��ӽ��ͱ��
		for (InstrList i = instrs; i != null; i = i.tail) {
			Node node = newNode();
			represent.put(node, i.head);
			if (i.head instanceof LABEL)
			labels.put(((LABEL) i.head).label, node);
		}
		//��ӱ�
		for (NodeList node = nodes(); node != null; node = node.tail) {
			Targets next = instr(node.head).jumps(); //��ת��ű�
			if (next == null) { //û����ת,��ǰָ�����һָ����һ����
				if (node.tail != null) addEdge(node.head, node.tail.head);
			} else //����ǰָ���������ת�������
				for (LabelList l = next.labels; l != null; l = l.tail)
					addEdge(node.head, (Node) labels.get(l.head));
		}
	}
	
	//����ĳ����ʾ�Ļ��ָ��
	public Instr instr(Node n) {return (Instr) represent.get(n);}
	
	//����ĳ��㶨��ı���
	public TempList def(Node node) {return instr(node).def();}
	
	//����ĳ���ʹ�õı���
	public TempList use(Node node) {return instr(node).use();}
	
	//�Ƿ��� move ָ��
	public boolean isMove(Node node) 
	{
		Instr instr = instr(node);
		return instr.assem.startsWith("move");
	}
}
