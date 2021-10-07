package ast;

import semantics.Visitor;

public class PrintStatement
   extends AbstractStatement
{
   private final Expression expression;

   public PrintStatement(int lineNum, Expression expression)
   {
      super(lineNum);
      this.expression = expression;
   }

   public String toString(String sp) {
      return String.format("%sprint%n  %s%s%n", sp, sp, this.expression.toString(sp));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
