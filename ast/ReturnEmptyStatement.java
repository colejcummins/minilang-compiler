package ast;

import semantics.Visitor;

public class ReturnEmptyStatement
   extends AbstractStatement
{
   public ReturnEmptyStatement(int lineNum)
   {
      super(lineNum);
   }

   public String toString(String sp) {
      return sp + "return\n";
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
