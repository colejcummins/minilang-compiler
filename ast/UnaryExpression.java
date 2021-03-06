package ast;

import semantics.Visitor;

public class UnaryExpression
   extends AbstractExpression
{
   private final UnaryOperator operator;
   private final Expression operand;

   private UnaryExpression(int lineNum, UnaryOperator operator, Expression operand)
   {
      super(lineNum);
      this.operator = operator;
      this.operand = operand;
   }

   public String toString(String sp) {
      return "%s %s\n".format(this.operator.toString(), this.operand.toString());
   }

   public void accept(Visitor v) {
      v.visit(this);
   }

   public UnaryOperator getOperator() {
      return this.operator;
   }

   public Expression getOperand() {
      return this.operand;
   }

   public static UnaryExpression create(int lineNum, String opStr,
      Expression operand)
   {
      if (opStr.equals(NOT_OPERATOR))
      {
         return new UnaryExpression(lineNum, UnaryOperator.NOT, operand);
      }
      else if (opStr.equals(MINUS_OPERATOR))
      {
         return new UnaryExpression(lineNum, UnaryOperator.MINUS, operand);
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }

   private static final String NOT_OPERATOR = "!";
   private static final String MINUS_OPERATOR = "-";

   public static enum UnaryOperator
   {
      NOT, MINUS
   }
}
