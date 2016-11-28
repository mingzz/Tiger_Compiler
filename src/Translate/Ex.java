package Translate;

import Temp.Label;
import Tree.Stm;

public class Ex extends Exp {
	Tree.Exp exp =null;
	
	Ex(Tree.Exp e) {this.exp = e;}
	@Override
	Tree.Exp unEx() {
		return exp;
	}

	@Override
	Stm unNx() {
		return new Tree.Exp(exp);
	}

	@Override
	Stm unCx(Label t, Label f) {
		//若表达式非 0 转到 t,否则转到 f
		//if (exp!=0) goto T else goto F
		return new Tree.CJUMP(Tree.CJUMP.NE, exp, new Tree.CONST(0), t, f);
	}

}
