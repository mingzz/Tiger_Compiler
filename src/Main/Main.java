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
		//Yylex ִ�дʷ�����
		//Grm ִ���﷨����
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
		
		//�ʷ�
		Lexer lexer = new Yylex(inp, errorMsg);
	    java_cup.runtime.Symbol tok;
	    System.out.println("-------------------------------�ʷ�------------------------------"); 
	    System.out.println("\nToken                                                      λ��");
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
	    
	    //�﷨
	    System.out.println("------------------------------�﷨------------------------------");
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
	    
	    
	    
	    //����
	    System.out.println("-----------------------------����------------------------------");
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

	    //IR�� 
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
	
	//���ݺ���������ú����ε� IR ���ͻ��ָ��
	static void emitProc(ProcFrag f) {
		java.io.PrintStream irOut = new java.io.PrintStream(System.out);
		//��� IR ��
		Tree.Print print = new Tree.Print(irOut);
		irOut.println("function " + f.frame.name);
		print.prStm(f.body);
		//�淶��
		//IR ����д��һ��û�� SEQ �� ESEQ ���Ĺ淶����
		Tree.StmList stms = Canon.Canon.linearize(f.body);
		//���ݸñ��ֻ�����,ÿ���������в������ڲ���ת�ͱ��
		Canon.BasicBlocks b = new Canon.BasicBlocks(stms);
		//�����鱻˳�����,���е� CJUMP ������ false ���
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
			//���ɻ�����
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
		//�Ĵ�������
		//��һ������ 3 ���� 9.6
		RegAlloc regAlloc = new RegAlloc(f.frame, instrs);
		//��Ӻ������úͷ��صĴ���
		instrs = f.frame.procEntryExit3(instrs);
		Temp.TempMap tempmap = new Temp.CombineMap(f.frame, regAlloc);
		//��� Mips ָ��
		System.out.println(".text");
		for (Assem.InstrList p = instrs; p != null; p = p.tail)
		System.out.println(p.head.format(tempmap));
	}
}