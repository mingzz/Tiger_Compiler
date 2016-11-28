package Mips;

import Frame.Access;
import Tree.*;

public class InFrame extends Access {
	private MipsFrame frame;	//֡
	private int offset;  		//ƫ����
	
	public InFrame(MipsFrame frame, int offset) 
	{
		this.frame = frame;
		this.offset = offset;
	}
	// ��fpΪ��ʼ��ַ���ر�����IR�����
	public Tree.Exp exp(Exp framePtr) 
	{
		return new MEM(new BINOP(BINOP.PLUS, framePtr, new CONST(offset)));
	}
	
	// ��spΪ��ʼ��ַ���ر�����IR�����
	public Tree.Exp expFromStack(Exp stackPtr)
	{
		return new MEM(new BINOP(BINOP.PLUS, stackPtr, new CONST(offset - frame.allocDown - Translate.Library.WORDSIZE)));
	}
}
