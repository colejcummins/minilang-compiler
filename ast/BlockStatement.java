package ast;

import java.util.List;
import java.util.ArrayList;

import semantics.Visitor;

public class BlockStatement
   extends AbstractStatement
{
   private final List<Statement> statements;

   public BlockStatement(int lineNum, List<Statement> statements)
   {
      super(lineNum);
      this.statements = statements;
   }

   public String toString(String sp) {
      String out = "";

      for (Statement s : this.statements) {
         out += s.toString("") + "\n";
      }

      return out;
   }

   public static BlockStatement emptyBlock()
   {
      return new BlockStatement(-1, new ArrayList<>());
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public List<Statement> getStatements() {
      return this.statements;
   }
}
