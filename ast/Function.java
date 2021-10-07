package ast;

import java.util.List;

import semantics.Visitor;

public class Function
{
   private final int lineNum;
   private final String name;
   private final Type retType;
   private final List<Declaration> params;
   private final List<Declaration> locals;
   private final Statement body;

   public Function(int lineNum, String name, List<Declaration> params,
      Type retType, List<Declaration> locals, Statement body)
   {
      this.lineNum = lineNum;
      this.name = name;
      this.params = params;
      this.retType = retType;
      this.locals = locals;
      this.body = body;
   }

   public String toString(String sp) {
      sp += "  ";
      String out = String.format("fun %s -> %s%n%sParameters ->%n", this.name, this.retType.toString(), sp);
      for (Declaration p : this.params) {
         out += "  " + p.toString(sp) + "\n";
      }
      out += sp + "Locals ->\n";
      for (Declaration l : this.locals) {
         out += "  " + l.toString(sp) + "\n";
      }
      out += sp + "Statements ->\n" + body.toString(sp);
      return out;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public String getName() {
      return this.name;
   }

   public Type getRetType() {
      return this.retType;
   }

   public List<Declaration> getParams() {
      return this.params;
   }

   public List<Declaration> getLocals() {
      return this.locals;
   }

   public Statement getBody() {
      return this.body;
   }
}
