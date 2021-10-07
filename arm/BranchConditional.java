package arm;

import ast.BinaryExpression.Operator;

public class BranchConditional implements ARMInstruction {
  private final Operator operator;
  private final String label;

  public BranchConditional(Operator operator, String label) {
    this.operator = operator;
    this.label = label;
  }

  public String toString() {
    return String.format("%s %s", this.getOperatorStr(), this.label);
  }

  private String getOperatorStr() {
    switch (this.operator) {
      case LT: return "blt";
      case GT: return "bgt";
      case LE: return "ble";
      case GE: return "bge";
      case NE: return "bne";
      default: return "beq";
    }
  }
}
