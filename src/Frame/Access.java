package Frame;

public abstract class Access {
	public abstract Tree.Exp exp(Tree.Exp framePtr); // fpΪ��ʼ��ַ
	public abstract Tree.Exp expFromStack(Tree.Exp stackPtr); //spΪ��ʼ��ַ
}