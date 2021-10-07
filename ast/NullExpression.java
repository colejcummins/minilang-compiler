package ast;

import semantics.Visitor;

public class NullExpression
   extends AbstractExpression
{
   public NullExpression(int lineNum)
   {
      super(lineNum);
   }

   public String toString(String sp) {
      return "Null";
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
