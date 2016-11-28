package Mips;

import Frame.Access;
import Tree.Exp;

public class InReg extends Access {
	private Temp.Temp reg;
	
	public InReg() {reg = new Temp.Temp();}
	
	@Override
	public Exp exp(Exp framePtr) {
		return new Tree.TEMP(reg);
	}

	@Override
	public Exp expFromStack(Exp stackPtr) {
		return new Tree.TEMP(reg);
	}

}
