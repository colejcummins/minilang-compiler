package ast;

import semantics.Visitor;

public class PrintLnStatement
   extends AbstractStatement
{
   private final Expression expression;

   public PrintLnStatement(int lineNum, Expression expression)
   {
      super(lineNum);
      this.expression = expression;
   }

   public String toString(String sp) {
      return String.format("%sprintln%n  %s%s%n", sp, sp, this.expression.toString(sp));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
