package Parse;

public class symName {
	 static String nameArray[];
	 public symName(){
		 nameArray=new String[100];
	     nameArray[sym.FUNCTION] = "FUNCTION";
	     nameArray[sym.EOF] = "EOF";
	     nameArray[sym.INT] = "INT";
	     nameArray[sym.GT] = "GT";
	     nameArray[sym.DIVIDE] = "DIVIDE";
	     nameArray[sym.COLON] = "COLON";
	     nameArray[sym.ELSE] = "ELSE";	
	     nameArray[sym.OR] = "OR";
	     nameArray[sym.NIL] = "NIL";
	     nameArray[sym.DO] = "DO";
	     nameArray[sym.GE] = "GE";
	     nameArray[sym.error] = "error";
	     nameArray[sym.LT] = "LT";
	     nameArray[sym.OF] = "OF"; 
	     nameArray[sym.MINUS] = "MINUS";
	     nameArray[sym.ARRAY] = "ARRAY";
	     nameArray[sym.TYPE] = "TYPE";
	     nameArray[sym.FOR] = "FOR";
	     nameArray[sym.TO] = "TO";
	     nameArray[sym.TIMES] = "TIMES";
	     nameArray[sym.COMMA] = "COMMA";
	     nameArray[sym.LE] = "LE";
	     nameArray[sym.IN] = "IN";
	     nameArray[sym.END] = "END";
	     nameArray[sym.ASSIGN] = "ASSIGN";
	     nameArray[sym.STRING] = "STRING";
	     nameArray[sym.DOT] = "DOT";
	     nameArray[sym.LPAREN] = "LPAREN";
	     nameArray[sym.RPAREN] = "RPAREN";
	     nameArray[sym.IF] = "IF";
	     nameArray[sym.SEMICOLON] = "SEMICOLON";
	     nameArray[sym.ID] = "ID";
	     nameArray[sym.WHILE] = "WHILE";
	     nameArray[sym.LBRACK] = "LBRACK";
	     nameArray[sym.RBRACK] = "RBRACK";
	     nameArray[sym.NEQ] = "NEQ";
	     nameArray[sym.VAR] = "VAR";
	     nameArray[sym.BREAK] = "BREAK";
	     nameArray[sym.AND] = "AND";
	     nameArray[sym.PLUS] = "PLUS";
	     nameArray[sym.LBRACE] = "LBRACE";
	     nameArray[sym.RBRACE] = "RBRACE";
	     nameArray[sym.LET] = "LET";
	     nameArray[sym.THEN] = "THEN";
	     nameArray[sym.EQ] = "EQ";
	     nameArray[sym.NUM] = "NUM";
	     nameArray[sym.STR] = "STR";
	 }
	 public String getName(int index){
		 return nameArray[index];
	 }
}
