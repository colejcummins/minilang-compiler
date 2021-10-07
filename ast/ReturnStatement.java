package ast;

import semantics.Visitor;

public class ReturnStatement
   extends AbstractStatement
{
   private final Expression expression;

   public ReturnStatement(int lineNum, Expression expression)
   {
      super(lineNum);
      this.expression = expression;
   }

   public String toString(String sp) {
      return String.format("%sreturn%n  %s%s%n", sp, sp, this.expression.toString(sp));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
