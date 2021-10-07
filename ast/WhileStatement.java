package ast;

import semantics.Visitor;

public class WhileStatement
   extends AbstractStatement
{
   private final Expression guard;
   private final Statement body;

   public WhileStatement(int lineNum, Expression guard, Statement body)
   {
      super(lineNum);
      this.guard = guard;
      this.body = body;
   }

   public String toString(String sp) {
      return String.format("%swhile%n  %s%s%n  %s%s%n", sp, sp, this.guard.toString(sp), sp, this.body.toString(sp));
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public Expression getGuard() {
      return this.guard;
   }

   public Statement getBody() {
      return this.body;
   }
}
