package ast;

import semantics.Visitor;

public class IdentifierExpression
   extends AbstractExpression
{
   private final String id;

   public IdentifierExpression(int lineNum, String id)
   {
      super(lineNum);
      this.id = id;
   }

   public String toString(String sp) {
      return this.id;
   }

   public String getId() {
      return this.id;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
