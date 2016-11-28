package Types;

public class RECORD extends Type {
   public Symbol.Symbol fieldName;
   public Type fieldType;
   public RECORD tail;
   public RECORD(Symbol.Symbol n, Type t, RECORD x) {
       fieldName=n; fieldType=t; tail=x;
   }
   public RECORD(){
	   fieldName=null; fieldType=null; tail=null;
   }
   public boolean coerceTo(Type t) {
	return this==t.actual();
   }
   static public boolean isNull(RECORD r)
   {
	   if (r == null || (r.fieldName == null && r.fieldType == null && r.tail == null))
		   return true; 
	   return false;
   }
}
   

