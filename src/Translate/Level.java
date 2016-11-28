package Translate;


import Symbol.Symbol;
import Util.*;

public class Level {
	public Level parent;	//直接上级层
	public Frame.Frame frame;		//帧
	public AccessList formals;		//参数
	
	public Access staticLink()
	{
		return formals.head;
	}
	
	public Level(Level parent, Symbol name, BoolList fmls)
	{
		this.parent = parent;
		this.frame = parent.frame.newFrame(new Temp.Label(name), new BoolList(true, fmls));
		for (Frame.AccessList f = frame.formals; f != null; f = f.next)
			this.formals = new AccessList(new Access(this, f.head), this.formals);
	}
	public Access allocLocal(boolean escape)
	{
		return new Access(this, frame.allocLocal(escape));	
	}
	public Level(Frame.Frame frm)
	{
		this.frame = frm;	
		this.parent = null;
	}
}
