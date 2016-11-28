package Mips;

import Frame.*;
import Assem.*;
import Tree.*;
import Temp.*;

public class Codegen {
	MipsFrame frame;
	InstrList ilist = null, last = null;
	
	public Codegen(MipsFrame f) 
	{
		frame = f;
	}
	
	public InstrList codegen(Stm s) 
	{
		InstrList l;
		munchStm(s);
		l = ilist;
		ilist = last = null;
		return l;
	}
	
	void munchStm(Stm s) 
	{
		if (s instanceof Tree.MOVE)
			munchStm((Tree.MOVE) s);
		if (s instanceof Tree.Exp)
			munchStm((Tree.Exp) s);
		if (s instanceof Tree.JUMP)
			munchStm((Tree.JUMP) s);
		if (s instanceof Tree.CJUMP)
			munchStm((Tree.CJUMP) s);
		if (s instanceof Tree.SEQ)
			munchStm((Tree.SEQ) s);
		if (s instanceof Tree.LABEL)
			munchStm((Tree.LABEL) s);
	}
	
	 Temp munchExp(Tree.Exp s) 
	 {
	    if (s instanceof Tree.CONST)
	    	return munchExp((Tree.CONST)s);
	    else if (s instanceof Tree.NAME)
	    	return munchExp((Tree.NAME)s);
	    else if (s instanceof Tree.TEMP)
	    	return munchExp((Tree.TEMP)s);
	    else if (s instanceof Tree.BINOP)
	    	return munchExp((Tree.BINOP)s);
	    else if (s instanceof Tree.MEM)
	    	return munchExp((Tree.MEM)s);
	    else if (s instanceof Tree.CALL)
	    	return munchExp((Tree.CALL)s);
	    else
	    	throw new Error("Codegen.munchExp");
	}
	
	private void emit(Assem.Instr inst) 
	{
		if (last != null)
	      last = last.tail = new Assem.InstrList(inst, null);
	    else {
	      if (ilist != null)
		      throw new Error("Codegen.emit");
	      last = ilist = new Assem.InstrList(inst, null);
	    }
	 }	
	

	static Assem.Instr OPER(String a, TempList d, TempList s, LabelList j)
	{
	    return new Assem.OPER("\t" + a, d, s, j);
	 }
	static Assem.Instr OPER(String a, TempList d, TempList s)
	{
	    return new Assem.OPER("\t" + a, d, s);
	}
	static Assem.Instr MOVE(String a, Temp d, Temp s) 
	{
	    return new Assem.MOVE("\t" + a, d, s);
	}	
	
	// a. Tree.Move
	public void munchStm(Tree.MOVE s) 
	{
		Tree.Exp dst = s.dst;
		Tree.Exp src = s.src;
		if (dst instanceof MEM) {
			MEM dst1 = (MEM) dst;
			if ((dst1.exp instanceof BINOP) //Tree.Exp.isBINOP(dst1.exp
				&& (((BINOP) dst1.exp).binop == BINOP.PLUS)
				&& (((BINOP) dst1.exp).right instanceof CONST)) //Tree.Exp.isCONST(((BINOP) dst1.exp).right)
			{ //情况 1
					Temp t1 = munchExp(src);
					Temp t2 = munchExp(((BINOP) dst1.exp).left);
					emit(new OPER("sw `s0, "
							+ ((CONST) ((BINOP) dst1.exp).right).value + "(`s1)",
							null, new TempList(t1, new TempList(t2, null))));
			} else if ((dst1.exp instanceof BINOP) // Tree.Exp.isBINOP(dst1.exp
						&& ((BINOP) dst1.exp).binop == BINOP.PLUS
						&& (((BINOP) dst1.exp).left instanceof CONST)) //Expr.isCONST(((BINOP) dst1.exp).left)
			{ //情况 2
					Temp t1 = munchExp(src);
					Temp t2 = munchExp(((BINOP) dst1.exp).right);
					emit(new OPER("sw `s0, "
							+ ((CONST) ((BINOP) dst1.exp).left).value + "(`s1)",
							null, new TempList(t1, new TempList(t2, null))));
			} else if (dst1.exp instanceof CONST) // Expr.isCONST(dst1.exp)
			{ //情况 3
				Temp t1 = munchExp(src);
				emit(new OPER("sw `s0, " + ((CONST) dst1.exp).value, null,
				new TempList(t1, null)));
			} else  
			{ //情况 4
				Temp t1 = munchExp(src);
				Temp t2 = munchExp(dst1.exp);
				emit(new OPER("sw `s0, (`s1)", null, new TempList(t1,
						new TempList(t2, null))));
			}
		} else if (dst instanceof TEMP) //Expr.isTEMP(dst)
				if (src instanceof CONST) //Expr.isCONST(src)
				{ //情况 5
					emit(new OPER("li `d0, " + ((CONST) src).value, new TempList(
							((TEMP) dst).temp, null), null));
				} else 
				{ //情况 6
					Temp t1 = munchExp(src);
					emit(new OPER("move `d0, `s0", new TempList(((TEMP) dst).temp,
							null), new TempList(t1, null)));
				}
	}
	
