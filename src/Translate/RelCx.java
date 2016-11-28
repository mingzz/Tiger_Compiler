package Translate;

import Temp.Label;
import Tree.*;
import Absyn.OpExp;

public class RelCx extends Cx {
	int oper = 0;
	Exp left = null;
	Exp right = null;
	
	public RelCx(int oper, Exp left, Exp right)
	{
		switch(oper){
		case OpExp.EQ:
			this.oper = Tree.CJUMP.EQ;
			break;
		case OpExp.NE:
			this.oper = Tree.CJUMP.NE;
			break;
		case OpExp.LT:
			this.oper = CJUMP.LT;	
			break;
		case OpExp.LE:
			this.oper = CJUMP.LE;	
			break;
		case OpExp.GT:
			this.oper = CJUMP.GT;	
			break;
		case OpExp.GE:
			this.oper = CJUMP.GE;	
			break;
		}
		this.left = left;
		this.right = right;
	}
	
	@Override
	Stm unCx(Label t, Label f) {
		return new CJUMP(oper, left.unEx(), right.unEx(), t, f);
	}

}
