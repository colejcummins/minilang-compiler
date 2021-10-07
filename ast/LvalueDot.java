package ast;

import semantics.Visitor;

public class LvalueDot
   implements Lvalue
{
   private final int lineNum;
   private final Expression left;
   private final String id;

   public LvalueDot(int lineNum, Expression left, String id)
   {
      this.lineNum = lineNum;
      this.left = left;
      this.id = id;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public String toString(String sp) {
      return String.format("%s%s . %s%n", sp, this.left.toString(sp), this.id);
   }

   public int getLineNum() {
      return this.lineNum;
   }

   public Expression getLeft() {
      return this.left;
   }

   public String getId() {
      return this.id;
   }
}