	// b Tree.Label
	public void munchStm(Tree.LABEL l) 
	{
		emit(new Assem.LABEL(l.label.toString() + ":", l.label));
	}
	
	// c Tree.JUMP
	public void munchStm(Tree.JUMP j) 
	{
		emit(new OPER("j " + j.targets.head, null, null, j.targets));
	}
	
	// d Tree.CJUMP
	public void munchStm(CJUMP j) 
	{
		String oper = null;
		switch (j.relop) {
			case CJUMP.EQ:
				oper = "beq";
				break;
			case CJUMP.NE:
				oper = "bne";
				break;
			case CJUMP.GT:
				oper = "bgt";
				break;
			case CJUMP.GE:
				oper = "bge";
				break;
			case CJUMP.LT:
				oper = "blt";
				break;
			case CJUMP.LE:
				oper = "ble";
				break;
			}
			Temp t1 = munchExp(j.left);
			Temp t2 = munchExp(j.right);
			emit(new OPER(oper + " `s0, `s1, `j0", null, new TempList(t1,
					new TempList(t2, null)), new LabelList(j.iftrue, new LabelList(
							j.iffalse, null))));
	}
	
	// e Tree.Exp
	public void munchStm(Tree.Exp e)
	{
		munchExp(e.exp);
	}
	
	// f Tree.MEM
	public Temp munchExp(MEM e) 
	{
		Temp ret = new Temp();
		
		if ((e.exp instanceof BINOP) && ((BINOP) e.exp).binop == BINOP.PLUS 
				&& (((BINOP)e.exp).right instanceof CONST)) 
		{	//情况 1
			Temp t1 = munchExp(((BINOP) e.exp).left);
			emit(new OPER("lw `d0, " + ((CONST) ((BINOP) e.exp).right).value
					+ "(`s0)", new TempList(ret, null), new TempList(t1, null)));
		
		} else if ((e.exp instanceof BINOP) && ((BINOP) e.exp).binop == BINOP.PLUS
					&& (((BINOP)e.exp).left instanceof CONST))
		{	//情况 2
			Temp t1 = munchExp(((BINOP) e.exp).right);
			emit(new OPER("lw `d0, " + ((CONST) ((BINOP) e.exp).left).value
					+ "(`s0)", new TempList(ret, null), new TempList(t1, null)));
		} else if (e.exp instanceof CONST) 
		{	//情况 3
			emit(new OPER("lw `d0, " + ((CONST) e.exp).value, new TempList(ret,
					null), null)); 
		} else 
		{	//情况 4
			Temp t1 = munchExp(e.exp);
			emit(new OPER("lw `d0, (`s0)", new TempList(ret, null),
					new TempList(t1, null))); 
		}
		return ret;
	}
	
	// g Tree.CONST
	public Temp munchExp(CONST e) 
	{
		Temp ret = new Temp();
		emit(new OPER("li `d0, " + e.value, new TempList(ret, null), null));
		return ret;
	}
	
	//h Tree.BINOP
	public Temp munchExp(BINOP e) {
		Temp ret = new Temp();
		String oper = null;
		switch (e.binop) {
		case BINOP.PLUS:
			oper = "add";
			break;
		case BINOP.MINUS:
			oper = "sub";
			break;
		case BINOP.MUL:
			oper = "mul";
			break;
		case BINOP.DIV:
			oper = "div";
			break;
		}
		if (e.right instanceof CONST) { //Expr.isCONST(e.right)
			//情况 1
			Temp t1 = munchExp(e.left);
			emit(new OPER(oper + " `d0, `s0, " + ((CONST) e.right).value,
					new TempList(ret, null), new TempList(t1, null)));
		} else if (e.left instanceof CONST) {//Expr.isCONST(e.left)
			//情况 2
			Temp t1 = munchExp(e.right);
			emit(new OPER(oper + " `d0, `s0, " + ((CONST) e.left).value,
					new TempList(ret, null), new TempList(t1, null)));
		} else { 
			//情况 3
			Temp t1 = munchExp(e.left);
			Temp t2 = munchExp(e.right);
			emit(new OPER(oper + " `d0, `s0, `s1", new TempList(ret, null),
					new TempList(t1, new TempList(t2, null))));
		}
		return ret;
	}
	
	// i Tree.TEMP
	public Temp munchExp(TEMP t) 
	{
		return t.temp;
	}
	
	// j Tree.NAME
	public Temp munchExp(NAME t)
	{
		Temp ret = new Temp();
		emit(new OPER("la `d0, " + t.label, new TempList(ret, null), null));
		return ret;
	}
	
	// k Tree.CALL
	public Temp munchExp(CALL c) {
		TempList list = null;
		int i = 0;
		for (ExpList a = c.args; a != null; a = a.tail, ++i) {
			Temp t = null;
			if (a.head instanceof CONST)
				emit(new OPER("li $a" + i + ", " + ((CONST) a.head).value,
						null, null));
			else {
				t = munchExp(a.head);
				emit(new OPER("move $a" + i + ", `s0", null, new TempList(t,
						null)));
			}
			if (t != null)
				list = new TempList(t, list);
		}
		emit(new OPER("jal " + ((NAME) c.func).label, MipsFrame.callDefs, list));
		return MipsFrame.rv;//v0
	}
}
