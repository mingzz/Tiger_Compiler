package Mips;

import Frame.Access;
import Frame.AccessList;
import Frame.Frame;
import Mips.InFrame;
import Mips.InReg;
import Temp.*;
import Tree.Exp;
import Tree.ExpList;
import Tree.Stm;
import Util.BoolList;
import Assem.InstrList;
import Assem.OPER;

public class MipsFrame extends Frame {
	public int allocDown = 0;
	public TempList argRegs = new TempList(new Temp(5), new TempList(new Temp(6), new TempList(new Temp(7), new TempList(new Temp(8), null))));
	public java.util.ArrayList saveArgs = new java.util.ArrayList();
	static Temp fp = new Temp(0);
	static Temp sp = new Temp(1);
	static Temp ra = new Temp(2);
	static Temp rv = new Temp(3);
	static Temp zero = new Temp(4);
	private TempList calleeSaves = null;
	private TempList callerSaves = null; 
	private int numOfcalleeSaves = 8;
	
	public MipsFrame()
	{
		for (int i = 9; i<= 18; i++)
			callerSaves = new TempList(new Temp(i), callerSaves);
		for (int i = 19; i<= 26; i++)
			calleeSaves = new TempList(new Temp(i), calleeSaves);
	}
	
	public Temp FP()
	{
		return fp;
	}
	
	public Temp SP()
	{
		return sp;
	}
	
	public Temp RA()
	{
		return ra;
	}
	
	public Temp RV()
	{
		return rv;
	}
	
	public Tree.Exp externCall(String func, Tree.ExpList args)
	{
		return new Tree.CALL(new Tree.NAME(new Label(func)), args);
	}
	
	public java.util.HashSet registers()
	{
		java.util.HashSet ret = new java.util.HashSet();
		for (TempList tl = this.calleeSaves; tl != null; tl = tl.tail)
			ret.add(tl.head);
		for (TempList tl = this.callerSaves; tl != null; tl = tl.tail)
			ret.add(tl.head);
		return ret;
	}
	
	@Override
	public String tempMap(Temp t) {
		if (t.toString().equals("t0"))
			return "$fp";
		if (t.toString().equals("t1"))
			return "$sp";
		if (t.toString().equals("t2"))
			return "$ra";
		if (t.toString().equals("t3"))
			return "$v0";
		if (t.toString().equals("t4"))
			return "$zero";
		
		for (int i = 5; i <= 8; i++)
			if (t.toString().equals("t" + i))
			{
				int r = i - 5;
				return "$a" + r;
			}
		for (int i = 9; i <= 18; i++)
			if (t.toString().equals("t" + i))
			{
				int r = i - 9;
				return "$t" + r;
			}
		for (int i = 19; i <= 26; i++)
			if (t.toString().equals("t" + i))
			{
				int r = i - 19;
				return "$s" + r;
			}
		return null;
	}

	@Override
	public Frame newFrame(Label name, BoolList formals) {
		MipsFrame ret = new MipsFrame();
		ret.name = name; 			//名称
		TempList argReg = argRegs; //参数表
		for (BoolList f = formals; f !=null; f= f.tail,argReg = argReg.tail)
		{
			Access a = ret.allocLocal(f.head); //为每个参数分配Access
			ret.formals = new AccessList(a, ret.formals);
			if (argReg != null) //产生保存参数的汇编指令
			{
				ret.saveArgs.add(new Tree.MOVE(a.exp(new Tree.TEMP(fp)), new Tree.TEMP(argReg.head)));
			}
		}
		return ret;
	}

	@Override
	public Access allocLocal(boolean escape) {
		if (escape)
		{
			Access ret = new InFrame(this, allocDown);
			allocDown -= Translate.Library.WORDSIZE; //增加分配的帧空间
			return ret;	
		} 
		else // 分配给寄存器
			return new InReg();
	}

