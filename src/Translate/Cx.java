package Translate;

import Temp.*;
import Tree.*;

public abstract class Cx extends Exp {

	@Override
	Tree.Exp unEx()
	{
		Temp r = new Temp();
		Label t = new Label();
		Label f = new Label();
		return new ESEQ(
				new SEQ(new MOVE(new TEMP(r), new CONST(1)),
						new SEQ(unCx(t, f),
								new SEQ(new LABEL(f),
										new SEQ(new MOVE(new TEMP(r), new Tree.CONST(0)),
												new LABEL(t))))),
				new TEMP(r));
	}	

	@Override
	Stm unNx() {return new Tree.Exp(unEx());} //留给子类具体处理

	@Override
	abstract Stm unCx(Label t, Label f); //留给子类具体处理

}
