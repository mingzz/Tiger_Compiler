package Frame;

import Temp.*;
import Assem.InstrList;

public abstract class Frame implements TempMap{
	// ������֡�����ơ�����������Ϣ��
	public abstract Frame newFrame(Label name, Util.BoolList formals);
	public Label name = null; //����
	public AccessList formals = null; //���ر����б�
	public abstract Access allocLocal(boolean escape);  //���������ر������Ƿ����ݣ�
	public abstract Tree.Exp externCall(String func, Tree.ExpList args); //�ⲿ����
	public abstract Temp FP(); //ָ֡��
	public abstract Temp SP(); //ջָ��
	public abstract Temp RA(); //���ص�ַ
	public abstract Temp RV(); //����ֵ
	public abstract java.util.HashSet registers(); // �Ĵ����б�
	public abstract Tree.Stm procEntryExit1(Tree.Stm body); //��Ӷ��⺯������ָ��
	public abstract Assem.InstrList procEntryExit2(Assem.InstrList body);
	public abstract Assem.InstrList procEntryExit3(Assem.InstrList body);
	public abstract String string(Label label, String value);
	public abstract Assem.InstrList codegen(Tree.Stm s); // ����MIPSָ����
	//public abstract int wordSize(); //����һ���ֳ�
	
	static public InstrList append(InstrList a, InstrList b)
	{
		InstrList tmp;
		
		if(a == null)
			return b;
		if(b == null)
			return a;
		for(tmp = a; tmp.tail != null; tmp = tmp.tail);
		tmp.tail = b;
		return a;
	}
}
