package llvm;

import java.util.Map;

import ast.BinaryExpression.Operator;

public class ComparisonOperation implements Operation {
  private final Operator operator;
  private Value left;
  private Value right;

  public ComparisonOperation(Operator operator, Value left, Value right) {
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  public Operator getOperator() {
    return this.operator;
  }

  public Value getLeft() {
    return this.left;
  }

  public Value getRight() {
    return this.right;
  }

  public String toString() {
    if (left instanceof NullValue) {
      return String.format("icmp %s %s %s, %s", this.getOperatorStr(), this.right.getType().toString(), this.left.toString(), this.right.toString());
    }
    return String.format("icmp %s %s %s, %s", this.getOperatorStr(), this.left.getType().toString(), this.left.toString(), this.right.toString());
  }

  private String getOperatorStr() {
    switch (this.operator) {
      case LT: return "slt";
      case GT: return "sgt";
      case LE: return "sle";
      case GE: return "sge";
      case NE: return "ne";
      default: return "eq";
    }
  }

  public void usingValue(Map<String, Boolean> used) {
    if (this.left instanceof RegisterValue) {
      used.put(this.left.toString(), true);
    }
    if (this.right instanceof RegisterValue) {
      used.put(this.right.toString(), true);
    }
  }

  public int getResult() {
    if ((this.left instanceof IntValue) && (this.right instanceof IntValue)) {
      switch (this.operator) {
        case LT: return (this.left.getValue() < this.right.getValue()) ? 1 : 0;
        case GT: return (this.left.getValue() > this.right.getValue()) ? 1 : 0;
        case LE: return (this.left.getValue() <= this.right.getValue()) ? 1 : 0;
        case GE: return (this.left.getValue() >= this.right.getValue()) ? 1 : 0;
        case NE: return (this.left.getValue() != this.right.getValue()) ? 1 : 0;
        default: return (this.left.getValue() == this.right.getValue()) ? 1 : 0;
      }
    }
    return -1;
  }

  public void propagate(Map<String, Integer> constants) {
    if (constants.containsKey(this.left.toString())) {
      this.left = new IntValue(constants.get(this.left.toString()));
    }
    if (constants.containsKey(this.right.toString())) {
      this.right = new IntValue(constants.get(this.right.toString()));
    }
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}
