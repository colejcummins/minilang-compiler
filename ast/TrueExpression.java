package ast;

import semantics.Visitor;

public class TrueExpression
   extends AbstractExpression
{
   public TrueExpression(int lineNum)
   {
      super(lineNum);
   }

   public String toString(String sp) {
      return "True";
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
