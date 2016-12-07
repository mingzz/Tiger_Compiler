package Semant;

import Types.*;
import Util.BoolList;
import Symbol.*;
import Translate.Level;
import ErrorMsg.*;

public class Env {
	Table vEnv = null;
	Table tEnv = null;
	ErrorMsg errorMsg = null;
	Level root = null;
	java.util.HashSet<Symbol> stdFuncSet = new java.util.HashSet<Symbol>(); 
	
	Env(ErrorMsg errorMsg, Level root){
		this.errorMsg = errorMsg;
		this.root = root;
		initTEnv();
		initVEnv();
	}
	
	//初始化tEnv
	public void initTEnv(){
		tEnv = new Table();
		// int 
		tEnv.put(Symbol.symbol("int"), INT._int);
		// string
		tEnv.put(Symbol.symbol("string"), STRING._string);
	}
	
	//初始化vEnv
	public void initVEnv(){
		vEnv = new Table();
		
		Symbol sym = null; 		//函数名
		RECORD formals = null; 	//参数表
		Type result = null;		//返回值类型
		Level level = null;		//层
		
		// 每个函数少1行 , set
		// print(s:string)
		sym = Symbol.symbol("print");
		formals = new RECORD(Symbol.symbol("s"), STRING._string,null);
		result = VOID._void;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
		// printi(i:int)
		sym = Symbol.symbol("printi");
		formals = new RECORD(Symbol.symbol("i"), INT._int, null);
		result = VOID._void;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
		// flush()
		sym = Symbol.symbol("flush");
		formals = null;
		result = VOID._void;
		level = new Level(root, sym, null);
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
				
		// getchar() : string
		sym = Symbol.symbol("getchar");
		formals = null;
		result = STRING._string;
		level = new Level(root, sym, null);
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
		// ord(s:string) :int
		sym = Symbol.symbol("ord");
		formals = new RECORD(Symbol.symbol("s"), STRING._string, null);
		result = INT._int;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
				
		// chr(i:int) : string
		sym = Symbol.symbol("chr");
		formals = new RECORD(Symbol.symbol("i"), INT._int, null);
		result = STRING._string;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
		// size(s:string) : int
		sym = Symbol.symbol("size");
		formals = new RECORD(Symbol.symbol("s"), STRING._string, null);
		result = INT._int;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
		// substring(s:string. f:int, n:int) : string
		sym = Symbol.symbol("substring");
		formals = new RECORD(Symbol.symbol("n"), INT._int, null);
		formals = new RECORD(Symbol.symbol("f"), INT._int, formals);
		formals = new RECORD(Symbol.symbol("s"), STRING._string, formals);
		result = STRING._string;
		level = new Level(root, sym, new BoolList(true, new BoolList(true, new BoolList(true, null))));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
				
		// concat(s1:string, s2:string):string
		sym = Symbol.symbol("concat");
		formals = new RECORD(Symbol.symbol("s"), STRING._string, null);
		formals = new RECORD(Symbol.symbol("s"), STRING._string, formals);
		result = STRING._string;
		level = new Level(root, sym, new BoolList(true, new BoolList(true, null)));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
			
		// not(i:int): int
		sym = Symbol.symbol("not");
		formals = new RECORD(Symbol.symbol("i"), INT._int, null);
		result = INT._int;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
		
		// exit(i:int)
		sym = Symbol.symbol("exit");
		formals = new RECORD(Symbol.symbol("i"), INT._int, null);
		result = VOID._void;
		level = new Level(root, sym, new BoolList(true, null));
		vEnv.put(sym, new StdFuncEntry(level, new Temp.Label(sym), formals, result));
		
	}
}
