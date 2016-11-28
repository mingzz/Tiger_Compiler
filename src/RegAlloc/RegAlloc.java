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
		FlowGraph flowGraph = new AssemFlowGraph(instrs);//根据汇编指令生成流图
		InterferenceGraph interGraph=new Liveness(flowGraph);//活性分析,干扰图
		Color color = new Color(interGraph, f, f.registers());//着色法分配寄存器
	}

	@Override
	public String tempMap(Temp t) {
		// TODO Auto-generated method stub
		return null;
	}
}
