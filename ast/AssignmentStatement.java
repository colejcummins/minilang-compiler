package ast;

import semantics.Visitor;

public class AssignmentStatement
   extends AbstractStatement
{
   private final Lvalue target;
   private final Expression source;

   public AssignmentStatement(int lineNum, Lvalue target, Expression source)
   {
      super(lineNum);
      this.target = target;
      this.source = source;
   }

   public String toString(String sp) {
      return String.format("%s%s ->%n  %s%s%n", sp, this.target.toString(), sp, this.source.toString("  "));
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public Lvalue getTarget() {
      return this.target;
   }

   public Expression getSource() {
      return this.source;
   }
}
