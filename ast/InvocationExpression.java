package ast;

import java.util.List;
import semantics.Visitor;

public class InvocationExpression
   extends AbstractExpression
{
   private final String name;
   private final List<Expression> arguments;

   public InvocationExpression(int lineNum, String name,
      List<Expression> arguments)
   {
      super(lineNum);
      this.name = name;
      this.arguments = arguments;
   }

   public String toString(String sp) {
      String out = String.format("%sCall %s ->%n", sp, this.name);
      for (Expression a : this.arguments) {
         out += String.format("  %s%s%n", sp, a.toString(sp + "  "));
      }
      return out;
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public String getName() {
      return this.name;
   }

   public List<Expression> getArguments() {
      return this.arguments;
   }
}
