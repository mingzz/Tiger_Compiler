package Frag;

public class ProcFrag extends Frag {
	public Frame.Frame frame = null;
	public Tree.Stm body = null;
	public ProcFrag(Tree.Stm body, Frame.Frame f)
	{
		this.body = body;
		frame = f;
	}
}
