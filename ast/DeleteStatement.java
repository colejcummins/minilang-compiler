package ast;

import semantics.Visitor;

public class DeleteStatement
   extends AbstractStatement
{
   private final Expression expression;

   public DeleteStatement(int lineNum, Expression expression)
   {
      super(lineNum);
      this.expression = expression;
   }

   public String toString(String sp) {
      return "%sdelete\n  %s%s".format(sp, sp, this.expression.toString());
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public Expression getExpression() {
      return this.expression;
   }
}
