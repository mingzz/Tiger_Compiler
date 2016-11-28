package Types;

public class VOID extends Type {
	public static final VOID _void = new VOID();
	public VOID () {}
	public boolean coerceTo(Type t) {return (t.actual() instanceof VOID);}
}
