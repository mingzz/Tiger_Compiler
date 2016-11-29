package Main;
import java.io.OutputStream;
import java.util.Scanner;
import Absyn.*;
import Frag.*;
import Parse.*;
import RegAlloc.RegAlloc;
import Semant.*;
import Frame.*;
import Mips.*;

public class Main{
	public static void main(String[] argv) throws java.io.IOException 
	{
		String filename = argv[0];
		ErrorMsg.ErrorMsg errorMsg = new ErrorMsg.ErrorMsg(filename);  
		java.io.FileInputStream inp = new java.io.FileInputStream(filename); 
		System.out.println("\n\n\n");
		
		Parse parse = new Parse(filename);
		//Yylex 执行词法分析
		//Grm 执行语法分析
		Grm parser = new Grm(new Yylex(inp, errorMsg), errorMsg);
	    try 
	    {
	    	parser.parse();  
	    }
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
		}
	   
	    Print printAbstree = new Print(System.out);  
	    Exp absyn = parser.parseResult;
	    printAbstree.prExp(absyn, 0); 
	    System.out.println("\n\n\n");
	    
	    //
	    Frame frame = new MipsFrame();
	    Translate.Translate translator = new Translate.Translate(frame);
	    Semant semant = new Semant(translator, errorMsg);
	    Frag frags = semant.transProg(absyn);
	    
	    System.out.println(".global main");
	    for (Frag f = frags; f != null; f = f.next)
	    	if (f instanceof ProcFrag)
	    	emitProc((ProcFrag) f);
	    	else if (f instanceof DataFrag)
	    	System.out.println(".data\n" + ((DataFrag) f).data);
		/*
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
	    */
	}
	
	//根据函数段输出该函数段的 IR 树和汇编指令
	static void emitProc(ProcFrag f) {
		java.io.PrintStream irOut = new java.io.PrintStream(System.out);
		//输出 IR 树
		Tree.Print print = new Tree.Print(irOut);
		irOut.println("function " + f.frame.name);
		print.prStm(f.body);
		//规范化
		//IR 树被写成一个没有 SEQ 和 ESEQ 结点的规范树表
		Tree.StmList stms = Canon.Canon.linearize(f.body);
		//根据该表划分基本块,每个基本块中不包含内部跳转和标号
		Canon.BasicBlocks b = new Canon.BasicBlocks(stms);
		//基本块被顺序放置,所有的 CJUMP 都跟有 false 标号
		Tree.StmList traced = (new Canon.TraceSchedule(b)).stms;//////
		
		System.out.println("\n\n\n");
		Tree.Print IRPrint = new Tree.Print(irOut);
    	for(Tree.StmList ss = traced; ss!=null; ss=ss.tail)
    		IRPrint.prStm(ss.head);
    	
		Assem.InstrList instrs = f.frame.codegen(traced.head);
		Assem.InstrList p1 = instrs;
		Tree.StmList m1 = traced.tail;
		//traced.head = traced.tail.head;
		for(Tree.Stm i = m1.head; i!=null; i = m1.head){
			//生成汇编代码
			p1.tail = f.frame.codegen(i);
			while(p1.tail != null){
				p1 = p1.tail;
			}
			
			if(m1.tail != null){
				m1 = m1.tail;
			}else{
				break;
			}
			//m1 = m1.tail;
			//instrs = f.frame.procEntryExit2(instrs);
		}
		instrs = f.frame.procEntryExit2(instrs);
		//寄存器分配
		//这一步具体 3 步见 9.6
		RegAlloc regAlloc = new RegAlloc(f.frame, instrs);
		//添加函数调用和返回的代码
		instrs = f.frame.procEntryExit3(instrs);
		Temp.TempMap tempmap = new Temp.CombineMap(f.frame, regAlloc);
		//输出 Mips 指令
		System.out.println(".text");
		for (Assem.InstrList p = instrs; p != null; p = p.tail)
		System.out.println(p.head.format(tempmap));
	}
}