package Semant;

import Types.*;

public class FuncEntry extends Entry{
	public Temp.Label label;
	RECORD formals;
	Type result;
	public Translate.Level level;
	
	public FuncEntry( Translate.Level level, Temp.Label label, RECORD p, Type rt)
	{
		formals = p;
		result = rt;
		this.level = level;
		this.label = label;
	}
}
