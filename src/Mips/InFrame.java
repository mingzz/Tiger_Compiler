package Mips;

import Frame.Access;
import Tree.*;

public class InFrame extends Access {
	private MipsFrame frame;	//帧
	private int offset;  		//偏移量
	
	public InFrame(MipsFrame frame, int offset) 
	{
		this.frame = frame;
		this.offset = offset;
	}
	// 以fp为初始地址返回变量得IR树结点
	public Tree.Exp exp(Exp framePtr) 
	{
		return new MEM(new BINOP(BINOP.PLUS, framePtr, new CONST(offset)));
	}
	
	// 以sp为初始地址返回变量的IR树结点
	public Tree.Exp expFromStack(Exp stackPtr)
	{
		return new MEM(new BINOP(BINOP.PLUS, stackPtr, new CONST(offset - frame.allocDown - Translate.Library.WORDSIZE)));
	}
}
