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
		// �޷�����
		return null;
	}

	@Override
	Stm unNx() {
		// ���� stm����
		return stm;
	}

	@Override
	Stm unCx(Label t, Label f) {
		// �޷�����
		return null;
	}

}
