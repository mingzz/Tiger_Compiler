package Translate;

import Temp.Label;
import Tree.Stm;

public class Nx extends Exp {
	Stm stm;
	
	Nx(Stm stm)
	{
		this.stm = stm;
	}
	
	@Override
	Tree.Exp unEx() {
		// 无法操作
		return null;
	}

	@Override
	Stm unNx() {
		// 返回 stm本身
		return stm;
	}

	@Override
	Stm unCx(Label t, Label f) {
		// 无法操作
		return null;
	}

}
