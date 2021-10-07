package ast;

import semantics.Visitor;

public class DotExpression
   extends AbstractExpression
{
   private final Expression left;
   private final String id;

   public DotExpression(int lineNum, Expression left, String id)
   {
      super(lineNum);
      this.left = left;
      this.id = id;
   }

   public String toString(String sp) {
      return String.format("%s%s . %s%n", sp, this.left.toString(sp), this.id);
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public Expression getLeft() {
      return this.left;
   }

   public String getId() {
      return this.id;
   }
}
