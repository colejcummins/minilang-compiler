package ast;

import semantics.Visitor;

public class FalseExpression
   extends AbstractExpression
{
   public FalseExpression(int lineNum)
   {
      super(lineNum);
   }

   public String toString(String sp) {
      return "False";
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
