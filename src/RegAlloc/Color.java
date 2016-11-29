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
		//����ÿ����ʱ�������
		for (NodeList node=interGraph.nodes(); node != null;node = node.tail) {
			++number;
			//�õ��������Ӧ����ʱ���� temp
			Temp temp = interGraph.gtemp(node.head);
			//��� temp �Ѿ��������˼Ĵ���
			if (init.tempMap(temp) != null) {
				--number;
				stack.push(node.head); //���Ľ��ѹ���ջ
				map.put(temp, temp); //��������б� map �У����ǵļĴ������Ǳ�����
				//ɾ���Ӹý����������б�
				for (NodeList adj = node.head.succ(); adj != null; adj = adj.tail)
					interGraph.rmEdge(node.head, adj.head);
			}
		}
		//ʣ���� number ����û�б�����Ĵ�����
		for (int i = 0; i < number; ++i) {
			Node node = null;
			int max = -1;
			//�ٴα���ÿ����ʱ�������
			for (NodeList n = interGraph.nodes(); n != null; n = n.tail)
				if (init.tempMap(interGraph.gtemp(n.head)) == null
						&& !isInStack(n.head))
				{ //û�б�����Ĵ����Ҳ��ڶ�ջ��
					int num = n.head.outDegree(); //����
					if (max < num && num < regs.size()) 
					{
						//�ҵ�һ����������С�ڼĴ�����Ŀ�Ľ��
						max = num;
						node = n.head;
					}
				}
			if (node == null) { //�ȴ��ڵ��ڼĴ�����Ŀ�����
				System.err.println("Color.color() : register spills.");
				break;
			}
			//������������ջ����ȥ�Ӳ��ڶ�ջ�еĽ��ָ��ý������б�
			stack.push(node);
			for (NodeList adj = node.pred(); adj != null; adj = adj.tail)
				if (!isInStack(adj.head))
					interGraph.rmEdge(adj.head, node);
		}
		//��������ʼ������ number ��û�з���Ĵ�������ʱ���������Ǵ���ջ��
		for (int i = 0; i < number; ++i) {
			Node node = (Node) stack.pop(); //����һ��
			Set available = new HashSet(regs); //�ɹ�����Ĵ����б�
			for (NodeList adj = node.succ(); adj != null; adj = adj.tail) {
				//�ӿɹ�����Ĵ����б����Ƴ��ý��ָ���ĳ�����������ļĴ���
				available.remove(map.get(interGraph.gtemp(adj.head)));
			}
		//ȡʣ�µ�һ����Ϊ�Ĵ���
		Temp reg = (Temp) available.iterator().next();
		//����Ĵ��������
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
