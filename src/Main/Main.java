package Main;
import java.io.OutputStream;
import java.util.Scanner;
import Absyn.*;
import Parse.*;
import Semant.*;

public class Main{
	public static void main(String[] argv) throws java.io.IOException 
	{
		String filename = argv[0];
		ErrorMsg.ErrorMsg errorMsg = new ErrorMsg.ErrorMsg(filename);  
		java.io.FileInputStream inp = new java.io.FileInputStream(filename); 
		System.out.println("\n\n\n");
		
		//词法
		Lexer lexer = new Yylex(inp, errorMsg);
	    java_cup.runtime.Symbol tok;
	    System.out.println("-------------------------------词法------------------------------"); 
	    System.out.println("\nToken                                                      位置");
	    symName name=new symName();
	    try
	    {
	    	do 
	    	{ 
	         tok=lexer.nextToken();
	         System.out.println(name.getName(tok.sym) + "-------------------------------------------------------"+ tok.left);
	    	}
	    	while (tok.sym != sym.EOF);
	    	inp.close(); 
	      }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    System.out.println("\n\n\n");
	    
	    //语法
	    System.out.println("------------------------------语法------------------------------");
	    inp = new java.io.FileInputStream(filename);
	    lexer = new Yylex(inp, errorMsg);
	    Grm p = new Grm(lexer, errorMsg);
	    try 
	    {
	    	p.parse();  
	    }
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
		}
	    Print printAbstree = new Print(System.out);  
	    printAbstree.prExp(p.parseResult, 0); 
	    System.out.println("\n\n\n");
	    
	    
	    
	    //语义
	    System.out.println("-----------------------------语义------------------------------");
	    Frame.Frame frame = new Mips.MipsFrame();
	    Translate.Translate translator = new Translate.Translate(frame);
	    Semant semant = new Semant(translator, errorMsg);
	    Frag.Frag frags = null;
	    try
	    {
	    	frags = semant.transProg(p.parseResult);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    if(ErrorMsg.ErrorMsg.anyErrors) return;

	    //IR树 
	     java.io.PrintStream irOut = new java.io.PrintStream(System.out);
	    for(Frag.Frag f = frags; f!=null; f=f.next)
	    {
	        if (f instanceof Frag.ProcFrag)
	        {
	        	Tree.Print IRPrint = new Tree.Print(irOut);
	        	Tree.StmList stml = Canon.Canon.linearize(((Frag.ProcFrag) f).body);
	        	Canon.BasicBlocks  b = new Canon.BasicBlocks(stml);
	        	Tree.StmList sheduledStml = (new Canon.TraceSchedule(b)).stms;
	        	for(Tree.StmList ss = sheduledStml; ss!=null; ss=ss.tail)
	        		IRPrint.prStm(ss.head);
	        }
	    }
	    
	}
}