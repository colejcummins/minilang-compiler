package ast;

import semantics.Visitor;

public class NewExpression
   extends AbstractExpression
{
   private final String id;

   public NewExpression(int lineNum, String id)
   {
      super(lineNum);
      this.id = id;
   }

   public String toString(String sp) {
      return "new " + id;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public String getId() {
      return this.id;
   }
}
