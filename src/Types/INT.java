package Types;

public class INT extends Type {
	public static final INT _int = new INT();
	public INT () {}
	public boolean coerceTo(Type t) {return (t.actual() instanceof INT);}
}

