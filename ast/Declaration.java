package ast;

import semantics.Visitor;

public class Declaration
{
   private final int lineNum;
   private final Type type;
   private final String name;

   public Declaration(int lineNum, Type type, String name)
   {
      this.lineNum = lineNum;
      this.type = type;
      this.name = name;
   }

   public String toString(String sp) {
      return String.format("%s%s: %s", sp, this.type.toString(), this.name);
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public int getLineNum() {
      return this.lineNum;
   }

   public Type getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

}
