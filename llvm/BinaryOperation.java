package llvm;

import java.util.Map;

import ast.BinaryExpression.Operator;

public class BinaryOperation implements Operation {
  private final Operator operator;
  private Value left;
  private Value right;

  public BinaryOperation(Operator operator, Value left, Value right) {
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("%s i64 %s, %s", this.getOperatorStr(), this.left.toString(), this.right.toString());
  }

  private String getOperatorStr() {
    switch (this.operator) {
      case MINUS: return "sub";
      case TIMES: return "mul";
      case DIVIDE: return "sdiv";
      case OR: return "or";
      case AND: return "and";
      default: return "add";
    }
  }

  public Operator getOperator() {
    return this.operator;
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
        case MINUS: return this.left.getValue() - this.right.getValue();
        case TIMES: return this.left.getValue() * this.right.getValue();
        case DIVIDE: return this.left.getValue() / this.right.getValue();
        case OR: return ((this.left.getValue() == 1) || (this.right.getValue() == 1)) ? 1 : 0;
        case AND: return ((this.left.getValue() == 1) && (this.right.getValue() == 1)) ? 1 : 0;
        default: return this.left.getValue() + this.right.getValue();
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

  public Value getLeft() {
    return this.left;
  }

  public Value getRight() {
    return this.right;
  }

  public void accept(LLVMVisitor v) {
    v.visit(this);
  }
}