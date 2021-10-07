package arm;

import ast.BinaryExpression.Operator;

public class BinaryARMInstruction implements ARMInstruction {
  private final Operator opr;
  private final Operand target;
  private final Operand left;
  private final Operand right;

  public BinaryARMInstruction(Operator opr, Operand target, Operand left, Operand right) {
    this.opr = opr;
    this.target = target;
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("%s %s, %s, %s",
      this.getOperatorStr(),
      this.target.toString(),
      this.left.toString(),
      this.right.toString()
    );
  }

  private String getOperatorStr() {
    switch(this.opr) {
      case MINUS: return "sub";
      case TIMES: return "mul";
      case OR: return "orr";
      case AND: return "and";
      default: return "add";
    }
  }
}