	@Override
	public Stm procEntryExit1(Stm body) {
		
		// 1 在 body 前面加上保存参数的汇编指令
		for (int i = 0; i < saveArgs.size(); ++i)
			body = new Tree.SEQ((Tree.MOVE) saveArgs.get(i), body);
		//2 在 body 前面加上保存 Callee-save 寄存器的指令
		Access fpAcc = allocLocal(true);
		Access raAcc = allocLocal(true);
		Access[] calleeAcc = new Access[numOfcalleeSaves];
		TempList calleeTemp = calleeSaves;
		for (int i = 0; i < numOfcalleeSaves; ++i, calleeTemp = calleeTemp.tail) 
		{
			calleeAcc[i] = allocLocal(true);
			body = new Tree.SEQ(new Tree.MOVE(calleeAcc[i].exp(new Tree.TEMP(fp)), new Tree.TEMP(calleeTemp.head)), body);
		}
		//3 在 body 前面加上保存返回地址 $ra 的指令
		body = new Tree.SEQ(new Tree.MOVE(raAcc.exp(new Tree.TEMP(fp)), new Tree.TEMP(ra)), body);
		//4 令$fp=$sp-帧空间+4 bytes
		body = new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(fp), new Tree.BINOP(Tree.BINOP.PLUS, new Tree.TEMP(sp), new Tree.CONST(-allocDown - Translate.Library.WORDSIZE))), body);
		//5 在 body 前保存 fp
		body = new Tree.SEQ(new Tree.MOVE(fpAcc.expFromStack(new Tree.TEMP(sp)), new Tree.TEMP(fp)), body);
		//6 在 body 后恢复 callee
		calleeTemp = calleeSaves;
		for (int i = 0; i < numOfcalleeSaves; ++i, calleeTemp = calleeTemp.tail)
			body = new Tree.SEQ(body, new Tree.MOVE(new Tree.TEMP(calleeTemp.head), calleeAcc[i].exp(new Tree.TEMP(fp))));
		//body 后恢复返回地址
		body = new Tree.SEQ(body, new Tree.MOVE(new Tree.TEMP(ra), raAcc.exp(new Tree.TEMP(fp))));
		//body 后恢复 fp
		body = new Tree.SEQ(body, new Tree.MOVE(new Tree.TEMP(fp), fpAcc.expFromStack(new Tree.TEMP(sp))));
		return body;
	}
	
	public InstrList procEntryExit2(InstrList body) {
		return append(body, new InstrList(new OPER("", null, new TempList(zero,
				new TempList(sp, new TempList(ra, calleeSaves)))), null));
		}
	
	public InstrList procEntryExit3(InstrList body) {
		//分配帧空间:将$sp 减去帧空间 (如 32byes)
		body = new InstrList(new OPER("subu $sp, $sp, " + (-allocDown),
				new TempList(sp, null), new TempList(sp, null)), body);
		//设置函数体标号
		body = new InstrList(new OPER(name.toString() + ":", null, null), body);
		//跳转到返回地址
		InstrList epilogue = new InstrList(new OPER("jr $ra", null,
				new TempList(ra, null)), null);
		//将$sp 加上相应的帧空间 (如 32bytes)
		epilogue = new InstrList(new OPER("addu $sp, $sp, " + (-allocDown),
				new TempList(sp, null), new TempList(sp, null)), epilogue);
		body = append(body, epilogue);
		return body;
		}
	
	@Override
	public String string(Label label, String value) 
	{
		String ret = label.toString() + ": " + System.getProperty("line.separator");
		if (value.equals("\n"))
		{
			ret = ret + ".word " + value.length() + System.getProperty("line.separator") ;
			ret = ret + ".asciiz \"" + System.getProperty("line.separator") + "\"";
			return ret;
		}
		ret = ret + ".word " + value.length() +  System.getProperty("line.separator");
		ret = ret + ".asciiz \"" + value + "\"";
		return ret;
	}
	
	  static TempList callDefs;
	  {
	    // registers defined by a call
	    callDefs = new TempList(RA(), new TempList(argRegs, callerSaves));
	  }


	@Override
	public InstrList codegen(Stm s) {
		return (new Codegen(this)).codegen(s);
	}

}
