package ast;

import semantics.Visitor;

public class ConditionalStatement
   extends AbstractStatement
{
   private final Expression guard;
   private final Statement thenBlock;
   private final Statement elseBlock;

   public ConditionalStatement(int lineNum, Expression guard,
      Statement thenBlock, Statement elseBlock)
   {
      super(lineNum);
      this.guard = guard;
      this.thenBlock = thenBlock;
      this.elseBlock = elseBlock;
   }

   public String toString(String sp) {
      return String.format("%sif \n  %s%s\n  %s%s\n  %s%s", sp, sp, this.guard.toString(sp), sp, this.thenBlock.toString(sp), sp, this.elseBlock.toString(sp));
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public Expression getGuard() {
      return this.guard;
   }

   public Statement getThenBlock() {
      return this.thenBlock;
   }

   public Statement getElseBlock() {
      return this.elseBlock;
   }
}
