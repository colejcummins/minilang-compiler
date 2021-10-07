package ast;

import java.util.List;

import semantics.Visitor;

public class TypeDeclaration
{
   private final int lineNum;
   private final String name;
   private final List<Declaration> fields;

   public TypeDeclaration(int lineNum, String name, List<Declaration> fields)
   {
      this.lineNum = lineNum;
      this.name = name;
      this.fields = fields;
   }

   public String toString(String sp) {
      String out = String.format("Struct %s ->%n", this.name);
      for (Declaration f : this.fields) {
         out += sp + f.toString(sp) + "\n";
      }
      return out;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public int getLineNum() {
      return this.lineNum;
   }

   public List<Declaration> getFields() {
      return this.fields;
   }

   public String getName() {
      return this.name;
   }
}
