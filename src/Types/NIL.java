package Types;

public class NIL extends Type {
	public static final NIL _nil = new NIL();
	public NIL () {}
	public boolean coerceTo(Type t) {
	    Type a = t.actual();
	    return (a instanceof RECORD) || (a instanceof NIL);
        }
}

