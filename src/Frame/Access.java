package Frame;

public abstract class Access {
	public abstract Tree.Exp exp(Tree.Exp framePtr); // fp为起始地址
	public abstract Tree.Exp expFromStack(Tree.Exp stackPtr); //sp为起始地址
}
