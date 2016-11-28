package Types;

public class STRING extends Type {
	public static final STRING _string = new STRING();
	public STRING(){}
	public boolean coerceTo(Type t) {return (t.actual() instanceof STRING);}
}

