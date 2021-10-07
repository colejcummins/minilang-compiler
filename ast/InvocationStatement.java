package ast;

import java.util.List;
import semantics.Visitor;

public class InvocationStatement
   extends AbstractStatement
{
   private final Expression expression;

   public InvocationStatement(int lineNum, Expression expression)
   {
      super(lineNum);
      this.expression = expression;
   }

   public String toString(String sp) {
      return this.expression.toString(sp);
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public Expression getExpression() {
      return this.expression;
   }
}
