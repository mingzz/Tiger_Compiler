package Translate;

import Temp.Label;
import Tree.*;

public class IfExp extends Exp {
	private Exp test; //测试条件
	private Exp e1; //then 子句
	private Exp e2; //else 子句
	
	IfExp(Exp test, Exp e1, Exp e2) {
		this.test = test;
		this.e1 = e1;
		this.e2 = e2;
	}
	
	//if-else 这样翻译成有返回值的表达式:
	//if (test) goto T else goto F
	//LABEL T: r=e1
	//goto JOIN
	//LABEL F: r=e2
	//LABEL JOIN: return r
	@Override
	Tree.Exp unEx() {
		Temp.Temp r = new Temp.Temp();
		Temp.Label join = new Temp.Label();
		Temp.Label t = new Temp.Label();
		Temp.Label f = new Temp.Label();
		return new ESEQ(new SEQ(test.unCx(t, f),
						new SEQ(new LABEL(t),
						new SEQ(new MOVE(new TEMP(r), e1.unEx()),
						new SEQ(new JUMP(join),
						new SEQ(new LABEL(f),
						new SEQ(new MOVE(new TEMP(r), e2.unEx()),
								new LABEL(join))))))),
						new TEMP(r));
	}

	
	//这样翻译成无返回值的 if-then
	//如果没有 else 子句
	// if (test) goto T else goto JOIN
	// LABEL T: e1
	// LABEL JOIN:
	//如果有 else 子句
	// if (test) goto T else goto F
	// LABEL T: e1
	// goto JOIN
	// LABEL F: e2
	// LABEL JOIN:
	@Override
	Stm unNx() {
		Temp.Label join = new Temp.Label();
		Temp.Label t = new Temp.Label();
		Temp.Label f = new Temp.Label();
		if (e2 == null)
			return new SEQ(test.unCx(t, join),new SEQ(new LABEL(t),new SEQ(e1.unNx(),new LABEL(join))));
		else return new SEQ(test.unCx(t, f),
							new SEQ(new LABEL(t),
							new SEQ(e1.unNx(),
							new SEQ(new JUMP(join),
							new SEQ(new LABEL(f),
							new SEQ(e2.unNx(),
							new LABEL(join)))))));	
	}
	
	//直接变成 Cx:
	//if test!=0 goto T else goto F
	@Override
	Stm unCx(Label t, Label f) {
		return new CJUMP(CJUMP.NE, unEx(), new CONST(0), t, f);
	}

}
