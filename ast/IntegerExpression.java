package ast;

import semantics.Visitor;

public class IntegerExpression
   extends AbstractExpression
{
   private final String value;

   public IntegerExpression(int lineNum, String value)
   {
      super(lineNum);
      this.value = value;
   }

   public String toString(String sp) {
      return this.value;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
