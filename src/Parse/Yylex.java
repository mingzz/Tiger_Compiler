/* The following code was generated by JFlex 1.4.1 on 16-11-14 下午9:31 */

package Parse;
import ErrorMsg.ErrorMsg;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 16-11-26 下午5:26 from the specification file
 * <tt>C:/Users/yiming/Google 云端硬盘/Compiler/编译原理/工具与代码/CODE/书里的框架代码/tiger/chap2/Parse/Tiger.flex</tt>
 */
public class Yylex implements Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int Str1 = 3;
  public static final int YYINITIAL = 0;
  public static final int Str = 1;
  public static final int Com = 2;

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = {
     4,  4,  4,  4,  4,  4,  4,  4,  4,  6,  1,  0,  7,  2,  4,  4, 
     4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0, 
     7,  0,  8,  0,  4,  0, 47,  0, 36, 37, 10, 42, 11, 25, 31,  9, 
     5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 12, 32, 23, 22, 21,  0, 
     0,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3, 
     3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3, 38, 48, 39,  0,  4, 
     0, 26, 45, 44, 16, 13, 24,  3, 34, 19,  3, 46, 14,  3, 18, 17, 
    30,  3, 27, 15, 29, 43, 35, 33,  3, 28,  3, 40, 20, 41,  0,  4
  };

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\4\0\1\1\2\2\1\3\1\4\1\2\1\5\1\6"+
    "\1\7\1\10\1\11\6\3\1\12\1\13\1\14\1\15"+
    "\1\3\1\16\2\3\1\17\1\20\2\3\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\3\1\30\1\31"+
    "\1\32\1\31\1\33\1\34\2\2\1\31\1\35\1\36"+
    "\1\37\1\40\1\41\3\3\1\42\1\43\1\3\1\44"+
    "\1\45\1\46\1\47\1\50\3\3\1\51\5\3\1\32"+
    "\1\0\1\52\1\53\1\54\1\55\1\56\1\57\1\3"+
    "\1\60\1\61\1\62\1\63\5\3\1\64\1\3\1\0"+
    "\1\65\2\3\1\66\1\67\2\3\1\70\1\3\1\71"+
    "\1\72\1\73\2\3\1\74";

  private static int [] zzUnpackAction() {
    int [] result = new int[111];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\61\0\142\0\223\0\304\0\365\0\u0126\0\u0157"+
    "\0\u0188\0\304\0\304\0\u01b9\0\u01ea\0\304\0\u021b\0\u024c"+
    "\0\u027d\0\u02ae\0\u02df\0\u0310\0\u0341\0\304\0\u0372\0\304"+
    "\0\u03a3\0\u03d4\0\304\0\u0405\0\u0436\0\304\0\304\0\u0467"+
    "\0\u0498\0\304\0\304\0\304\0\304\0\304\0\304\0\304"+
    "\0\u04c9\0\304\0\u04fa\0\u052b\0\u055c\0\304\0\u058d\0\u05be"+
    "\0\u05ef\0\304\0\304\0\304\0\304\0\304\0\304\0\u0620"+
    "\0\u0651\0\u0682\0\u0157\0\u0157\0\u06b3\0\u0157\0\u0157\0\304"+
    "\0\304\0\304\0\u06e4\0\u0715\0\u0746\0\u0157\0\u0777\0\u07a8"+
    "\0\u07d9\0\u080a\0\u083b\0\304\0\u086c\0\304\0\304\0\304"+
    "\0\304\0\304\0\304\0\u089d\0\u0157\0\u0157\0\u0157\0\u0157"+
    "\0\u08ce\0\u08ff\0\u0930\0\u0961\0\u0992\0\u0157\0\u09c3\0\u09f4"+
    "\0\u0157\0\u0a25\0\u0a56\0\u0157\0\u0157\0\u0a87\0\u0ab8\0\304"+
    "\0\u0ae9\0\u0157\0\u0157\0\u0157\0\u0b1a\0\u0b4b\0\u0157";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[111];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\5\1\6\1\7\1\10\1\5\1\11\2\12\1\13"+
    "\1\14\1\15\1\16\1\17\1\20\1\21\1\10\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\33\1\34\2\10\1\35\1\10\1\36\1\37\1\40"+
    "\1\10\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\50\2\10\1\51\1\10\1\52\1\5\1\53\1\54"+
    "\1\55\3\53\1\0\1\53\1\56\47\53\1\57\11\12"+
    "\1\60\1\61\46\12\1\62\1\6\1\7\3\62\2\12"+
    "\1\63\47\62\1\64\63\0\1\12\57\0\1\12\62\0"+
    "\3\10\7\0\7\10\4\0\1\10\1\0\5\10\2\0"+
    "\3\10\7\0\4\10\7\0\1\11\65\0\1\65\57\0"+
    "\1\66\75\0\1\67\35\0\3\10\7\0\1\10\1\70"+
    "\3\10\1\71\1\10\4\0\1\10\1\0\5\10\2\0"+
    "\3\10\7\0\4\10\5\0\3\10\7\0\1\72\6\10"+
    "\4\0\1\10\1\0\5\10\2\0\3\10\7\0\4\10"+
    "\5\0\3\10\7\0\4\10\1\73\2\10\4\0\1\10"+
    "\1\0\5\10\2\0\3\10\7\0\4\10\5\0\3\10"+
    "\7\0\7\10\4\0\1\74\1\0\5\10\2\0\3\10"+
    "\7\0\4\10\5\0\3\10\7\0\6\10\1\75\4\0"+
    "\1\10\1\0\5\10\2\0\3\10\7\0\4\10\5\0"+
    "\3\10\7\0\5\10\1\76\1\10\4\0\1\77\1\0"+
    "\5\10\2\0\3\10\7\0\4\10\30\0\1\100\57\0"+
    "\1\101\1\102\35\0\3\10\7\0\4\10\1\103\2\10"+
    "\4\0\1\10\1\0\5\10\2\0\3\10\7\0\1\104"+
    "\3\10\5\0\3\10\7\0\7\10\4\0\1\10\1\0"+
    "\1\10\1\105\3\10\2\0\3\10\7\0\4\10\5\0"+
    "\3\10\7\0\4\10\1\106\2\10\4\0\1\10\1\0"+
    "\2\10\1\107\2\10\2\0\1\10\1\110\1\10\7\0"+
    "\4\10\5\0\3\10\7\0\7\10\4\0\1\10\1\0"+
    "\5\10\2\0\1\10\1\111\1\10\7\0\4\10\5\0"+
    "\3\10\7\0\7\10\4\0\1\10\1\0\1\112\4\10"+
    "\2\0\3\10\7\0\4\10\5\0\3\10\7\0\7\10"+
    "\4\0\1\10\1\0\1\10\1\113\3\10\2\0\3\10"+
    "\7\0\4\10\2\0\1\53\1\0\4\53\1\0\1\53"+
    "\1\0\47\53\3\0\1\114\56\0\1\53\1\114\4\53"+
    "\1\0\1\53\1\0\47\53\6\0\1\115\2\0\1\116"+
    "\11\0\1\117\12\0\1\120\22\0\1\121\12\0\1\122"+
    "\57\0\1\123\52\0\3\10\7\0\2\10\1\124\4\10"+
    "\4\0\1\10\1\0\5\10\2\0\3\10\7\0\4\10"+
    "\5\0\3\10\7\0\3\10\1\125\3\10\4\0\1\10"+
    "\1\0\5\10\2\0\3\10\7\0\4\10\5\0\3\10"+
    "\7\0\7\10\4\0\1\10\1\0\3\10\1\126\1\10"+
    "\2\0\3\10\7\0\4\10\5\0\3\10\7\0\1\10"+
    "\1\127\5\10\4\0\1\10\1\0\5\10\2\0\3\10"+
    "\7\0\4\10\5\0\3\10\7\0\7\10\4\0\1\10"+
    "\1\0\1\10\1\130\3\10\2\0\3\10\7\0\4\10"+
    "\5\0\3\10\7\0\5\10\1\131\1\10\4\0\1\10"+
    "\1\0\5\10\2\0\3\10\7\0\4\10\5\0\3\10"+
    "\7\0\7\10\4\0\1\10\1\0\1\10\1\132\3\10"+
    "\2\0\3\10\7\0\4\10\5\0\3\10\7\0\7\10"+
    "\4\0\1\10\1\0\4\10\1\133\2\0\3\10\7\0"+
    "\4\10\5\0\3\10\7\0\1\134\6\10\4\0\1\10"+
    "\1\0\5\10\2\0\3\10\7\0\4\10\5\0\3\10"+
    "\7\0\6\10\1\135\4\0\1\10\1\0\5\10\2\0"+
    "\3\10\7\0\4\10\5\0\3\10\7\0\7\10\4\0"+
    "\1\10\1\0\1\10\1\136\3\10\2\0\3\10\7\0"+
    "\4\10\5\0\3\10\7\0\1\137\6\10\4\0\1\10"+
    "\1\0\5\10\2\0\3\10\7\0\4\10\7\0\1\140"+
    "\56\0\3\10\7\0\1\141\6\10\4\0\1\10\1\0"+
    "\5\10\2\0\3\10\7\0\4\10\5\0\3\10\7\0"+
    "\7\10\4\0\1\10\1\0\5\10\2\0\3\10\7\0"+
    "\1\10\1\142\2\10\5\0\3\10\7\0\7\10\4\0"+
    "\1\10\1\0\1\143\4\10\2\0\3\10\7\0\4\10"+
    "\5\0\3\10\7\0\1\144\6\10\4\0\1\10\1\0"+
    "\5\10\2\0\3\10\7\0\4\10\5\0\3\10\7\0"+
    "\5\10\1\145\1\10\4\0\1\10\1\0\5\10\2\0"+
    "\3\10\7\0\4\10\5\0\3\10\7\0\1\10\1\146"+
    "\5\10\4\0\1\10\1\0\5\10\2\0\3\10\7\0"+
    "\4\10\5\0\3\10\7\0\7\10\4\0\1\10\1\0"+
    "\1\147\4\10\2\0\3\10\7\0\4\10\7\0\1\150"+
    "\56\0\3\10\7\0\7\10\4\0\1\10\1\0\3\10"+
    "\1\151\1\10\2\0\3\10\7\0\4\10\5\0\3\10"+
    "\7\0\7\10\4\0\1\10\1\0\2\10\1\152\2\10"+
    "\2\0\3\10\7\0\4\10\5\0\3\10\7\0\1\153"+
    "\6\10\4\0\1\10\1\0\5\10\2\0\3\10\7\0"+
    "\4\10\5\0\3\10\7\0\7\10\4\0\1\10\1\0"+
    "\5\10\2\0\3\10\7\0\3\10\1\154\5\0\3\10"+
    "\7\0\6\10\1\155\4\0\1\10\1\0\5\10\2\0"+
    "\3\10\7\0\4\10\5\0\3\10\7\0\4\10\1\156"+
    "\2\10\4\0\1\10\1\0\5\10\2\0\3\10\7\0"+
    "\4\10\5\0\3\10\7\0\5\10\1\157\1\10\4\0"+
    "\1\10\1\0\5\10\2\0\3\10\7\0\4\10\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2940];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\4\0\1\11\4\1\2\11\2\1\1\11\7\1\1\11"+
    "\1\1\1\11\2\1\1\11\2\1\2\11\2\1\7\11"+
    "\1\1\1\11\3\1\1\11\3\1\6\11\10\1\3\11"+
    "\11\1\1\11\1\0\6\11\14\1\1\0\7\1\1\11"+
    "\7\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[111];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */
StringBuffer string = new StringBuffer();
int count;
private void newline() {
errorMsg.newline(yychar);
}
private void err(int pos, String s) {
errorMsg.error(pos,s);
}
private void err(String s) {
err(yychar,s);
}
private java_cup.runtime.Symbol tok(int kind, Object value) {
return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(),
value);
}
private ErrorMsg errorMsg;
public Yylex(java.io.InputStream s, ErrorMsg e) {
this(s);
errorMsg=e;
}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Yylex(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Yylex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzPushbackPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead < 0) {
      return true;
    }
    else {
      zzEndRead+= numRead;
      return false;
    }
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol nextToken() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 29: 
          { err("\\dont match");
          }
        case 61: break;
        case 28: 
          { yybegin(Str1);
          }
        case 62: break;
        case 8: 
          { return tok(sym.COMMA, null);
          }
        case 63: break;
        case 38: 
          { return tok(sym.GE,null);
          }
        case 64: break;
        case 15: 
          { return tok(sym.DOT,null);
          }
        case 65: break;
        case 17: 
          { return tok(sym.LPAREN,null);
          }
        case 66: break;
        case 22: 
          { return tok(sym.RBRACE,null);
          }
        case 67: break;
        case 9: 
          { return tok(sym.COLON,null);
          }
        case 68: break;
        case 12: 
          { return tok(sym.EQ,null);
          }
        case 69: break;
        case 23: 
          { return tok(sym.PLUS,null);
          }
        case 70: break;
        case 40: 
          { return tok(sym.LE,null);
          }
        case 71: break;
        case 60: 
          { return tok(sym.FUNCTION,null);
          }
        case 72: break;
        case 44: 
          { string.append('\t');
          }
        case 73: break;
        case 58: 
          { return tok(sym.WHILE,null);
          }
        case 74: break;
        case 16: 
          { return tok(sym.SEMICOLON,null);
          }
        case 75: break;
        case 59: 
          { return tok(sym.BREAK,null);
          }
        case 76: break;
        case 11: 
          { return tok(sym.GT,null);
          }
        case 77: break;
        case 14: 
          { return tok(sym.MINUS,null);
          }
        case 78: break;
        case 18: 
          { return tok(sym.RPAREN,null);
          }
        case 79: break;
        case 46: 
          { count++;
          }
        case 80: break;
        case 1: 
          { err("Illegal character < "+yytext()+" >!");
          }
        case 81: break;
        case 31: 
          { count=1;yybegin(Com);
          }
        case 82: break;
        case 45: 
          { string.append('\\');
          }
        case 83: break;
        case 54: 
          { return tok(sym.TYPE,null);
          }
        case 84: break;
        case 53: 
          { return tok(sym.ELSE,null);
          }
        case 85: break;
        case 13: 
          { return tok(sym.LT,null);
          }
        case 86: break;
        case 51: 
          { return tok(sym.FOR,null);
          }
        case 87: break;
        case 41: 
          { return tok(sym.TO,null);
          }
        case 88: break;
        case 19: 
          { return tok(sym.LBRACK,null);
          }
        case 89: break;
        case 27: 
          { yybegin(YYINITIAL);return tok(sym.STR,string.toString());
          }
        case 90: break;
        case 10: 
          { return tok(sym.OR,null);
          }
        case 91: break;
        case 57: 
          { return tok(sym.ARRAY,null);
          }
        case 92: break;
        case 49: 
          { return tok(sym.LET,null);
          }
        case 93: break;
        case 4: 
          { return tok(sym.NUM,new Integer(yytext()));
          }
        case 94: break;
        case 42: 
          { string.append('\"');
          }
        case 95: break;
        case 56: 
          { int tmp=Integer.parseInt(yytext().substring(1, 4));
		if(tmp>255) 
			err("exceed \\ddd"); 
			else 
				string.append((char)tmp);
          }
        case 96: break;
        case 30: 
          { yybegin(Str);
          }
        case 97: break;
        case 34: 
          { return tok(sym.DO,null);
          }
        case 98: break;
        case 35: 
          { return tok(sym.OF,null);
          }
        case 99: break;
        case 20: 
          { return tok(sym.RBRACK,null);
          }
        case 100: break;
        case 36: 
          { return tok(sym.IN,null);
          }
        case 101: break;
        case 32: 
          { err("Comment symbol don't match!");
          }
        case 102: break;
        case 48: 
          { return tok(sym.END,null);
          }
        case 103: break;
        case 39: 
          { return tok(sym.NEQ,null);
          }
        case 104: break;
        case 47: 
          { count--;if (count==0) {yybegin(YYINITIAL);}
          }
        case 105: break;
        case 52: 
          { return tok(sym.VAR,null);
          }
        case 106: break;
        case 43: 
          { string.append('\n');
          }
        case 107: break;
        case 25: 
          { string.append(yytext());
          }
        case 108: break;
        case 24: 
          { return tok(sym.AND,null);
          }
        case 109: break;
        case 5: 
          { string.setLength(0);yybegin(Str);
          }
        case 110: break;
        case 55: 
          { return tok(sym.THEN,null);
          }
        case 111: break;
        case 33: 
          { return tok(sym.ASSIGN,null);
          }
        case 112: break;
        case 6: 
          { return tok(sym.DIVIDE,null);
          }
        case 113: break;
        case 21: 
          { return tok(sym.LBRACE,null);
          }
        case 114: break;
        case 37: 
          { return tok(sym.IF,null);
          }
        case 115: break;
        case 7: 
          { return tok(sym.TIMES,null);
          }
        case 116: break;
        case 50: 
          { return tok(sym.NIL,null);
          }
        case 117: break;
        case 3: 
          { return tok(sym.ID,yytext());
          }
        case 118: break;
        case 26: 
          { err("String presentation error!");
          }
        case 119: break;
        case 2: 
          { 
          }
        case 120: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              { {if (yystate()==Com) err("Comment symbol don't match!");
if (yystate()==Str) err("String presentation error!");
if (yystate()==Str1) err("String presentation error!");
return tok(sym.EOF, null);
}
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
