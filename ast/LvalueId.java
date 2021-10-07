package ast;

import semantics.Visitor;

public class LvalueId
   implements Lvalue
{
   private final int lineNum;
   private final String id;

   public LvalueId(int lineNum, String id)
   {
      this.lineNum = lineNum;
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public int getLineNum() {
      return this.lineNum;
   }

   public String toString(String sp) {
      return this.id;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }
}
