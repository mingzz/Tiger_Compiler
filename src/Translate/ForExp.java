package Translate;

import Temp.Label;
import Tree.*;

public class ForExp extends Exp {
	Level currentL; //层
	Access var; //循环变量
	Exp low, high; //初试值、终止值
	Exp body; //循环体
	Label done; //完成后出口
	
	ForExp(Level home, Access var, Exp low, Exp high, Exp body, Label done)
	{
		this.currentL = home;
		this.var = var;
		this.low = low;
		this.high = high;
		this.body = body;
		this.done = done;
	}
	
	// for不能有返回值
	@Override
	Tree.Exp unEx() {
		System.err.println("ForExp.unEx() should not be called.");
		return null;
	}

	
	/*
	循环变量和循环上限被分配在帧空间中
	MOVE VAR, LOW
	MOVE LIMIT, HIGH
	if (VAR<=LIMIT) goto BEGIN else goto DONE
	LABEL BEGIN:
	body
	if (VAR<LIMIT) goto GOON else goto DONE
	LABEL GOON:
	VAR=VAR+1
	GOTO BEGIN:
	LABEL DONE:
	*/
	@Override
	Stm unNx() {
		Access hbound = currentL.allocLocal(true);
		Label begin = new Label();
		Label goon = new Label();
	
		return new SEQ(new MOVE(var.acc.exp(new TEMP(currentL.frame.FP())),low.unEx()), 
		               new SEQ(new MOVE(hbound.acc.exp(new TEMP(currentL.frame.FP())),high.unEx()), 
		               new SEQ(new CJUMP(CJUMP.LE, var.acc.exp(new TEMP(currentL.frame.FP())), hbound.acc.exp(new TEMP(currentL.frame.FP())), begin, done),
		               new SEQ(new LABEL(begin), 
				       new SEQ(body.unNx(), 
				       new SEQ(new CJUMP(CJUMP.LT, var.acc.exp(new TEMP(currentL.frame.FP())),	hbound.acc.exp(new TEMP(currentL.frame.FP())), goon, done), 
					   new SEQ(new LABEL(goon),
					   new SEQ(new MOVE( var.acc.exp(new TEMP(currentL.frame.FP())), new BINOP(BINOP.PLUS, var.acc.exp(new TEMP(currentL.frame.FP())), new CONST(1))),
					   new SEQ(new JUMP(begin), new LABEL(done))))))))));
	}

	
	// for只有一个出口
	@Override
	Stm unCx(Label t, Label f) {
		System.err.println("ForExp.unCx() should not be called.");
		return null;
	}

}
