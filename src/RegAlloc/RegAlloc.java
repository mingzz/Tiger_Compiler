package RegAlloc;

import Frame.Frame;
import Temp.Temp;
import Temp.TempMap;
import Assem.InstrList;
import Assem.Instr;
import FlowGraph.*;

public class RegAlloc implements TempMap {
	InstrList instrs;
	
	public RegAlloc(Frame f, InstrList instrs) {
		this.instrs = instrs;
		FlowGraph flowGraph = new AssemFlowGraph(instrs);//���ݻ��ָ��������ͼ
		InterferenceGraph interGraph=new Liveness(flowGraph);//���Է���,����ͼ
		Color color = new Color(interGraph, f, f.registers());//��ɫ������Ĵ���
	}

	@Override
	public String tempMap(Temp t) {
		// TODO Auto-generated method stub
		return null;
	}
}
